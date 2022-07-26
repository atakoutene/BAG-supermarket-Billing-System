/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseConnectionManager;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import main.Main;
import models.Customer;

/**
 * FXML Controller class
 *
 * @author atako
 */
public class NewCustomerController implements Initializable {
    
  @FXML
    private JFXTextField userNameInputField;

    @FXML
    private JFXTextField fnameInputField;

    @FXML
    private JFXTextField lnameInputField;

    @FXML
    private JFXTextField phoneNumberInputField;

    @FXML
    private JFXTextField emailInputField;

    @FXML
    private JFXDatePicker dobInputField;

    @FXML
    private JFXPasswordField pwdInputField;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton cancel;
    
    DatabaseConnectionManager manager = new DatabaseConnectionManager();

    @FXML
    void onCancel(ActionEvent event) throws Exception{
        (new Main()).loadLoginView(event);
    }

    @FXML
    void onSave(ActionEvent event) throws Exception{
        String email = emailInputField.getText();
        if(userNameInputField.getText().isEmpty()|| lnameInputField.getText().isEmpty() || phoneNumberInputField.getText().isEmpty() || emailInputField.getText().isEmpty()
                || pwdInputField.getText().isEmpty() || dobInputField.getValue() == null){
            (new Main()).showAlert(Alert.AlertType.ERROR,"Empty fields", "You must fill all the field");
        }
        else if(!email.contains("@") || email.charAt(email.length() - 1) == '@' || email.charAt(email.indexOf("@") + 1) == '.'){
            (new Main()).showAlert(Alert.AlertType.ERROR,"Invalid email address", "You must follow this pattern xxx@sss.ooo");
        }
        else if(pwdInputField.getText().length() < 4){
            (new Main()).showAlert(Alert.AlertType.ERROR,"Invalid password", "You password must contain at least 4 characters!!");
        }
        else{
            try{
            Customer customer = new Customer(userNameInputField.getText(),
                    fnameInputField.getText(), lnameInputField.getText(), 
                    emailInputField.getText(), convertLocalDateIntoDateObject(dobInputField.getValue()),
                    new Integer(phoneNumberInputField.getText()),123, pwdInputField.getText());
            manager.putCustomer(customer);
            (new Main()).showAlert(Alert.AlertType.CONFIRMATION,"Saved Succesfully", "Your account has been created successfully");
            System.out.println(customer.toString());
            (new Main()).loadLoginView(event);
            }catch(NumberFormatException ex){
                (new Main()).showAlert(Alert.AlertType.ERROR,"Wrong Entry", "Phone number Must be an integer!! ");
            }
        }

            //convert local date into sql date to be stored into the database!
    }    public Date convertLocalDateIntoDateObject(LocalDate localDate){
        //default time zone
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
            return sqlDate;
            
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
