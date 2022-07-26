/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author atako
 */
public class Product implements Comparable<Product>{
    private String id;
    private String name;
    private float unitPrice;
    private Integer quantity;
    private Integer quantitySell;
    private String category;
    
    @Override
    public int compareTo(Product t) {
        return this.quantitySell.compareTo(t.quantitySell);
    }    
    
    public Product() {
    }

    public Product(String id, String name, float unitPrice, int quantity,  int quantitySell, String category) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.quantitySell = quantitySell;
        this.category = category;
    }

    public Product(String name, float unitPrice, int quantity, int quantitySell, String category) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.quantitySell = quantitySell;
        this.category = category;
    }
    

    public Product(String id, String name, float unitPrice, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.category = category;
    }
    
    

    public int getQuantitySell() {
        return quantitySell;
    }

    public void setQuantitySell(int quantitySell) {
        this.quantitySell = quantitySell;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    
}
