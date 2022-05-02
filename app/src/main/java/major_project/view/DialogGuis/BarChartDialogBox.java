package major_project.view.DialogGuis;

import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import major_project.model.API.Objects.Nutritents2ElectricBoogaloo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BarChartDialogBox {
    public static void buildDialog(String title, String header, ArrayList<String> nutritionNames, ArrayList<Nutritents2ElectricBoogaloo> nutritionList, HashMap<String, Double> maxes){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.observableArrayList(nutritionNames));
        xAxis.setLabel("Nutrition");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");

        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle("Nutritional Bar Chart");

        for(Nutritents2ElectricBoogaloo nutrition: nutritionList){
            XYChart.Series<String, Number> addNutrition = new XYChart.Series<>();
            addNutrition.setName(nutrition.getLabel());
            if(maxes.containsKey(nutrition.getLabel())){
                addNutrition.getData().add(new XYChart.Data<>(nutrition.getLabel(), maxes.get(nutrition.getLabel())));
            }
            addNutrition.getData().add(new XYChart.Data<>(nutrition.getLabel(), nutrition.getQuantity()));
            stackedBarChart.getData().add(addNutrition);
        }
        GridPane.setVgrow(stackedBarChart, Priority.ALWAYS);
        GridPane.setHgrow(stackedBarChart, Priority.ALWAYS);
        GridPane pane = new GridPane();
        pane.setMaxWidth(Double.MAX_VALUE);
        pane.add(stackedBarChart, 0, 1);
        alert.getDialogPane().setContent(stackedBarChart);
        alert.showAndWait();
    }
}
