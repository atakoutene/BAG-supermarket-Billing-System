/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author atako
 */

import database.DatabaseConnectionManager;
import java.util.ArrayList;
import java.util.Collections;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import models.Customer;

public class LoadCustomersChart {
    ArrayList<Customer> customersList ;
    DatabaseConnectionManager manager = new DatabaseConnectionManager();

    public LoadCustomersChart() {     
        customersList = manager.getCustomers();
        Collections.sort(customersList); 
    }

    public BarChart<Number,String> getCustomersChart() {

        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Customers");
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("quantity purchased");

        XYChart.Series series = new XYChart.Series();
        series.setName("Ranking Of The Best Customers ");

        for (Customer customer : customersList) {
                series.getData().add(new XYChart.Data(customer.getQtyPurchased(), customer.getFname()));
        }

        BarChart<Number,String > barChart = new BarChart<>(xAxis, yAxis);
        barChart.getData().add(series);
        barChart.setBarGap(3);

        return barChart;

    }
    
    public void displayLabeledBarChart(VBox graphsContainer){
         graphsContainer.getChildren().add(new LoadCustomersChart().getCustomersChart());
         graphsContainer.setAlignment(Pos.CENTER);     
    }    
    
    
}
