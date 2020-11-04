package wordcounter;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
 
/**
 *
 * @author Paul
 */
public class WordCounter extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WordCounter.class.getResource("/views/Main.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        primaryStage.getIcons().add(new Image("/img/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bug Tracker");
        primaryStage.show();
        
        //end of program procedure
        primaryStage.setOnCloseRequest(event -> {
            // prevent window from closing
            event.consume();

            // ask user if they want to close the program and respond accordingly
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Exit Application");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want close this application?");
            Optional<ButtonType> alertResult = alert.showAndWait();
            
            if (alertResult.get() == ButtonType.OK){
                System.exit(0);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
