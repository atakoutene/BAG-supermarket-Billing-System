/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import models.Admin;
import util.LoadCashiersChart;
import util.LoadCustomersChart;
import util.LoadProductsChart;
import util.MyDate;

/**
 * FXML Controller class
 *
 * @author atako
 */
public class HomeController implements Initializable {
    
   @FXML
    private BorderPane homeContainer;
   

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnCategoryManagement;

    @FXML
    private Button btnProductManagement;

    @FXML
    private Label dateTimeLabel;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox mainContainer;
    
    
    private final Label title1 = new Label("Statistics");
    private final HBox mainTitle1 = new HBox(title1);
    
    private final Label title2 = new Label("Most Sell Products");
    private final HBox mainTitle2 = new HBox(title2);    
    
    private final Label title3 = new Label("Best Customers");
    private final HBox mainTitle3 = new HBox(title3);
    
    private final Label title4 = new Label("Best Cashier");
    private final HBox mainTitle4 = new HBox(title4); 
    
    private final MyDate dateUtil = new MyDate(); 
    private Admin adminInfos;    
    
    private  BorderPane categoryManagementContainer = new BorderPane();
    private  BorderPane productManagementContainer = new BorderPane();
    

    
    @FXML
    void loadCategoryManagement(ActionEvent event) {
        mainContainer.getChildren().setAll(categoryManagementContainer);
    }

    @FXML
    void loadHome(ActionEvent event) throws Exception {
        mainContainer.getChildren().clear();
        setHomeView();
    }

    @FXML
    void loadProductManagement(ActionEvent event) throws Exception{
        mainContainer.getChildren().setAll(productManagementContainer);
    }

    @FXML
    void signOutHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add((new Main()).appLogo);
        alert.setTitle("Sign Out");
        alert.setHeaderText("Do you really want to log out?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);
        alert.showAndWait()
                .filter(response -> response.getButtonData() == ButtonBar.ButtonData.YES)
                .ifPresent((response) -> {
                    try {
                        (new Main()).loadLoginView(event);
                    } catch (Exception ex) {
                        System.out.println("SIGN OUT HANDLER ERROR: "
                                + ex.getMessage());
                    }
                });        

    }
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setting up the pseudo of the user
        setWelcomeLabel();
        //Setting up the date
        setDateTimeLabel();
        //Setting up the home view
        setHomeView();
        //Setting up the categoryManagement view
       try {
           Parent root = FXMLLoader.load(getClass().getResource("/views/categoryManagement.fxml"));
           categoryManagementContainer = (BorderPane) root;
       } catch (IOException ex) {
           Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
       }
       //Setting up the ProductManagement view
       try {
           Parent root = FXMLLoader.load(getClass().getResource("/views/productManagement.fxml"));
           productManagementContainer = (BorderPane) root;
       } catch (IOException ex) {
           Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        
    }    
    
    private void setHomeView(){
        mainContainer.getChildren().add( mainTitle1);
        mainTitle1.setAlignment(Pos.CENTER);
        mainContainer.getChildren().add( mainTitle2);        
        (new LoadProductsChart()).displayLabeledBarChart(mainContainer);
        mainContainer.getChildren().add( mainTitle3);
        (new LoadCustomersChart()).displayLabeledBarChart(mainContainer);
        mainContainer.getChildren().add( mainTitle4);
        (new LoadCashiersChart()).displayLabeledBarChart(mainContainer);        
    }
    
    private void setDateTimeLabel() {
        dateTimeLabel.setText(dateUtil.toString());
    }    
    
    private void setWelcomeLabel(){
        adminInfos = LoginController.admin;
        welcomeLabel.setText("Welcome " + adminInfos.getId());
    }

}
