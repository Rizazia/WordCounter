package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
 
/**
 * FXML Controller class
 *
 * @author Paul
 */
public class MainController implements Initializable {
    @FXML private TextField tfFilePath;
    @FXML private RadioButton rbWord;
    @FXML private RadioButton rbChar;
    @FXML private RadioButton rbText;
    @FXML private RadioButton rbFile;
    @FXML private TextArea taText;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //none required
    }    
    
    /**
     * When btnSelectFile is clicked
     * open a file chooser and if a file is chosen, set tfFilePath to contain the file's path
     * only directories and text files are available in the file chooser
     */
    public void btnSelectFileIsClicked(){
        String userDir = System.getProperty("user.home");
        JFileChooser chooser = new JFileChooser(userDir + "/Desktop");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
        chooser.setFileFilter(filter);
        
        chooser.setDialogTitle("Select a filie");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION){
            tfFilePath.setText(chooser.getSelectedFile().getAbsolutePath());
            //assume if the user selects a file they want to count that file and not the entered text
            rbText.setSelected(false);
            rbFile.setSelected(true);
        }
    }
    
    /**
     * When btnCount is clicked
     * access the file in tfFilePath and count how many characters/words it has
     * or count how many characters/words are in the text area, taText
     * both what to count and where the text being counted is is determined by radio buttons
     */
    public void btnCountIsClicked(){
        try{
            int count = 0;
            String typeToCount;
            String method;
            
            if (rbWord.isSelected()){//if counting words
                method = "words";
                if (rbFile.isSelected()){//if counting a file
                    typeToCount = "file";
                    BufferedReader reader = new BufferedReader(new java.io.FileReader(tfFilePath.getText()));
                    String line = reader.readLine();
                    
                    while (line != null){
                        count += countWords(line);
                        line = reader.readLine();
                    }
                    reader.close();
                } else {//else, counting the text area
                    typeToCount = "text area";
                    count = countWords(taText.getText());
                }
            } else {//else, characters are being counted
                method = "non-space characters";
                if (rbFile.isSelected()){//if counting a file
                    typeToCount = "file";
                    BufferedReader reader = new BufferedReader(new java.io.FileReader(tfFilePath.getText()));
                    String line = reader.readLine();
                    
                    while (line != null){
                        count += countChars(line);
                        line = reader.readLine();
                    }
                    reader.close();
                } else {//else, counting the text area
                    typeToCount = "text area";
                    count = countChars(taText.getText());
                }
            }
            
            //Display the results in an alert
            Alert result = new Alert(Alert.AlertType.INFORMATION, "The " + typeToCount + " has " + String.valueOf(count) + " " + method + " in it.");
            result.setHeaderText(null);
            result.setGraphic(null);
            Stage stage = (Stage) result.getDialogPane().getScene().getWindow();
            stage.setTitle("Words Counted");
            stage.getIcons().add(new Image("/img/icon.png"));

            stage.showAndWait();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The file path entered is invalid");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setTitle("Error");
            stage.getIcons().add(new Image("/img/icon.png"));

            stage.showAndWait();
        }
    }
    /**
     * if the user types into the text area assume the user wants to have the text area counted
     */
    public void taTextOnTyped(){
        if (rbFile.isSelected()){
            rbFile.setSelected(false);
            rbText.setSelected(true);
        }
    }
    /**
     * counts how many words are in parameter text and returns the value to the calling function
     * 
     * @param text
     * @return 
     */
    private int countWords(String text){
        if (text.length() == 0) return 0;//handles the edge case of an empty string being submitted (otherwise textArray.length would be 1)
        
        String[] textArray = text.trim().split("\\s+");
                
        return textArray.length;
    }
    /**
     * counts how many non-white space characters are in the submitted string and returns that number as an int
     * 
     * @param text
     * @return 
     */
    private int countChars(String text){
        int count = 0;
        
        for (int i = 0; i < text.length(); i++){//for every character in the text
            if (String.valueOf(text.charAt(i)).matches("\\S")) count++;//if the character is any non-space character increment count
        }
        
        return count;
    }
}
