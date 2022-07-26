/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseConnectionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.Main;
import models.Category;

/**
 * FXML Controller class
 *
 * @author atako
 */
public class AddOrEditCategoryController implements Initializable {
    
    private Category toBeSelected = new Category();

    public void setToBeSelected(Category toBeSelected) {
        this.toBeSelected = toBeSelected;
    }    

    public Category getToBeSelected() {
        return toBeSelected;
    }
    

    @FXML
    private JFXTextField refNumber;

    @FXML
    private JFXTextField name;
    

    @FXML
    private JFXTextArea description;    

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton cancel;
    
    private TableView<Category> categoriesTable;
    
    DatabaseConnectionManager manager = new DatabaseConnectionManager();    

    public void setCategoriesTable(TableView<Category> table) {
        this.categoriesTable = table;
    }
    
    public void initWindow(){
        refNumber.setText(toBeSelected.getId() + "");
        name.setText(toBeSelected.getName());
        description.setText(toBeSelected.getDescription());
    }    

    @FXML
    void onCancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onSave(ActionEvent event) {
        //Handle empty fields
        if(refNumber.getText().isEmpty() || name.getText().isEmpty() || description.getText().isEmpty() ){
            (new Main()).showAlert(Alert.AlertType.ERROR, "Error empty fields", "You must fill all the fields!!!");
        }     
        else{  
            //handle casting exception
     try{       
         //Creating a category object
     Category newCategory = new Category();
     newCategory.setId(new Integer(refNumber.getText()));
     newCategory.setName(name.getText());
     newCategory.setDescription(description.getText());
     if(toBeSelected != null){
         categoriesTable.getItems().remove(toBeSelected);
        //Update info in the database
        categoriesTable.getItems().add(newCategory);
        manager.postCategoryInfo(newCategory);
        (new Main()).showAlert(Alert.AlertType.CONFIRMATION, "Updated successfully", "Category information have been updated successfully!!");     
        categoriesTable.refresh();         
     }
     else{
     //Add category to the database
     categoriesTable.getItems().add(newCategory);
     manager.putCategory(newCategory);
     categoriesTable.refresh();
     (new Main()).showAlert(Alert.AlertType.CONFIRMATION, "Created successfully", "Category  has been created successfully!!");
     }
     Node source = (Node) event.getSource();
     Stage stage = (Stage) source.getScene().getWindow();
     stage.close();}
     catch(Exception ex){
         (new Main()).showAlert(Alert.AlertType.ERROR, "Wrong entry", "Category Id must be an integer!!");
     }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
