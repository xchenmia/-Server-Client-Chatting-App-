import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;

public class ServerCotroller {
    @FXML
    public ListView<String> serverList;
    @FXML
    public Button update;


    public void updateClient(){
        for( int i=0; i<programGui.serverConnection.clients.size();i++){
            CheckMenuItem temp = new CheckMenuItem("clients"+ i);

        }

    }








}
