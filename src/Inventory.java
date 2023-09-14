import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Malik
// Invenotry class thayt creates an arraylist to hold all the item objects that are created
public class Inventory implements Serializable{
    private List<Item> itemList;
    
    public Inventory() {
        this.itemList = new ArrayList<>();
    }
    
    public void addItem(Item item) {
        itemList.add(item);
    }
    
    
}
