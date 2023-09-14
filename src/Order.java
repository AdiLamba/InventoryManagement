/*
 * @author Aditya Lamba
 * Order Class 
 * 
 * This class is used in an inventory management program to allow the user
 * access to a file called 'orders.txt'. The user can create the file, add to the existing file,
 * or remove pending orders from the file as desired.
 * 
 * This is done in an intuitive GUI window, where the user can grasp the functionality
 * of the program through an autodidactic nature.
 * 
 * The 'orders.txt' list will then be passed on a regular basis (daily) to the larger Inventory Class
 * 
 */

 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.nio.file.Files;
 import java.nio.file.Paths;
 import java.util.List;
 import javafx.event.ActionEvent;
 import javafx.event.EventHandler;
 import javafx.geometry.Pos;
 import javafx.scene.Scene;
 import javafx.scene.control.Alert;
 import javafx.scene.control.Button;
 import javafx.scene.control.Label;
 import javafx.scene.control.TextArea;
 import javafx.scene.control.TextField;
 import javafx.scene.control.Alert.AlertType;
 import javafx.scene.layout.HBox;
 import javafx.scene.layout.VBox;
 import javafx.stage.Stage;
 
 public class Order {
 
     private String name;
     private int quantity;
     private String id;
     private double price;
 
     public double getItemPrice(){
         return this.price;
     }
 
     public void setItemPrice(double price){
         this.price = price;
     }
 
     public String getItemName() {
         
         return this.name;
     }
     
     public int getItemQuantity() {
         
         return this.quantity;
     }
 
     public void setItemQuantity(int quantity) {
         this.quantity = quantity;
     }
 
     public String getItemId() {
         return this.id;
     }
     
     
 
     Button order = new Button("_Order");
 
     public Order(String id, String name, int quantity, double price, double total) {
     }
 
     public Order() {
     }
 
     // Code to handle the order button click
     public void orderStock() {
 
          OrderProcessor processor = new OrderProcessor("orders.txt");
         processor.startProcessingOrders();
 
 
 
                 Label labelId = new Label("Item ID: ");
                 TextField textFieldId = new TextField();
                 HBox hbox1 = new HBox(10, labelId, textFieldId);
 
                 Label labelName = new Label("Item Name: ");
                 TextField textFieldName = new TextField();
                 HBox hbox2 = new HBox(10, labelName, textFieldName);
 
                 Label quantity = new Label("Quantity: ");
                 TextField textFieldQuantity = new TextField();
                 HBox hbox3 = new HBox(10, quantity, textFieldQuantity);
 
                 Label price = new Label("Price Per Item: ");
                 TextField textFieldPrice = new TextField();
                 HBox hbox4 = new HBox(10, price, textFieldPrice);
 
                 Label total = new Label("Total Price: ");
                 TextField textFieldTotal = new TextField();
                 textFieldTotal.setEditable(false);
 
                 // textFieldTotal Listeners to update once values are changed
                 textFieldQuantity.textProperty().addListener((obs, oldVal, newVal) -> {
                     double quantityValue = Double.parseDouble(newVal);
                     double priceValue = Double.parseDouble(textFieldPrice.getText());
                     double totalPrice = quantityValue * priceValue;
                     textFieldTotal.setText(String.format("%.2f", totalPrice));
                 });
 
                 textFieldPrice.textProperty().addListener((obs, oldVal, newVal) -> {
                     double priceValue = Double.parseDouble(newVal);
                     double quantityValue = Double.parseDouble(textFieldQuantity.getText());
                     double totalPrice = quantityValue * priceValue;
                     textFieldTotal.setText(String.format("%.2f", totalPrice));
                 });
 
                 HBox hbox5 = new HBox(10, total, textFieldTotal);
 
                 Button add = new Button("Add To Order List");
                 Button create = new Button("Create Order");
                 Button back = new Button("Exit");
                 Button show = new Button("Show Current Orders");
                 Button remove = new Button("Remove Selected");
                 // ObservableList<String> itemList = FXCollections.observableArrayList();
 
                 TextArea infoBox = new TextArea();
                 Label boxTitle = new Label("Order Information");
                 infoBox.setEditable(false);
                 infoBox.setStyle("-fx-font-family: Courier New");
                 boxTitle.setStyle("-fx-font-family: Arial Bold; -fx-font-weight: Bold; -fx-font-size: 20");
                 boxTitle.setAlignment(Pos.CENTER);
                 VBox vbox2 = new VBox(25, boxTitle, infoBox);
                 vbox2.setAlignment(Pos.CENTER);
 
                 TextArea ordersBox = new TextArea();
                 Label ordersBoxTitle = new Label("Order List");
                 ordersBox.setEditable(false);
                 ordersBox.setStyle("-fx-font-family: Courier New");
                 ordersBoxTitle.setStyle("-fx-font-family: Arial Bold; -fx-font-weight: Bold; -fx-font-size: 20");
                 ordersBoxTitle.setAlignment(Pos.CENTER);
                 VBox vbox3 = new VBox(25, ordersBoxTitle, ordersBox);
                 vbox3.setAlignment(Pos.CENTER);
 
                 String header = String.format("%-20s%-20s%-20s%-20s%-20s%n", "ID", "Name", "Quantity", "Price",
                         "Total");
                 infoBox.setText(header);
                 //ordersBox.setText(header);
 
                 create.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(ActionEvent event) {
                         // get the text from the infoBox text area
                         String itemsText = infoBox.getText();
 
                         // write the text to a file
                         try {
                             FileWriter fw = new FileWriter("orders.txt", true); // true to append to the file
                             PrintWriter pw = new PrintWriter(fw);
                             pw.println(itemsText);
                             pw.close();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
 
                         Stage stage = (Stage) back.getScene().getWindow();
                         stage.close();
                     }
                 });
 
                 back.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(ActionEvent event) {
 
                         Stage stage = (Stage) back.getScene().getWindow();
                         stage.close();
                     }
                 });
 
                 add.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(ActionEvent event) {
 
                         String id = textFieldId.getText();
                         String name = textFieldName.getText();
                         String quantity = textFieldQuantity.getText();
                         String price = textFieldPrice.getText();
                         String total = textFieldTotal.getText();
 
                         // check if any of the text fields are empty
                         if (id.isEmpty() || name.isEmpty() || quantity.isEmpty() || price.isEmpty()
                                 || total.isEmpty()) {
                             // display an error message
                             Alert alert = new Alert(AlertType.ERROR);
                             alert.setTitle("Error");
                             alert.setHeaderText(null);
                             alert.setContentText("Please fill in all fields with appropriate data!");
                             alert.showAndWait();
 
                             // stop the handler from executing further
                             return;
                         }
                         String itemInfo = String.format("%-20s%-20s%-20s%-20s%-20s%n", id, name, quantity, price,
                                 total);
                         infoBox.appendText(itemInfo);
 
                         // clear the input fields after adding the new entry
                         textFieldId.clear();
                         textFieldName.clear();
                         textFieldQuantity.clear();
                         textFieldPrice.clear();
                         textFieldTotal.clear();
 
                     }
                 });
 
                 show.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(ActionEvent event) {
                         // clear the ordersBox text area
                         ordersBox.clear();
 
                         // read the text from the orders.txt file and append it to the ordersBox text
                         // area
                         try {
                             BufferedReader br = new BufferedReader(new FileReader("orders.txt"));
                             String line;
                             while ((line = br.readLine()) != null) {
                                 ordersBox.appendText(line + "\n");
                             }
                             br.close();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }
                 });
 
                 remove.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(ActionEvent event) {
                         // get the selected lines from the ordersBox text area
                         String selectedLines = ordersBox.getSelectedText();
 
                         // check if the selected lines contain the header
                         if (selectedLines.equals(header)) {
                             // do not remove the header
                             return;
                         }
 
                         // remove the selected lines from the orders.txt file
                         try {
                             // read the contents of the orders.txt file into a List<String>
                             List<String> lines = Files.readAllLines(Paths.get("orders.txt"));
 
                             // create a new PrintWriter to overwrite the orders.txt file
                             PrintWriter writer = new PrintWriter("orders.txt");
 
                             // iterate over the lines in the List and write them to the file if they're not
                             // selected
                             for (String line : lines) {
                                 if (!selectedLines.contains(line)) {
                                     writer.println(line);
                                 }
                             }
 
                             writer.close();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
 
                         // update the ordersBox text area to reflect the changes
                         show.fire();
                     }
                 });
 
                 HBox hbox6 = new HBox(10, add, create);
 
                 HBox hbox6b = new HBox(10, show, remove);
 
                 HBox hbox6c = new HBox(back);
 
                 VBox vbox1 = new VBox(20, hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox6b, hbox6c);
                 vbox1.setAlignment(Pos.CENTER);
 
                 HBox hbox7 = new HBox(10, vbox1, vbox2, vbox3);
                 hbox7.setAlignment(Pos.CENTER);
 
                 Stage orderStage = new Stage();
                 orderStage.setTitle("Orders");
                 Scene orderScene = new Scene(hbox7, 1500, 500);
                 orderStage.setScene(orderScene);
                 orderStage.show();
             }
         };
     
 