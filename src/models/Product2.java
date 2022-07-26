/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author leopo
 */
public class Product2 {
    private float unitPrice; 
    private int qtity;
    private String name;
    private String id;
    private String description;
    
    public Product2(){
        
    }
    
    public Product2(String name, float unitPrice, int quantity){
        this.name = name;
        this.unitPrice = unitPrice;
        this.qtity = quantity;
    }
    
    public Product2(String name, float unitPrice){
        this.name = name;
        this.unitPrice = unitPrice;
    }    
    
    public Product2(String name, int quantity, float unitPrice, String id, String description){
        this.name = name;
        this.qtity = quantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.id = id;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQtity(int qtity) {
        this.qtity = qtity;
    }

    public void setName(String name) {
        this.name = name;
    }



    public float getUnitPrice() {
        return unitPrice;
    }

    public int getQtity() {
        return qtity;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
