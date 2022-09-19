import java.io.Serializable;
import java.util.ArrayList;

public class ChatRoomInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    ArrayList<Integer> clientIDs; // might want to make this a map/binary search tree
    ArrayList<Integer> sendTo; // stores the ids of the people that we are sending the message to
    String Message;
    public ChatRoomInfo(){
        Message="";
        clientIDs=new ArrayList<Integer>();
        sendTo = new ArrayList<Integer>();
    }

    //
    // TextBox: Hi everyone!
    // chatInfo.Message = "Client" + id + ": " + TextBox.getText();
    //
}
