package shoppingsystem.classes;



public class CartItem {
    private int productId;
    private int quantity;
    private double price;
    private String name;
    private String description;

    // Constructor, getters, and setters

    public CartItem(int productId, int quantity, double price, String name, String description) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.description = description;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
