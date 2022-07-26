/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseConnectionManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.Main;
import models.Category;
import models.Product;

/**
 * FXML Controller class
 *
 * @author atako
 */
public class AddOrEditProductController implements Initializable {
    
    private Product toBeSelected = new Product();
    
    private Product newProduct = new Product();
    
    private TableView<Product> productsTable;
    
    private ArrayList<Category> categoryList  = new ArrayList<>() ;
    
    private String[] list2 ;
    
    private DatabaseConnectionManager manager = new DatabaseConnectionManager();    
       
    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXTextField unitPrice;

    @FXML
    private JFXTextField qtyInStock;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    void onCancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onSave(ActionEvent event) {
        //Handle empty fields
        if(id.getText().isEmpty() || name.getText().isEmpty() || unitPrice.getText().isEmpty() || qtyInStock.getText().isEmpty() || categoryComboBox.getSelectionModel().getSelectedItem() == null){
            (new Main()).showAlert(Alert.AlertType.ERROR, "Error empty fields", "You must fill all the fields!!!");
        }
        else{    
            //handle casting exception
     try{       
     newProduct.setId(id.getText());
     newProduct.setName(name.getText());
     newProduct.setUnitPrice(new Float(unitPrice.getText()));
     newProduct.setQuantity(new Integer(qtyInStock.getText()));
     newProduct.setCategory(categoryComboBox.getSelectionModel().getSelectedItem());
     if(toBeSelected != null){
         productsTable.getItems().remove(toBeSelected);
        productsTable.getItems().add(newProduct);
        manager.postProductInfo(newProduct);
        (new Main()).showAlert(Alert.AlertType.CONFIRMATION, "Updated successfully", "Product information have been updated successfully!!");     
        productsTable.refresh();         
     }
     else{
     productsTable.getItems().add(newProduct);
     manager.putProduct(newProduct);
     (new Main()).showAlert(Alert.AlertType.CONFIRMATION, "Created successfully", "Product  has been created successfully!!"); 
     
     productsTable.refresh();
     }
    
     Node source = (Node) event.getSource();
     Stage stage = (Stage) source.getScene().getWindow();
     stage.close();
     }
    catch(NumberFormatException ex){
         (new Main()).showAlert(Alert.AlertType.ERROR, "Wrong entries", "Unit price  must be a float and quantity must be an integer!!");
     }
        }
    }


    public void setProductsTable(TableView<Product> table) {
        this.productsTable = table;
    }
    
    public void setToBeSelected(Product toBeSelected) {
        this.toBeSelected = toBeSelected;
    }        

    public Product getToBeSelected() {
        return toBeSelected;
    }
    
    
    public void initWindow(){
        int temp = 0;
        categoryList = manager.getCategories();
        list2 = new String[categoryList.size()];
        for (int i = 0; i < list2.length; i++) {
            list2[i] = categoryList.get(i).getName();
            
        }
        id.setText(toBeSelected.getId() );
        name.setText(toBeSelected.getName());
        unitPrice.setText(toBeSelected.getUnitPrice() + "");
        qtyInStock.setText(toBeSelected.getQuantity() + "");
        //get the index of the category of the selected product
        for (int i = 0; i < list2.length; i++) {
            if(list2[i].equals(toBeSelected.getCategory())){
                temp = i ;
            }
            
        }
        categoryComboBox.getSelectionModel().select(list2[temp]);
    }     
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryList = manager.getCategories();
        for (int i = 0; i < categoryList.size(); i++) {
            categoryComboBox.getItems().add(categoryList.get(i).getName());
            
        }
        
        categoryComboBox.setStyle("fx-color: blue");
    }    
    
}
