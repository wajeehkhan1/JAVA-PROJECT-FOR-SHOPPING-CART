package shoppingsystem.classes;

import java.sql.SQLException;

public class CartBuilder {
    private Cart cart;

    public CartBuilder(){
        this.cart = Cart.getInstance();
    }

    public CartBuilder addItem(int productId, int quantity) {
        cart.addItem(productId, quantity);
        return this;
    }

    public CartBuilder removeItem(int productId) {
        cart.removeItem(productId);
        return this;
    }
    
    public CartBuilder editItem(int productId, int newQuantity) {
        cart.editItem(productId, newQuantity);
        return this;
    }
    
    public double totalPriceCart() {
        return cart.calculateTotalPriceofCart();
    }

    public Cart build() {
        return cart.getInstance();
    }
}
