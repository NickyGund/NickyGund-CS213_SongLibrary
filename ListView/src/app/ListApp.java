// Sai Nayan Malladi srm275
// Nicolas Gundersen neg62
// CS 213 Software Methodology 
// Sesh

// THe ListApp class serves as the main launcher of our GUI program. 
package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.ListController;

public class ListApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//load our fxml file 
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/list.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		//load our controller into the primary stage
		ListController listController = 
				loader.getController();
		listController.start(primaryStage);
		//set the scene
		Scene scene = new Scene(root, 330, 420);
		//make scene primary stage - dont make resizable
		primaryStage.setScene(scene);
		primaryStage.show(); 
		primaryStage.setResizable(false);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//LAUNCH THE PROGRAM
		launch(args);

	}

}
