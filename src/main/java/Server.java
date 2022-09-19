import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    private Consumer<Serializable> callback;
    ChatRoomInfo chatInfo;


    Server(Consumer<Serializable> call){

        callback = call;
        server = new TheServer();
        server.start();
        chatInfo = new ChatRoomInfo();
    }


    public class TheServer extends Thread{

        public void run() {

            try(ServerSocket mysocket = new ServerSocket(5555);){
                System.out.println("Server is waiting for a client!");


                while(true) {
                   // System.out.println("testing!!!");
                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("client has connected to server: " + "client #" + count);
                    clients.add(c);
                    //chatInfo.Message = "client has connected to server: " + "client #" + count;
                    //chatInfo.clientIDs.add(count);
                    c.start();

                    count++;

                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }


    class ClientThread extends Thread{


        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
        }

        public void updateClients(ChatRoomInfo data) {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(data);
                    t.out.reset();
                }
                catch(Exception e) {}
            }
        }

        public void sendData(ChatRoomInfo chatInfo, int count) {
            // sent data to specific client, use the count - 1 as index to send data for specific client
            ClientThread t = clients.get(count -1);
            try {
                t.out.writeObject(chatInfo);
                t.out.reset();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        public void run(){

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
//                // add all clients to clientIDs
//                chatInfo.clientIDs=new ArrayList<>();
//                for(int i=0 ;i< clients.size();i++){
//                    chatInfo.clientIDs.add(i+1);
//
//                }
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            synchronized (chatInfo) {
                chatInfo.clientIDs.add(count);
                chatInfo.Message = "new client on server: client #" + count;
                updateClients(chatInfo);
                chatInfo.Message = "";
            }

            while(true) {
                try {
                    ChatRoomInfo clientInfo = (ChatRoomInfo) in.readObject();

                    synchronized (chatInfo) {
                        chatInfo = clientInfo;
                        callback.accept("client: " + count + " sent: " + clientInfo.Message);


                        if (chatInfo.sendTo.isEmpty()) {
                            chatInfo.Message = "[World channel]---client" + count + ": " + clientInfo.Message;
                            updateClients(chatInfo);
                        } else {
                            chatInfo.Message = "[Private channel]---client" + count + ": " + clientInfo.Message;

                            for (Integer id : chatInfo.sendTo) {
                                sendData(chatInfo, id);
                            }
                        }
//
//                    updateClients(chatInfo);
                        chatInfo.Message = "";
                    }
                } catch (Exception e) {
                    synchronized (chatInfo) {
                        callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                        chatInfo.Message = "Client #" + count + " has left the server!";
                        chatInfo.clientIDs.remove(count - 1);

                        updateClients(chatInfo);

                        chatInfo.Message = "";
                        clients.remove(this);
                        break;
                    }
                }
            }
        }//end of run


    }//end of client thread
}






