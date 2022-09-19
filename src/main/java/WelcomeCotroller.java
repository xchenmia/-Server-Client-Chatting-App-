import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;

public class WelcomeCotroller {
    @FXML
    public  Button server;
    @FXML
    private Button clients;
    @FXML
    private AnchorPane root;


    ServerCotroller serverController;
    ClientCotroller clientController;


//		sceneMap.put("server",  createServerGui());
//		sceneMap.put("client",  createClientGui());

    public void toClientGui() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGui.fxml"));
        Parent temp = loader.load();
        temp.getStylesheets().add("/style.css");
        clientController = loader.getController();
        programGui.clientConnection = new Client(data->{
            Platform.runLater(()->{
                //if(data.equals("join/left"))
                clientController.clientOnline.getItems().clear();
                clientController.menu.getItems().clear();

                if(!data.equals(""))
                    clientController.clientList.getItems().add(data.toString());

                for(Integer id : programGui.clientConnection.chatInfo.clientIDs) {
                    if(programGui.clientConnection.clientID != id) {
                        programGui.clientConnection.chatInfo.sendTo.clear();
                        clientController.clientOnline.getItems().add("Client" + id);
                        CheckMenuItem clientButton = new CheckMenuItem("Client" + id);

                        clientButton.setOnAction(e->{
                            if(((CheckMenuItem) e.getSource()).isSelected()) {
                                System.out.println("Selected");
                                programGui.clientConnection.chatInfo.sendTo.add(id);
                            }
                            else
                                programGui.clientConnection.chatInfo.sendTo.remove(id);
                        });

                        clientController.menu.getItems().add(clientButton);
                    }
                    else{
                        clientController.user.setText("You Are Client " + id);

                    }
                }
            });
        });

        programGui.clientConnection.start();

        root.getScene().setRoot(temp);
    }

    public void toServerGui() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ServerGui.fxml"));
        Parent temp = loader.load();
        temp.getStylesheets().add("/style.css");
        serverController = loader.getController();
        programGui.serverConnection = new Server(data -> {
            Platform.runLater(()->{
                serverController.serverList.getItems().add(data.toString());
            });

        });

        server.setDisable(true);
        root.getScene().setRoot(temp);



    }

}
