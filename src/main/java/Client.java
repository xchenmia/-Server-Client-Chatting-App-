import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{


    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;

    int clientID;
    ChatRoomInfo chatInfo;

    private Consumer<Serializable> callback;

    Client(Consumer<Serializable> call){
        chatInfo = new ChatRoomInfo();
        callback = call;
    }

    public void run() {

        try {
            socketClient= new Socket("127.0.0.1",5555);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {}

        try {
            ChatRoomInfo serverInfo = (ChatRoomInfo) in.readObject();
//          if(serverInfo.clientIDs.size() != chatInfo.clientIDs.size())
//              callback.accept("join/left");
            chatInfo = serverInfo; // might need to change in the future
            clientID = chatInfo.clientIDs.get(chatInfo.clientIDs.size()-1);
            callback.accept(chatInfo.Message);
            System.out.println("testing: " +chatInfo.Message);

            //we need to show the display a current list of other clients on the server.
            callback.accept("");

        }
        catch(Exception e) {}

        while(true) {

            try {
//            if(serverInfo.clientIDs.size() != chatInfo.clientIDs.size()) // this if statement is not in the right spot
//                callback.accept("join/left");
                ChatRoomInfo serverInfo = (ChatRoomInfo) in.readObject();
                callback.accept(serverInfo.Message);
                chatInfo.clientIDs = serverInfo.clientIDs;
                //System.out.println(serverInfo.Message);

                //chatInfo.sendTo = serverInfo.sendTo; make sure this line of code never happens!!!!
                //chatInfo = serverInfo; also avoid using this line as well. Super buggy!!!



            }
            catch(Exception e) {}
        }

    }

    public void send(ChatRoomInfo data) {

        try {
            out.writeObject(data);
            out.reset();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}

