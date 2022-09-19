import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class ClientCotroller {

    @FXML
    ListView<String> clientList;
    @FXML
    ListView<String> clientOnline;

    @FXML
    private Button groupChat;

    @FXML
    public MenuButton menu;

    @FXML
    private Button send;
    @FXML
    public Label user;

    @FXML
    private TextField textBox;
    @FXML
    private Button confrim;

    public ArrayList<CheckMenuItem> clientButtons; // might put this in programGUI. Not sure yet.
    //public  ChatRoomInfo chatInfo;



    public void sendMessage(){
        programGui.clientConnection.chatInfo.Message = textBox.getText();
        for(Integer i: programGui.clientConnection.chatInfo.sendTo){
            System.out.println(i);
        }
        programGui.clientConnection.send(programGui.clientConnection.chatInfo);

        textBox.clear();
        send.setDisable(true);
        textBox.setDisable(true);
        groupChat.setDisable(false);
        menu.setDisable(false);

        programGui.clientConnection.chatInfo.sendTo.clear();
        programGui.clientConnection.chatInfo.Message = "";
    }

    // this function seems unneccesary
    public void sendToIndividual(){
//        programGui.clientConnection.chatInfo.Message = textBox.getText();
//        programGui.clientConnection.send(programGui.clientConnection.chatInfo);
//        textBox.clear();
//        send.setDisable(false);
//        textBox.setDisable(false);
//        groupChat.setDisable(true);
//
//        programGui.clientConnection.chatInfo.sendTo.clear();
    }

    public void Confrim(){
        send.setDisable(false);
        textBox.setDisable(false);
        groupChat.setDisable(true);
        menu.setDisable(true);

        for(MenuItem e : menu.getItems())
        {
            CheckMenuItem item = (CheckMenuItem) e;
            item.setSelected(false);
        }
    }
    public void sendAllPeople(){
        programGui.clientConnection.chatInfo.sendTo.clear();
        menu.setDisable(true);
    }



}
