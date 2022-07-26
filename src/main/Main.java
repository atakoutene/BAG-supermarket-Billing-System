/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Locale;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author atako
 */
public class Main extends Application {
    
    public Image appLogo = new Image(Main.class
            .getResourceAsStream("/images/sekud-logo.jpg")); 
    private static Stage stage;
    public static final String APP_NAME = "BAG";
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Locale.setDefault(Locale.ENGLISH);
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));           
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/sekud-logo.jpg")));
        Scene scene = new Scene(root);  
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    
    public void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(appLogo);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }    
    
    public static void closeView(Event event) {
        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    
    public  void loadHomeView(ActionEvent event) throws Exception{
        closeView(event);
        Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));           
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/sekud-logo.jpg")));
        Scene scene = new Scene(root);  
        stage.setTitle("BAG-APP");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();    
        
    }
    
    public  void loadLoginView(ActionEvent event) throws Exception{
        closeView(event);
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));           
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/sekud-logo.jpg")));
        Scene scene = new Scene(root);  
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();    
        
    }    
    
    public  void loadNewCustomerView() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/newCustomer.fxml"));           
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/sekud-logo.jpg")));
        Scene scene = new Scene(root);  
        stage.setTitle("Create an account");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.show();    
        
    }    
    
    public  void loadAddOrEitCategoryView() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/addOrEditCategory.fxml"));           
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/sekud-logo.jpg")));
        Scene scene = new Scene(root);  
        stage.setTitle("Add Or Edit a category");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();    
        
    }      
    
    public  void loadAddOrEitProductView() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/addOrEditProduct.fxml"));           
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/sekud-logo.jpg")));
        Scene scene = new Scene(root);  
        stage.setTitle("Add Or Edit a product");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();    
        
    }      
    
    public void loadCustomerDashboard(ActionEvent event) throws Exception{
        closeView(event);
        Parent root = FXMLLoader.load(getClass().getResource("/views/CustomerView.fxml"));           
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/sekud-logo.jpg")));
        Scene scene = new Scene(root);  
        stage.setTitle("BAG-APP");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();      
    }
    
    public void loadCashierDashboard(ActionEvent event) throws Exception{
        closeView(event);
        Parent root = FXMLLoader.load(getClass().getResource("/views/CashierView.fxml"));           
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/sekud-logo.jpg")));
        Scene scene = new Scene(root);  
        stage.setTitle("BAG-APP");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();        
    }    
    
    public static Stage getStage() {
        return stage;
    }    

    


   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
