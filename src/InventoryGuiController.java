import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

//@Malik
//  Controller that holds all the eventhandlers and all the GUI elements variables for the inventory window
public class InventoryGuiController {

    @FXML
    private Button addClearButton;

    @FXML
    private DatePicker addDateAddedDatePicker;

    @FXML
    private DatePicker addExpiryDateDatePicker;

    @FXML
    private TextField addItemIdTextField;

    @FXML
    private TextField addNameTextField;

    @FXML
    private TextField addOrderCostTextField;

    @FXML
    private TextField addQuantityTextField;

    @FXML
    private TextField addReorderPointTextField;

    @FXML
    private TextField addSalePriceTextField;

    @FXML
    private Button addSubmitButton;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Item, LocalDate> dateAddedTableColumn;

    @FXML
    private Button editClearButton;

    @FXML
    private DatePicker editDateAddedDatePicker;

    @FXML
    private DatePicker editExpriyDateDatePicker;

    @FXML
    private TextField editItemIdTextField;

    @FXML
    private TextField editNameTextField;

    @FXML
    private TextField editOrderCostTextField;

    @FXML
    private TextField editQuantityTextField;

    @FXML
    private TextField editReorderPointTextField;

    @FXML
    private TextField editSalePriceTextField;

    @FXML
    private Button editSubmitButton;

    @FXML
    private TableColumn<Item, LocalDate> expiryDateTableColumn;

    @FXML
    private TableView<Item> inventoryTableView;

    @FXML
    private TableColumn<Item, Integer> itemIdColumn;

    @FXML
    private TableColumn<Item, String> nameTableColumn;

    @FXML
    private TableColumn<Item, Double> orderPriceTableColumn;

    @FXML
    private TableColumn<Item, Double> priceTableColumn;

    @FXML
    private TableColumn<Item, Integer> quantityTableColumn;

    @FXML
    private TableColumn<Item, Integer> reorderPointTableColumn;

    @FXML
    private Button sellClearButton;

    @FXML
    private TextField sellItemIdTextField;

    @FXML
    private TextField sellQuantityTextField;

    @FXML
    private TextField sellSalePriceTextField;

    @FXML
    private Button sellSubmitButton;

    @FXML
    private DatePicker sellDateAddedDatePicker;

    @FXML
    private DatePicker sellDateSoldDatePicker;

    private ArrayList<Item> itemList = new ArrayList<>();

    public void saveInventoryToFile() {
        try {
            FileOutputStream fos = new FileOutputStream("data/inventory.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(itemList);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // save item arraylist to the data folder

    public static ArrayList<Item> loadInventoryFromFile(TableView<Item> inventoryTableView) {
        ArrayList<Item> itemList = new ArrayList<>();
        File file = new File("data/inventory.dat");
        if (!file.exists()) {
            return itemList;
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            itemList = (ArrayList<Item>) inputStream.readObject();
            inventoryTableView.setItems(FXCollections.observableArrayList(itemList)); // set loaded inventory to the TableView
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return itemList;
    } // load item arraylist from data folder
    
    

    public void initialize(){
        itemList = loadInventoryFromFile(inventoryTableView);
        
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemId"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("itemPrice"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemQuantity"));
        orderPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("orderPrice"));
        reorderPointTableColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("reorderPoint"));
        expiryDateTableColumn.setCellValueFactory(new PropertyValueFactory<Item, LocalDate>("expiryDate"));
        dateAddedTableColumn.setCellValueFactory(new PropertyValueFactory<Item, LocalDate>("dateAdded"));
        inventoryTableView.setItems(FXCollections.observableArrayList(itemList));
    } // intializes the table elements to display the item arraylsit
    

    @FXML
    void addClearButtonClicked(ActionEvent event) {
        addItemIdTextField.setText("");
        addNameTextField.setText("");
        addSalePriceTextField.setText("");
        addQuantityTextField.setText("");
        addOrderCostTextField.setText("");
        addReorderPointTextField.setText("");
        addExpiryDateDatePicker.setValue(null);
        addDateAddedDatePicker.setValue(null);

    } // clears the entry fields when clear button is clicked

    @FXML
void addSubmitButtonClicked(ActionEvent event) {
    String errorMessage = "";
    int itemId = 0;
    int itemQuantity = 0;
    int reorderPoint = 0;
    double itemPrice = 0.0;
    double orderPrice = 0.0;
    LocalDate expiryDate = null;
    LocalDate dateAdded = null;
    
    // exception handling window thats displayed for invalid inputs
    try {
        itemId = Integer.parseInt(addItemIdTextField.getText().trim());
        if (itemId <= 0 || itemId > 999999999) {
            errorMessage += "Item ID must be a positive integer not exceeding 9 digits.\n";
        }
    } catch (NumberFormatException e) {
        errorMessage += "Item ID must be a valid number.\n";
    }
    
    String itemName = addNameTextField.getText().trim();
    if (itemName.isEmpty() || itemName.length() > 15) {
        errorMessage += "Item name must not be blank and must not exceed 15 characters.\n";
    }
    
    try {
        itemPrice = Double.parseDouble(addSalePriceTextField.getText());
        if (itemPrice <= 0) {
            errorMessage += "Item price must be a positive number.\n";
        }
    } catch (NumberFormatException e) {
        errorMessage += "Item price must be a valid number.\n";
    }
    
    try {
        itemQuantity = Integer.parseInt(addQuantityTextField.getText().trim());
        if (itemQuantity <= 0 || itemQuantity > 9999) {
            errorMessage += "Item quantity must be a positive integer not exceeding 9999.\n";
        }
    } catch (NumberFormatException e) {
        errorMessage += "Item quantity must be a valid number.\n";
    }
    
    try {
        reorderPoint = Integer.parseInt(addReorderPointTextField.getText().trim());
        if (reorderPoint <= 0 || reorderPoint > 9999) {
            errorMessage += "Reorder point must be a positive integer not exceeding 9999.\n";
        }
    } catch (NumberFormatException e) {
        errorMessage += "Reorder point must be a valid number.\n";
    }
    
    expiryDate = addExpiryDateDatePicker.getValue();
    if (expiryDate == null || expiryDate.isBefore(LocalDate.now())) {
        errorMessage += "Expiry date must be a valid date in the future.\n";
    }
    
    dateAdded = addDateAddedDatePicker.getValue();
    if (dateAdded == null || dateAdded.isAfter(LocalDate.now())) {
        errorMessage += "Date added must be a valid date in the past.\n";
    }
    
    if (!errorMessage.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid input");
        alert.setHeaderText("Please correct the following errors:");
        alert.setContentText(errorMessage);
        alert.showAndWait();
        return;
    }
    
    for (Item item : itemList) {
        if (item.getItemId() == itemId) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Duplicate item ID");
            alert.setHeaderText("An item with this ID already exists:");
            alert.setContentText("Please enter a unique item ID.");
            alert.showAndWait();
            return;
        }
    }
    
    Alert soldAlert = new Alert(AlertType.INFORMATION);
    soldAlert.setTitle("Inventory");
    soldAlert.setHeaderText("Item Added!");
    soldAlert.showAndWait();

    Item newItem = new Item(itemId, itemName, itemPrice, itemQuantity, orderPrice, reorderPoint, expiryDate, dateAdded);
    itemList.add(newItem);
    inventoryTableView.setItems(FXCollections.observableArrayList(itemList));

    saveInventoryToFile();
} // window to display when an  item is added correctly


    @FXML
    void backButtonClicked(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("Are you sure you want to return to the main screen?");
    alert.setContentText("Press OK to return to the main screen, or cancel to stay on this screen.");

    Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        } else {
        alert.close();
        }
    } // confirmatuion window displayed before exiting inventory window

    

    @FXML
    void handleAddDateAddedEntered(ActionEvent event) {

    }

    @FXML
    void handleAddExpiryDateEntered(ActionEvent event) {

    }

    @FXML
    void handleAddItemIdEntered(ActionEvent event) {

    }

    @FXML
    void handleAddNameEntered(ActionEvent event) {

    }

    @FXML
    void handleAddOrderCostEntered(ActionEvent event) {

    }

    @FXML
    void handleAddQuantityEntered(ActionEvent event) {

    }

    @FXML
    void handleAddReorderPointEntered(ActionEvent event) {

    }

    @FXML
    void handleAddSalePriceEntered(ActionEvent event) {

    }

    @FXML
    void handleDateAddedEntered(ActionEvent event) {

    }

    @FXML
    void handleEditClearButtonClicked(ActionEvent event) {
        editItemIdTextField.setText("");
        editNameTextField.setText("");
        editSalePriceTextField.setText("");
        editQuantityTextField.setText("");
        editOrderCostTextField.setText("");
        editReorderPointTextField.setText("");
        editExpriyDateDatePicker.setValue(null);
        editDateAddedDatePicker.setValue(null);

    } // clear entry fields

    @FXML
    void handleEditExpiryDateEntered(ActionEvent event) {

    }

    @FXML
    void handleEditItemIdEntered(ActionEvent event) {

    }

    @FXML
    void handleEditNameEntered(ActionEvent event) {

    }

    @FXML
    void handleEditQuantityEntered(ActionEvent event) {

    }

    @FXML
    void handleEditReorderPointEntered(ActionEvent event) {

    }

    @FXML
    void handleEditSalePriceEntered(ActionEvent event) {

    }

    @FXML
void handleEditSubmitButtonClicked(ActionEvent event) {

    // window displayed if invalid input is received
    if (editItemIdTextField.getText().isEmpty() || editNameTextField.getText().isEmpty() ||
            editSalePriceTextField.getText().isEmpty() || editQuantityTextField.getText().isEmpty() ||
            editOrderCostTextField.getText().isEmpty() || editReorderPointTextField.getText().isEmpty() ||
            editExpriyDateDatePicker.getValue() == null || editDateAddedDatePicker.getValue() == null) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Inventory");
        errorAlert.setHeaderText("Blank Entries Found!");
        errorAlert.setContentText("Please fill in all fields before updating an item in the inventory.");
        errorAlert.showAndWait();
        return;
    }

    int itemId = Integer.parseInt(editItemIdTextField.getText());
    String itemName = editNameTextField.getText();
    double itemPrice = Double.parseDouble(editSalePriceTextField.getText());
    int itemQuantity = Integer.parseInt(editQuantityTextField.getText());
    double orderPrice = Double.parseDouble(editOrderCostTextField.getText());
    int reorderPoint = Integer.parseInt(editReorderPointTextField.getText());
    LocalDate expiryDate = editExpriyDateDatePicker.getValue();
    LocalDate dateAdded = editDateAddedDatePicker.getValue();

    boolean itemExists = false;

    for (Item item : itemList) {
        if (item.getItemId() == itemId) {
            item.setItemName(itemName);
            item.setItemPrice(itemPrice);
            item.setItemQuantity(itemQuantity);
            item.setOrderPrice(orderPrice);
            item.setReorderPoint(reorderPoint);
            item.setExpiryDate(expiryDate);
            item.setDateAdded(dateAdded);
            itemExists = true;
            break;
        }
    }

    if (!itemExists) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Inventory");
        errorAlert.setHeaderText("Item ID not found!");
        errorAlert.setContentText("The item ID entered does not match an existing item in the inventory.");
        errorAlert.showAndWait();
        return;
    }

    

    Alert soldAlert = new Alert(AlertType.INFORMATION);
    soldAlert.setTitle("Inventory");
    soldAlert.setHeaderText("Item Updated!");
    soldAlert.showAndWait();
    inventoryTableView.refresh();

    saveInventoryToFile();
} // window displayed if item was correctly updated

    
    


    

    @FXML
    void handleOrderCostEntered(ActionEvent event) {

    }

    @FXML
    void handleSellItemIdEntered(ActionEvent event) {

    }

    @FXML
    void handleSellQuantityEntered(ActionEvent event) {

    }

    @FXML
    void handleSellSalePriceEntered(ActionEvent event) {

    }

    @FXML
    void sellClearButtonClicked(ActionEvent event) {
        sellItemIdTextField.setText("");
        sellSalePriceTextField.setText("");
        sellQuantityTextField.setText("");
        sellDateSoldDatePicker.setValue(null);
    } // clears entry fields

    @FXML
    void sellSubmitButtonClicked(ActionEvent event) {
       
        // window displayed if item is sold correctly
        Alert soldAlert = new Alert(AlertType.INFORMATION);
        soldAlert.setTitle("Inventory");
        soldAlert.setHeaderText("Item Sold!");
    
        String itemIdText = sellItemIdTextField.getText();
        String quantityText = sellQuantityTextField.getText();
        LocalDate dateSold = sellDateSoldDatePicker.getValue();
    

        if (itemIdText.trim().isEmpty() || quantityText.trim().isEmpty() || dateSold == null) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Invalid input");
            errorAlert.setContentText("All fields are required");
            errorAlert.showAndWait();
            return;
        }
    
        int itemId = Integer.parseInt(itemIdText);
        int quantity = Integer.parseInt(quantityText);
    
        for (Item item : itemList) {
            if (item.getItemId() == itemId) {
                if (quantity > item.getItemQuantity()) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Invalid input");
                    errorAlert.setContentText("Quantity exceeds item stock");
                    errorAlert.showAndWait();
                    return;
                }
                if (dateSold.isBefore(item.getDateAdded())) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Invalid input");
                    errorAlert.setContentText("Date sold cannot be earlier than date added");
                    errorAlert.showAndWait();
                    return;
                }
                int newQuantity = item.getItemQuantity() - quantity;
                item.setItemQuantity(newQuantity);
    
                soldAlert.showAndWait();
                inventoryTableView.refresh();
                saveInventoryToFile();
                return;
            }
        }
    
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Invalid input");
        errorAlert.setContentText("Item ID not found");
        errorAlert.showAndWait();
    } // window dispalyed when invalid inputs are entered
    

}
