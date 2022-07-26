/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.DatabaseConnectionManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import models.Category;

/**
 * FXML Controller class
 *
 * @author atako
 */
public class CategoryManagementController implements Initializable {
    

    @FXML
    private TableView<Category> tableView;

    @FXML
    private TableColumn<Category, Integer> id;

    @FXML
    private TableColumn<Category, String> name;

    @FXML
    private TableColumn<Category, String> description;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton edit;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXTextField search;
    
    private ArrayList<Category> categoriesList = new ArrayList<>();
    private ObservableList<Category> list ; 
    DatabaseConnectionManager manager = new DatabaseConnectionManager();
    
   

    @FXML
    void onAddOrEdit(ActionEvent event)throws Exception {
        try{
           
            FXMLLoader loader =  new FXMLLoader(getClass().getResource("/views/addOrEditCategory.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene((Pane)loader.load());
            stage.setScene(scene);
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/sekud-logo.jpg")));            
            AddOrEditCategoryController controller = loader.getController();
            controller.setCategoriesTable(tableView);
            if(event.getSource() == edit){
                if(tableView.getSelectionModel().getSelectedItem() == null){
                    (new Main()).showAlert(Alert.AlertType.ERROR, "No selection found!!", "You must select the category to be edited!!");                   
                }
                else{
                controller.setToBeSelected(tableView.getSelectionModel().getSelectedItem());
                controller.initWindow();
                stage.setTitle("Categories Space");
                stage.show();
                stage.setResizable(false);                
                }
            }else{
            controller.setToBeSelected(null);    
            stage.setTitle("Categories Space");
            stage.show();
            stage.setResizable(false);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void onDelete(ActionEvent event) {
        if(tableView.getSelectionModel().getSelectedItem() == null){
            (new Main()).showAlert(Alert.AlertType.ERROR,"No selection found!!","You must select the category to be deleted!!");                
        }
        else{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add((new Main()).appLogo);
        alert.setTitle("Delete selected category");
        alert.setHeaderText("Do you really want to delete this category?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);
        alert.showAndWait()
                .filter(response -> response.getButtonData() == ButtonBar.ButtonData.YES)
                .ifPresent((response) -> {
                    manager.deleteCategory(tableView.getSelectionModel().getSelectedItem());
                    tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
                    (new Main()).showAlert(Alert.AlertType.CONFIRMATION, "Deleted successfully", "Category deleted successfully!!");
                });               
        }
    }

    @FXML
    void onSearch(MouseEvent event) {
        ObservableList<Category> itemsInStock = FXCollections.observableArrayList();

        for(Category category:categoriesList){
            itemsInStock.add(category);
        }            
   //initial filtered list
        FilteredList<Category> filteredData = new FilteredList<>(itemsInStock, b -> true);
        
        search.textProperty().addListener((observable, oldValue, newValue)->{
            filteredData.setPredicate(category -> {
                //if no search value then display all records or whatever records it current have.
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                
                String searchKeyword = newValue.toLowerCase();
                if(category.getName().toLowerCase().indexOf(searchKeyword)> -1){
                    return true;
                }else{
                    return false; // no match found
                }
                
            });
        });
        
        SortedList<Category> sortedData = new SortedList<>(filteredData);
        
        //Bind sorteed result with inStockTable
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        
        //Apply filtered and sorted data to the inStockTable
        
        tableView.setItems(sortedData);
    }   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoriesList = manager.getCategories();
        list = FXCollections.observableArrayList(categoriesList);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(list);
        
       
    }    
    

    
    
}
