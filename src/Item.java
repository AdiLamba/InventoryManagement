import java.io.Serializable;
import java.time.LocalDate;

//@Malik
//Item class that models each inventory object along with its details
public class Item implements Serializable {
    private int itemId;
    private String itemName;
    private double itemPrice;
    private int itemQuantity;
    private double orderPrice;
    private int reorderPoint;
    private LocalDate expiryDate;
    private LocalDate dateAdded;



    // constructor with all item details
    public Item(int id, String name, double price, int quantity, double orderPrice, int reorderPoint, LocalDate expiryDate, LocalDate dateAdded) {
        this.itemId = id;
        this.itemName = name;
        this.itemPrice = price;
        this.itemQuantity = quantity;
        this.orderPrice = orderPrice;
        this.reorderPoint = reorderPoint;
        this.dateAdded = dateAdded;
        this.expiryDate = expiryDate;
        
    } 

    
    // getters and setters for item details
    public int getItemId() {
        return itemId;
    }
    
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public double getItemPrice() {
        return itemPrice;
    }
    
    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    public int getItemQuantity() {
        return itemQuantity;
    }
    
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    
    public double getOrderPrice() {
        return orderPrice;
    }
    
    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }
    
    public int getReorderPoint() {
        return reorderPoint;
    }
    
    public void setReorderPoint(int reorderPoint) {
        this.reorderPoint = reorderPoint;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }


    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    
    public LocalDate getDateAdded() {
        return dateAdded;
    }


    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }
}
