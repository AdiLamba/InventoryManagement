import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;


//@Malik
// Controller class for the main page that has the event handlers and the MainGUI page elemtns variables
public class MainGuiController {

    @FXML
    private Button inventoryButton;

    @FXML
    private Button mainExitButton;

    @FXML
    private Button mainOrderButton;
    
    @FXML
void handleInventoryButtonClicked(ActionEvent event) throws IOException {
    Stage stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("inventoryGui.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    
} // button to open inventory window when clicked

@FXML
void handleMainExitButtonClicked(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("Are you sure you want exit the program?");
    alert.setContentText("Press OK to close the program, or cancel to stay on this screen.");

    Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Stage stage = (Stage) mainExitButton.getScene().getWindow();
        stage.close();
        } else {
        alert.close();
        }

} // confirmation window that dispalys before closing trhe program

@FXML
void handleMainOrderButtonClicked(ActionEvent event) {
    Order order = new Order();
        order.orderStock();

   
}




}
