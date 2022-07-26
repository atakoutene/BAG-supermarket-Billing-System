/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import database.DatabaseConnectionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;
import models.Admin;
import models.Cashier;
import models.Customer;


/**
 *
 * @author atako
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnSignIn;

    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField pwdInput;

    @FXML
    private Label warningLabel;

    @FXML
    private Label welcomeText;
    
    @FXML
    private Label signUpLabel;

    @FXML
    private ComboBox<String> positionComboBox;
    static Admin admin;
    static Customer customer;
    static Cashier cashier;

    


    @FXML
    void cancelHandler(ActionEvent event) {
        // Close the window that originated the event
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void signInHandler(ActionEvent event) throws Exception {
        DatabaseConnectionManager manager = new DatabaseConnectionManager();

        if (positionComboBox.getValue().equals("Admin")) {
             admin = new Admin(loginInput.getText(), pwdInput.getText());
            Admin user = manager.checkAdminLogin(admin);
            if(user != null){
            (new Main()).showAlert(
                    Alert.AlertType.INFORMATION,
                    "Connection successful",
                    "You provided the correct credentials.");

            (new Main()).loadHomeView(event);
            }
            else{
            (new Main()).showAlert(
                    Alert.AlertType.ERROR,
                    "Connection unsuccessful",
                    "Incorrect credentials.");                
            }

        }
        else if(positionComboBox.getValue().equals("Customer")){
            customer = new Customer(loginInput.getText(), pwdInput.getText());
            Customer user = manager.checkCustomerLogin(customer);
            if(user != null){
            (new Main()).showAlert(
                    Alert.AlertType.INFORMATION,
                    "Connection successful",
                    "You provided the correct credentials.");
                // LOAD CUSTOMER VIEW
            (new Main()).loadCustomerDashboard(event);
            }
            else{
            (new Main()).showAlert(
                    Alert.AlertType.ERROR,
                    "Connection unsuccessful",
                    "Incorrect credentials.");                
            }           
        }
        else if(positionComboBox.getValue().equals("Cashier") ){
            cashier = new Cashier(loginInput.getText(), pwdInput.getText());
            Cashier user = manager.checkCashierLogin(cashier);
            if(user != null){
            (new Main()).showAlert(
                    Alert.AlertType.INFORMATION,
                    "Connection successful",
                    "You provided the correct credentials.");
                // LOAD CASHIER VIEW
            (new Main()).loadCashierDashboard(event);
            }
            else{
            (new Main()).showAlert(
                    Alert.AlertType.ERROR,
                    "Connection unsuccessful",
                    "Incorrect credentials.");                
            }               
        }
    }
    
        @FXML
    void signUpHandler(MouseEvent event) throws Exception {
        (new Main()).loadNewCustomerView();
    }

    public TextField getLoginInput() {
        return loginInput;
    }

    public PasswordField getPwdInput() {
        return pwdInput;
    }
    
    

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add the logo as label graphic to the welcome text
        setUpLogo();

        // Enable the validation of the form using "Enter" key
        loginInput.setOnKeyReleased((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                validateInput();
            }
        });
        pwdInput.setOnKeyReleased((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                validateInput();
            }
        });
        
        positionComboBox.getItems().addAll("Customer", "Cashier", "Admin");
        positionComboBox.setStyle("fx-color: blue");
        positionComboBox.setPromptText("Select a status");
    }

    private void validateInput() {
        String login = loginInput.getText();
        String password = pwdInput.getText();

        if (login.isEmpty()) {
            animateField(loginInput);
            return;
        }
        if (password.isEmpty()) {
            animateField(pwdInput);
            return;
        }

        if (login.length() < 6) {
            warningLabel.setText("Invalid username.");
            warningLabel.setVisible(true);
            return;
        }

        if (password.length() < 4) {
            warningLabel.setText("Password too short.");
            warningLabel.setVisible(true);
            return;
        }

        if (login.length() >= 6 && password.length() >= 4) {
            btnSignIn.fire();
        }

    }

    private void animateField(Node node) {
        // Set the bg color of the field to red
        node.setStyle("-fx-background-color: "
                + "linear-gradient(#FF5050, #FF0000); ");
        // Create a flashing animation on the field
        FadeTransition animation = new FadeTransition(
                Duration.millis(500), node);
        animation.setFromValue(0.2);
        animation.setToValue(1.0);
        animation.setCycleCount(5);
        animation.setAutoReverse(true);
        // Restore the original bg color of the field when the animation is over
        animation.setOnFinished(event -> {
            node.setStyle("-fx-background-color: "
                    + "rgba(255, 255, 255, 0.4); ");
        });
        animation.play();
    }

    private void setUpLogo() {
        Ellipse ellipse = new Ellipse(100, 70);
        ellipse.setStroke(Color.valueOf("#D1AC6D"));
        ellipse.setStrokeWidth(5);
        ellipse.setStrokeType(StrokeType.INSIDE);
        ellipse.setStrokeDashOffset(6);
        ellipse.setFill(Color.WHITE);

        ImageView logo = new ImageView(
                new Image(LoginController.class.
                        getResourceAsStream("/images/sekud-logo.jpg")));
        logo.setFitWidth(145);
        logo.setFitHeight(85);
        logo.setSmooth(true);

        Pane stack = new StackPane(ellipse, logo);
        welcomeText.setGraphic(stack);
        welcomeText.setContentDisplay(ContentDisplay.TOP);
        welcomeText.setGraphicTextGap(0);
        //welcomeText.setText("Log in to your account");
    }
}



