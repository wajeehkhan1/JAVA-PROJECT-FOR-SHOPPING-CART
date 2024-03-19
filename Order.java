/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shoppingsystem.classes;

import java.util.ArrayList;
import java.util.List;


public class Order {
    private int orderId;
    private List<Product> products;
    private double totalPrice;

    // Constructor, getters, and setters

    public Order(int orderId, List<Product> products, double totalPrice) {
        this.orderId = orderId;
        this.products = products;
        this.totalPrice = totalPrice;
    }
    public Order() {
        this.products = new ArrayList<>();
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}
