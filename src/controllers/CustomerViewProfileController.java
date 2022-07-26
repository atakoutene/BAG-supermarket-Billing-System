/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import database.DatabaseConnectionManager;
import models.Profile;
/**
 * FXML Controller class
 *
 * @author leopo
 */
public class CustomerViewProfileController implements Initializable {

    
    DatabaseConnectionManager manager = new DatabaseConnectionManager();
    
    @FXML
    private Label nameValue;
    @FXML
    private Label phoneNumberValue;
    @FXML
    private Label addressValue;
    @FXML
    private Label emailValue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        //  pass the id of the current user.   //////////////////////////////////////////////////////////////
        Profile profile = manager.getCustomerProfile(LoginController.customer.getRefNumber());
        
        nameValue.setText(profile.getfName() +" "+ profile.getlName());
        phoneNumberValue.setText(String.valueOf(profile.getPhoneNumber()));
        addressValue.setText(profile.getDob());
        emailValue.setText(profile.getEmail());
        
        
    }    
    
}
