/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.File;

//import javafx.beans.Observable;


/**
 *
 * @author leopo
 */
public class Bill {
    
    private int id;
    private String customerId;
    private String cashierId;
    private File billFile;
    //private Cart cart;
    //private ObservableList<Product> cartt = observableArrayList();                 /// Make research on ObservableList
    public Bill(){
        
    }
    
    public  Bill(int id, String customerId, String cashierId, File billFile){
        this.id = id;
        this.customerId = customerId;
        this.cashierId = cashierId;
        this.billFile = billFile;
    }
    
    
    
//    public Bill(Cart cart){
//        this.cart = cart;   
//    }
    
//    public Bill(ObservableList<Product> cartt){
//        this.cartt = cartt; 
//    }


//    public Cart getCart() {
//        return cart;
//    }

//    public ObservableList<Product> getCartt() {
//        return cartt;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public File getBillFile() {
        return billFile;
    }

    public void setBillFile(File billFile) {
        this.billFile = billFile;
    }
    
}