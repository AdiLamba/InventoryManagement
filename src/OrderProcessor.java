import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderProcessor implements Runnable {
    private HashMap<String, String> orders; // order ID -> status
    private String filename;

    public OrderProcessor(String filename) {
        this.orders = new HashMap<>();
        this.filename = filename;
    }

    Order order = new Order();

    @Override
    public void run() {
        while (true) {
            // read orders from file
            List<String> newOrders = readNewOrders(filename);
            List<Order> ordersList = extractOrders(newOrders);
            for (Order order : ordersList) {
                String orderId = order.getItemId();
                orders.put(orderId, "pending");
            }
            try {
                Thread.sleep(120000); // wait for 2 minutes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> readNewOrders(String filename) {
        List<String> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                orders.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> extractOrders(List<String> orderStrings) {
        List<Order> orders = new ArrayList<>();
        for (String orderString : orderStrings) {
            String[] orderData = orderString.split("\\s+");
            if (orderData.length < 5) {
                System.out.println("Invalid order data: " + orderString);
                continue;
            }
            String id = orderData[0];
            String name = orderData[1];
            int quantity = isNumber(orderData[2]) ? Integer.parseInt(orderData[2]) : 0;
            double price = isNumber(orderData[3]) ? Double.parseDouble(orderData[3]) : 0.0;
            double total = isNumber(orderData[4]) ? Double.parseDouble(orderData[4]) : 0.0;

            Order order = new Order(id, name, quantity, price, total);
            orders.add(order);
        }
        return orders;
    }

    private boolean isNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*
     * public List<Order> extractOrders(List<String> orderStrings) {
     * List<Order> orders = new ArrayList<>();
     * for (String orderString : orderStrings) {
     * String[] orderData = orderString.split("\\s+");
     * String id = orderData[0];
     * String name = orderData[1];
     * int quantity = 0;
     * double price = 0.0;
     * double total = 0.0;
     * try {
     * if (isNumber(orderData[2])) {
     * quantity = Integer.parseInt(orderData[2]);
     * }
     * if (isNumber(orderData[3])) {
     * price = Double.parseDouble(orderData[3]);
     * }
     * if (isNumber(orderData[4])) {
     * total = Double.parseDouble(orderData[4]);
     * }
     * } catch (NumberFormatException e) {
     * e.printStackTrace();
     * }
     * Order order = new Order(id, name, quantity, price, total);
     * orders.add(order);
     * }
     * return orders;
     * }
     * 
     * private boolean isNumber(String input) {
     * try {
     * Double.parseDouble(input);
     * return true;
     * } catch (NumberFormatException e) {
     * return false;
     * }
     * }
     */

    //private static final String ORDERS_FILE = "orders.txt";

    public void startProcessingOrders() {
        Timer timer = new Timer();
        timer.schedule(new OrderTask(), 0, 2 * 60 * 1000);
    }

    /*  private void processOrders() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            List<String> orderStrings = readNewOrders(ORDERS_FILE);
            List<Order> orders = extractOrders(orderStrings);
            Inventory inventory = new Inventory();
            inventory.updateInventory(orders);
            String line;
            while ((line = reader.readLine()) != null) {
                // process each line of the orders file
                // and update the inventory accordingly
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */

    private class OrderTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("Orders.txt updating...");
            //processOrders();
        }
    } 

}
