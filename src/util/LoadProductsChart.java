
package util;

/**
 *
 * @author pacha
 */
import database.DatabaseConnectionManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import models.Product;

public class LoadProductsChart {

    DatabaseConnectionManager manager = new DatabaseConnectionManager();
    ArrayList<Product> productsList ;
    int countBestProducts = 0;

    public LoadProductsChart() {
        productsList = manager.getProducts();
        Collections.sort(productsList);
    }

    public BarChart<Number,String> getProductsChart() {

        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("products");
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("quantity Sell");

        XYChart.Series series = new XYChart.Series();
        series.setName("Ranking Of The Most Sell Products ");

        for (Product product : productsList) {
            if(countBestProducts > 5)
                break ;
            else{
                series.getData().add(new XYChart.Data(product.getQuantitySell(), product.getName()));
                countBestProducts++;
            }
        }

        BarChart<Number,String > barChart = new BarChart<>(xAxis, yAxis);
        barChart.getData().add(series);
        barChart.setBarGap(3);

        return barChart;

    }
    
    public void displayLabeledBarChart(VBox graphsContainer){
         graphsContainer.getChildren().add(new LoadProductsChart().getProductsChart());
         graphsContainer.setAlignment(Pos.CENTER);     
    }    
    

}
