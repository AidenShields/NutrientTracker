package major_project.view;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import major_project.model.API.Objects.Food;
import major_project.model.API.Objects.Measure;
import major_project.model.API.Objects.Nutritents2ElectricBoogaloo;
import major_project.model.Engine;
import major_project.view.DialogGuis.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Window {
    private Engine model;
    private final Scene scene;
    private ListView<Food> foodListView;
    private MenuBar menuBar;
    private VBox sideButtonBar;

    public Window(Engine model){
        this.model = model;
        BorderPane borderPane = new BorderPane();
        foodListView = new ListView<>();
        foodListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        buildMenu();
        buildSideMenu();

        borderPane.setCenter(foodListView);
        borderPane.setTop(menuBar);
        borderPane.setRight(sideButtonBar);

        this.scene = new Scene(borderPane);
    }


    public Scene getScene(){
        return scene;
    }

    public void buildMenu(){
        Menu actionMenu = new Menu("Actions");
        MenuItem selectIngredient = new MenuItem("Add Ingredient/Meal");
        selectIngredient.setOnAction(event -> {viewPossibleFoodsList(getIngredientInput());});

        MenuItem viewCurrentFood = new MenuItem("View Food In List");
        viewCurrentFood.setOnAction(event -> {viewCurrentFoodsList();});

        actionMenu.getItems().addAll(selectIngredient, viewCurrentFood);
        this.menuBar = new MenuBar();
        menuBar.getMenus().add(actionMenu);
    }

    public void buildSideMenu(){
        Button select = new Button("Select");
        select.setOnAction(event -> {selectFoodFromList();});

        Button viewNutrients = new Button("View Nutrients");
        viewNutrients.setOnAction(event -> {viewNutritionFromList();});

        Button setMaxNutrient = new Button("Set Max Nutrient");
        setMaxNutrient.setOnAction(event -> {setMax();});

        Button viewMaxes = new Button("View Maxes");
        viewMaxes.setOnAction(event -> {viewMaxes();});

        Button viewNutritionalTotals = new Button("View Totals");
        viewNutritionalTotals.setOnAction(event -> {viewNutritionalTotals();});

        Button viewBarChart = new Button("Bar Chart Totals");
        viewBarChart.setOnAction(event -> buildBarChart());

        this.sideButtonBar = new VBox(select, viewNutrients, setMaxNutrient, viewMaxes, viewNutritionalTotals, viewBarChart);
        sideButtonBar.setSpacing(10);
    }

    public String getIngredientInput(){
        return TextInput.getTextInput("INGREDIENT", "Ingredient Selector", "Please enter Ingredient");
    }

    public void viewPossibleFoodsList(String foodInput){
        foodListView.getItems().clear();
        ArrayList<Food> hintList = model.callFood(foodInput);
        if(hintList == null || hintList.size() == 0){               // (TODO) bad ik, didnt know where else to put it, will fix it by the final submission
            if(ConfirmationDialog.buildConfirmationDialog("ERROR", "INVALID INPUT", "Would you like to try again?")){
                viewPossibleFoodsList(getIngredientInput());
                return;
            }
            return;
        }
        for(Food food : hintList){
            foodListView.getItems().add(food);
        }
    }

    public void viewCurrentFoodsList(){
        foodListView.getItems().clear();
        foodListView.getItems().addAll(model.getCurrentFoodList());
    }

    public void selectFoodFromList(){
        if(!foodListView.getSelectionModel().isEmpty()){
            Food food = foodListView.getSelectionModel().getSelectedItem();
            Measure nutritionSize = NutritionSizeDialog.buildDialog("Choose Size", "Please select size", "Options", food.getMeasures());
            int NumberOfUnits = IntInput.getTextInput("Choose Amount", "Please Enter Unit Amount", "Please Unit Amount: ");
            food = model.updateAmount(food, nutritionSize, NumberOfUnits);
            if(model.addFood(food)){
                viewNutritionFromList();
                return;
            }
            else{
                ErrorMessage.ErrorMessage("Invalid Input", "You entered a invalid input please try again");
            }
            return;
        }
        ErrorMessage.ErrorMessage("No Food Selected", "Error: please select food to use this feature");

    }
    public void viewNutritionFromList(){
        if(!foodListView.getSelectionModel().isEmpty()){
            Food food = foodListView.getSelectionModel().getSelectedItem();
            if(food.getMeasure() == null){
                Measure nutritionSize = NutritionSizeDialog.buildDialog("Choose Size", "Please select size", "Options", food.getMeasures());
                int NumberOfUnits = IntInput.getTextInput("Choose Amount", "Please Enter Unit Amount", "Please Unit Amount: ");
                food = model.updateAmount(food, nutritionSize, NumberOfUnits);
                model.addNutritional(food);
            }
            InfoDialog.buildDialog("Nutrition Info", "Nutritional Info", food.getNutrients().toString().substring(1, food.getNutrients().toString().length() - 1).replace(",", "") + "\nWeight: \t\t" + Math.round(food.getMeasure().getWeight())  +
                    " grams");
            return;
        }
        ErrorMessage.ErrorMessage("No Food Selected", "Error: please select food to use this feature");
    }
    public void setMax(){
        String nutritionSelected = NutritientChoiceDialog.buildDialog("Nutrition Info", "Choose a Nutrient to Set Max", "Choose: ",model.getNutritionList());
        int maximumAmount = IntInput.getTextInput("Choose Amount", "Choose Maximum Amount", null);
        model.addMax(nutritionSelected, Double.valueOf(maximumAmount));
    }
    public void viewMaxes(){
        if(model.getMaxes() == null){
            ErrorMessage.ErrorMessage("Error", "Select Max First");
            return;
        }
        InfoDialog.buildDialog("Maxes", "Maxes Info", model.getMaxes().toString().replace("{", "").replace("}", "").replace(",", "\n").replace("=", "\t\t"));

    }
    public void viewNutritionalTotals(){
        if(model.getMaxes() == null){
            ErrorMessage.ErrorMessage("Error", "Select Max First");
            return;
        }
        InfoDialog.buildDialog("Current", "Current Values", model.getCurrentNutritionalTotalsString());
    }
    public void buildBarChart(){
        BarChartDialogBox.buildDialog("Chart", "Nutritional Maxes Bar Chart", model.getNutritionList(), model.getCurrentNutritionalTotals(), model.getMaxes());
    }
}
