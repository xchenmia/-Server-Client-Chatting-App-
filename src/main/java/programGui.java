
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.HashMap;

public class programGui extends Application {

	public static HashMap<String, Scene> sceneMap;
	public static Server serverConnection;
	public static Client clientConnection;
//	// I am still comfuse why we put this one here?
//	public static ServerCotroller serverController;
//	public static ClientCotroller clientController;



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

//	//feel free to remove the starter code from this method
//	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome");
		Parent root = FXMLLoader.load(getClass().getResource("/welcome.fxml"));
		Scene scene = new Scene(root,600,600);
		scene.getStylesheets().add("/style.css");
		primaryStage.setScene(scene);
		primaryStage.show();


	}

}