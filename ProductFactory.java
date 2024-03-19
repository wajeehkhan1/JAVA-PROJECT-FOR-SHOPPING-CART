/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shoppingsystem.classes;

public class ProductFactory {
    public static Product createProduct(int id, String name, double price, String description) {
        
        return new Product(id, name, price, description);
    }
}