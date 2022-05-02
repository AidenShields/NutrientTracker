package major_project.model.API.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Food{
    private String foodId;
    private String label;
    private ArrayList<Nutritents2ElectricBoogaloo> nutrientsList;
    private Measure measure;
    private ArrayList<Measure> measures;
    private double quantity;
    public Food(String foodId, String label, ArrayList<Measure> measures){
        this.foodId = foodId;
        this.label = label;
        this.measures = measures;
        this.quantity = 0;
    }

    public void setMeasure(Measure measure, double quantity){
        double newWeight = measure.getWeight() * quantity;
        measure.setWeight(newWeight);
        this.measure = measure;
        this.quantity = quantity;
    }
    public void addNutrients(ArrayList<Nutritents2ElectricBoogaloo> nutrients){
        this.nutrientsList = nutrients;
    }
    public Measure getMeasure(){
        return measure;
    }
    public double getQuantity(){
        return quantity;
    }
    public String getFoodId(){
        return foodId;
    }

    public void setMeasures(ArrayList<Measure> measuresInput){
        this.measures = measuresInput;
    }
    public ArrayList<Measure> getMeasures(){
        return measures;
    }
    public ArrayList<Nutritents2ElectricBoogaloo> getNutrients(){
        return nutrientsList;
    }

    public String toString(){
        if(measure == null)
            return label;
        else{
            return "Name: \t" + label + "\n" + measure + "\nQuantity: \t" + Math.round(quantity);
        }
    }
}
