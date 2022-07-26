/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JTextArea;
import models.Product2;
import util.MyDate;
import database.DatabaseConnectionManager;

/**
 * FXML Controller class
 *
 * @author leopo
 */
public class CustomerViewHomeController implements Initializable {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnMyProfile;
    @FXML
    private Label dateTimeLabel;
    @FXML
    private TextField searchProductTextField;
    @FXML
    private TextArea billTextArea;
    @FXML
    private TableView<Product2> inStockTable;
    @FXML
    private TableView<Product2> cartTable;
    @FXML
    private TableColumn<Product2, String> inStockName;
    @FXML
    private TableColumn<Product2, SimpleFloatProperty> inStockUnitPrice;
    @FXML
    private TableColumn<Product2, SimpleIntegerProperty> inStockQuantity;
    @FXML
    private TableColumn<Product2, String> cartName;
    @FXML
    private TableColumn<Product2, SimpleFloatProperty> cartUnitPrice;
    @FXML
    private TableColumn<Product2, SimpleIntegerProperty> cartQuantity;
    
    MyDate dateUtil = new MyDate();
    ///////////////////////////////////////////////////////////////////////////////////
    ObservableList<Product2> productInStock = FXCollections.observableArrayList();
    ObservableList<Product2> productInCart = FXCollections.observableArrayList();
    
    //////////////////////////////////////////////////////////////////////////////////
    DatabaseConnectionManager manager = new DatabaseConnectionManager();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inStockName.setCellValueFactory(new PropertyValueFactory<Product2, String>("name"));
        inStockUnitPrice.setCellValueFactory(new PropertyValueFactory<Product2, SimpleFloatProperty>("unitPrice"));
        inStockQuantity.setCellValueFactory(new PropertyValueFactory<Product2, SimpleIntegerProperty>("qtity"));
        inStockTable.setItems(getProductInStock());
        // To be incapable of editing the table
        inStockTable.setEditable(false);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        cartName.setCellValueFactory(new PropertyValueFactory<Product2, String>("name"));
        cartUnitPrice.setCellValueFactory(new PropertyValueFactory<Product2, SimpleFloatProperty>("unitPrice"));
        cartQuantity.setCellValueFactory(new PropertyValueFactory<Product2, SimpleIntegerProperty>("qtity"));
        //cartTable.setItems(getProductInStock());
        // To be incapable of editing the table
        cartTable.setEditable(false);     
        
///////////////////////////////////////////////////////        dynamic search of products           ////////////////////////////////////////////////
/*      //initial filtered list
        FilteredList<Product> filteredData = new FilteredList<>(productInStock, b -> true);
        
        searchProductTextField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredData.setPredicate(product -> {
                //if no search value then display all records or whatever records it current have.
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                
                String searchKeyword = newValue.toLowerCase();
                if(product.getName().toLowerCase().indexOf(searchKeyword)> -1){
                    return true;
                }else{
                    return false; // no match found
                }
                
            });
        });
        
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        
        //Bind sorteed result with inStockTable
        sortedData.comparatorProperty().bind(inStockTable.comparatorProperty());
        
        //Apply filtered and sorted data to the inStockTable
        
        inStockTable.setItems(sortedData);
*/        
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
        
    }    

    
    @FXML
    private void exitFromSearchBar(MouseEvent event) {
       // inStockTable.setItems(productInStock);
        //refresh();
    }

    @FXML
    private void clickOnSearchBar(MouseEvent event) {
        
        ObservableList<Product2> itemsInStock = FXCollections.observableArrayList();

        for(Product2 product:productInStock){
            itemsInStock.add(product);
        }            
   //initial filtered list
        FilteredList<Product2> filteredData = new FilteredList<>(itemsInStock, b -> true);
        
        searchProductTextField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredData.setPredicate(product -> {
                //if no search value then display all records or whatever records it current have.
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                
                String searchKeyword = newValue.toLowerCase();
                if(product.getName().toLowerCase().indexOf(searchKeyword)> -1){
                    return true;
                }else{
                    return false; // no match found
                }
                
            });
        });
        
        SortedList<Product2> sortedData = new SortedList<>(filteredData);
        
        //Bind sorteed result with inStockTable
        sortedData.comparatorProperty().bind(inStockTable.comparatorProperty());
        
        //Apply filtered and sorted data to the inStockTable
        
        inStockTable.setItems(sortedData);        
    }
    
    
    @FXML
    private void btnProductDescription(ActionEvent event) {
    }

    @FXML
    private void btnAddToCart(ActionEvent event) {
       // Product2 selectedProduct = inStockTable.getSelectionModel().getSelectedItem();
        getQuantityNeeded();
        //Product newSelectedProduct = new Product2(selectedProduct.getName(), selectedProduct.getUnitPrice(), );
   //     productInCart.add(selectedProduct);
        //cartTable.getItems().clear();
   //     cartTable.getItems().add(newSelectedProduct);
   
        
    }

    @FXML
    private void btnRemoveFromCart(ActionEvent event) {
        if(cartTable.getItems().isEmpty()){
            billTextArea.setText("The Cart is Empty, cannot remove a product.");
        }
        else{
            int selectedID = cartTable.getSelectionModel().getSelectedIndex();
            int cartQtity =  cartTable.getSelectionModel().getSelectedItem().getQtity();
            String cartName = cartTable.getSelectionModel().getSelectedItem().getName();

            // search for the corresponding product in the instockTable
            int index=0;
            for(Product2 product:inStockTable.getItems()){
                if(product.getName().equals(cartTable.getItems().get(selectedID).getName())){
                    index = inStockTable.getItems().indexOf(product);
                }
            }
            // increment the quantity of the corresponding product
            inStockTable.getItems().get(index).setQtity(inStockTable.getItems().get(index).getQtity() +cartQtity);
            cartTable.getItems().remove(selectedID);
            billTextArea.clear();

            // update quatities in the database
            manager.addQuantityInStockInDatabase(cartName, cartQtity);
            manager.removeQuantitySoldInDatabase(cartName, cartQtity);

            bill();
            refresh();        
        }
    }

    @FXML
    private void btnGenerateBill(ActionEvent event) {
        billTextArea.clear();
        if(cartTable.getItems().isEmpty())
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
        for(int i = 0; i<cartTable.getItems().size(); i++){
            billTextArea.setText(billTextArea.getText()+ "--------------------------------------------------------------------------------------------------------------\n");
            String proName = (String)cartTable.getItems().get(i).getName();
            double proPrice = cartTable.getItems().get(i).getUnitPrice();
            int proQtity = cartTable.getItems().get(i).getQtity();
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

    public  ObservableList<Product2> getProductInStock(){
        
        productInStock = manager.getProductInStockFromDatabase();
//        productInStock.addAll(new Product2("Lenovo EarBud", (float)25000.0, 120),
//                                new Product2("Seagate HDD 5To", (float)95000.0, 250),
//                                new Product2("SanDisk Flash 64Go", (float)12000.0, 200 ),
//                                new Product2("Whiteboard marker", (float)500.0, 500 ),
//                                new Product2("Wooden Duster", (float)400.0, 450 ),
//                                new Product2("WhiteBoard 1.2m x 0.9m", (float)30000.0, 100 ),
//                                new Product2("Extra Fresco Juice", (float)350.0, 250 ),
//                                new Product2("Academic Box", (float)1000.0, 250 ),
//                                new Product2("Inacopia A4 80g/m2", (float)3000.0, 150 ),
//                                new Product2("Scientific Calculator", (float)1500.0, 200 ),
//                                new Product2("Electrolux Fridge", (float)100000.0, 50 ),
//                                new Product2("Fan aifa SINGAPORE", (float)20000.0, 130 ),
//                                new Product2("Bicycle", (float)35000.0, 50 ),
//                                new Product2("Iron SINSUNG", (float)14500.0, 155 ));
        
        return productInStock;
    }
    
    public void refresh(){
        //searchProductTextField.setText(null);
       
        //inStockTable.setItems(inStockTable.getItems());
        
        //refreshing the inStockTable
        inStockTable.refresh();
    }    
    
    String qtity;
    String name;
    private void getQuantityNeeded() {
        
        Stage stage = new Stage();
        TextField qtityTextField = new TextField();
        Button bt1 = new Button("OK");
        bt1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
               qtity = qtityTextField.getText();
               if(Integer.parseInt(qtity)<0){
                   billTextArea.setText("Quantity cannot be negative");
               }
               else{
               
                   if(inStockTable.getSelectionModel().getSelectedItem().getQtity()>=Integer.parseInt(qtity)){
                       Product2 selectedProduct = inStockTable.getSelectionModel().getSelectedItem();
                       name = inStockTable.getSelectionModel().getSelectedItem().getName();
                       Product2 newSelectedProduct = new Product2(selectedProduct.getName(), selectedProduct.getUnitPrice(), Integer.parseInt(qtity));

                       selectedProduct.setQtity(selectedProduct.getQtity()-Integer.parseInt(qtity));
                       productInCart.add(selectedProduct);
                       cartTable.getItems().add(newSelectedProduct);

                        manager.removeQuantityInStockInDatabase(name, Integer.parseInt(qtity));
                        manager.addQuantitySoldInDatabase(name, Integer.parseInt(qtity));

                       refresh();
                       stage.close();
                   }
                   else{
                       billTextArea.setText("Product is not available or Product is limited");
                   }                   
               }

            }
        });
        VBox root = new VBox();
        root.getChildren().addAll(qtityTextField, bt1);
        Scene scene = new Scene(root);
        stage.setTitle("Enter Your Quantity");
        stage.setScene(scene);
        stage.setWidth(450);
        stage.show();
        
       
    }
    
   
    
    
}
