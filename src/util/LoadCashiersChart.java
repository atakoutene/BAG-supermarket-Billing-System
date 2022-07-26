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
import models.Cashier;

public class LoadCashiersChart {
    ArrayList<Cashier> cashiersList ;
    DatabaseConnectionManager manager = new DatabaseConnectionManager();

    public LoadCashiersChart() {
        cashiersList = manager.getCashiers();
        Collections.sort(cashiersList);
    }

    public BarChart<Number,String> getCashiersChart() {

        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Cashiers");
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("quantity registered");

        XYChart.Series series = new XYChart.Series();
        series.setName("Ranking Of The Best Cashiers ");

        for (Cashier cashier : cashiersList) {
                series.getData().add(new XYChart.Data(cashier.getQtyRegistered(), cashier.getId()));
        }

        BarChart<Number,String > barChart = new BarChart<>(xAxis, yAxis);
        barChart.getData().add(series);
        barChart.setBarGap(4);

        return barChart;

    }
    
    public void displayLabeledBarChart(VBox graphsContainer){
         graphsContainer.getChildren().add(new LoadCashiersChart().getCashiersChart());
         graphsContainer.setAlignment(Pos.CENTER);     
    }    
    
}
