/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXTextField;
import database.DatabaseConnectionManager;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JTextArea;
import models.Product2;
import util.MyDate;
/**
 * FXML Controller class
 *
 * @author leopo
 */
public class CashierViewHomeController implements Initializable {

    @FXML
    private TableView<Product2> cartTableView;
    @FXML
    private TableColumn<Product2, String> cartName;
    @FXML
    private TableColumn<Product2, SimpleFloatProperty> cartUnitPrice;
    @FXML
    private TableColumn<Product2, SimpleIntegerProperty> cartQuantity;
    @FXML
    private JFXTextField productCodeTextField;
    @FXML
    private JFXTextField productQuantityTextField;
    @FXML
    private TextArea billTextArea;
    
    MyDate dateUtil = new MyDate();
    
    ObservableList<Product2> productInCart = FXCollections.observableArrayList();
    
    DatabaseConnectionManager manager = new DatabaseConnectionManager();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cartName.setCellValueFactory(new PropertyValueFactory<Product2, String>("name"));
        cartUnitPrice.setCellValueFactory(new PropertyValueFactory<Product2, SimpleFloatProperty>("unitPrice"));
        cartQuantity.setCellValueFactory(new PropertyValueFactory<Product2, SimpleIntegerProperty>("qtity"));
        // To be incapable of editing the table
        cartTableView.setEditable(false);     

    }    

    @FXML
    private void btnRegisterProduct(ActionEvent event) {

            
            
             Product2 product = manager.getRegisteredProductByCashier(productCodeTextField.getText());
            if(productCodeTextField.getText() == null || productQuantityTextField.getText() == null /*|| Integer.parseInt(productQuantityTextField.getText()) <= 0*/ 
                ||productQuantityTextField.getText().isEmpty() || productCodeTextField.getText().isEmpty() || /*product.getName().isEmpty() ||*/ product.getName() == null){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Invalid Entry");
                a.setHeaderText("Error");
                a.setContentText("Product code do not exist");
                a.show();
                refresh();
                
            }
            else{
                
             if(Integer.parseInt(productQuantityTextField.getText()) <= 0){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Invalid Entry");
                a.setHeaderText("Error");
                a.setContentText("quantity can not be negative");
                a.show();
                refresh();
             }
             else{
                Product2 product1 = new Product2(product.getName(), product.getUnitPrice(), Integer.parseInt(productQuantityTextField.getText()));
//            Product2 product = new Product2(var1.getText(),var2.getText(),productQuantityTextField.getText());      // Make the program to pop up an Alert in case the user did not enter the name or the description
            //System.out.println(catName.getText());
                productInCart = cartTableView.getItems();
                productInCart.add(product1);
                cartTableView.setItems(productInCart);
                
                manager.addQuantitySoldInDatabase(product.getName(), Integer.parseInt(productQuantityTextField.getText()));
                manager.removeQuantityInStockInDatabase(product.getName(), Integer.parseInt(productQuantityTextField.getText()));
                
                refresh();
            }
            }
        
    
    
    }

    @FXML
    private void btnGenerateBill(ActionEvent event) {
        billTextArea.clear();
        if(cartTableView.getItems().isEmpty())
            billTextArea.setText("There is no bill.");
        else
            bill();  
    }

    @FXML
    private void btnPrintBill(ActionEvent event) {
        try{
            System.out.println(billTextArea.getText());
            JTextArea area = new JTextArea(billTextArea.getText());
            area.print();
        }catch(Exception e){
            
        }
    }
 
    public void refresh(){
        productCodeTextField.setText(null);
        productQuantityTextField.setText(null);
    }

    public void bill(){ 
        
        billTextArea.setText(billTextArea.getText()+ "                                        BAG SUPERMARKET                                 \n");
        billTextArea.setText(billTextArea.getText()+ "*************************************************************************************************\n");
       // billTextArea.setText(billTextArea.getText()+ "*****************************************************************************\n");
        billTextArea.setText(billTextArea.getText()+ "                                         Bill Preview                            \n");
       // billTextArea.setText(billTextArea.getText()+ "*****************************************************************************\n");
        billTextArea.setText(billTextArea.getText()+ "*************************************************************************************************\n");
        
        //Heading
        billTextArea.setText(billTextArea.getText()+ "           Product  " + "\t\t" + "  Price  " + "\t\t" + "  Amount  " + "\n");
        double total = 0.0;                          
        for(int i = 0; i<cartTableView.getItems().size(); i++){
            billTextArea.setText(billTextArea.getText()+ "--------------------------------------------------------------------------------------------------------------\n");
            String proName = (String)cartTableView.getItems().get(i).getName();
            double proPrice = cartTableView.getItems().get(i).getUnitPrice();
            int proQtity = cartTableView.getItems().get(i).getQtity();
            total = total + proPrice*proQtity;
            billTextArea.setText(billTextArea.getText()+ proName + "\t\t" + proPrice + "\t\t" + proQtity + "\n");
            
            
        }
        
        billTextArea.setText(billTextArea.getText()+ "*************************************************************************************************\n");
       
        billTextArea.setText(billTextArea.getText()+ "                                                         Total: " + total +"\n");
        billTextArea.setText(billTextArea.getText()+ "*************************************************************************************************\n");
        
        billTextArea.setText(billTextArea.getText()+ "                                                          Date: "+ dateUtil.toString()+ "\n");
        
        billTextArea.setText(billTextArea.getText()+ "*************************************************************************************************\n");
        
        Calendar calendar = new GregorianCalendar();
        
        billTextArea.setText(billTextArea.getText()+ "                                                          Time: "+ calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND) + "\n");
 
        billTextArea.setText(billTextArea.getText()+ "*************************************************************************************************\n");
        billTextArea.setText(billTextArea.getText()+ "                                 YOUR SATISFACTION OUR PRIORTITY :)                                  \n");

        
    }

}
