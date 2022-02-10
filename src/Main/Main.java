package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


/** This creates the main class and inherits from the Application */
public class Main extends Application {

/** This method loads the mainScreen as the primary stage*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        }

        /** This method launches the application*/
        public static void main(String[] args) {
            launch(args);
        }

}

