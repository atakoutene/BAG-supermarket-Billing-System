/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.DatabaseConnectionManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Main;
import util.MyDate;
/**
 * FXML Controller class
 *
 * @author leopo
 */
public class CustomerViewController implements Initializable {
    
    private AnchorPane homeContainer;
    private AnchorPane profileContainer;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnMyProfile;
    @FXML
    private Label dateTimeLabel;
    @FXML
    private BorderPane mainContainerBorderPane;
    @FXML
    private ScrollPane addedScrollPane;    
    @FXML
    private AnchorPane addedAnchorPane;    
    
    MyDate dateUtil = new MyDate();

    DatabaseConnectionManager manager = new DatabaseConnectionManager();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
// load the pseudo of current customer                                     /////////////////////////////////////////////////////////////////////        
        welcomeLabel.setText("Welcome "+ LoginController.customer.getRefNumber());
        setDateTimeLabel();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/CustomerViewHome.fxml"));
            homeContainer = (AnchorPane)root;
            addedAnchorPane.getChildren().add(homeContainer);
        } catch (IOException ex) {
            Logger.getLogger(CustomerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void signOutHandler(ActionEvent event) {
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

    @FXML
    private void loadHome(ActionEvent event) {
        addedAnchorPane.getChildren().clear();
        addedAnchorPane.getChildren().add(homeContainer);
    }

    @FXML
    private void loadMyProfile(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/CustomerViewProfile.fxml"));
            mainContainerBorderPane.getChildren().clear();
            profileContainer = (AnchorPane)root;
            mainContainerBorderPane.getChildren().add(profileContainer);
        } catch (IOException ex) {
            Logger.getLogger(CustomerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }  
    
    private void setDateTimeLabel(){
        dateTimeLabel.setText(dateUtil.toString());
    }

}
