package major_project.model;

import major_project.model.API.Calls.FoodDataBaseAPI;
import major_project.model.API.Objects.Food;
import major_project.model.API.Objects.Measure;
import major_project.model.API.Objects.Nutritents2ElectricBoogaloo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Engine {
    private FoodDataBaseAPI API;
    private ArrayList<Food> currentFood;
    private HashMap<String, Double> maxes;
    public Engine(FoodDataBaseAPI API){
        this.API = API;
        currentFood = new ArrayList<>();
        maxes = new HashMap<>();
    }

    public ArrayList<Food> callFood(String food){
        return API.callFood(food);
    }

    public boolean addFood(Food foodInput){
        if(foodInput.getMeasure().getWeight() < 1){
            return false;
        }
        if(foodInput.getQuantity() < 1){
            return false;
        }
        API.addNutritional(foodInput);
        currentFood.add(foodInput);
        return true;
    }
    public void addNutritional(Food food){
        API.addNutritional(food);
    }
    public HashMap<String, Double> getMaxes(){
        return maxes;
    }
    public void addMax(String key, Double value){
        maxes.put(key, value);
    }
    public ArrayList<String> getNutritionList(){
        return API.getNutrientsNames();
    }

    public ArrayList<Food> getCurrentFoodList(){
        return currentFood;
    }

    public Food updateAmount(Food food, Measure measure, double amount){
        food.setMeasure(measure, amount);
        return food;
    }
    public String getCurrentNutritionalTotalsString(){
        HashMap<String, Nutritents2ElectricBoogaloo> returnMap = new HashMap<>();
        for(Food food: currentFood){
            ArrayList<Nutritents2ElectricBoogaloo> nutrients = food.getNutrients();
            for(Nutritents2ElectricBoogaloo nutrient: nutrients){
                if(returnMap.containsKey(nutrient.getLabel())){
                    returnMap.get(nutrient.getLabel()).setQuantity(returnMap.get(nutrient.getLabel()).getQuantity() + nutrient.getQuantity());
                }else{
                    returnMap.put(nutrient.getLabel(), nutrient);
                }
            }
        }
        ArrayList<Nutritents2ElectricBoogaloo> returnArray = new ArrayList<>();
        for(Map.Entry<String, Nutritents2ElectricBoogaloo> entry : returnMap.entrySet()){
            returnArray.add(entry.getValue());
        }
        return(returnArray.toString().replace("[", "").replace("]", "").replace(",",""));
    }
    public ArrayList<Nutritents2ElectricBoogaloo> getCurrentNutritionalTotals(){ // (TODO) merge with string version of function
        HashMap<String, Nutritents2ElectricBoogaloo> returnMap = new HashMap<>();
        for(Food food: currentFood){
            ArrayList<Nutritents2ElectricBoogaloo> nutrients = food.getNutrients();
            for(Nutritents2ElectricBoogaloo nutrient: nutrients){
                if(returnMap.containsKey(nutrient.getLabel())){
                    returnMap.get(nutrient.getLabel()).setQuantity(returnMap.get(nutrient.getLabel()).getQuantity() + nutrient.getQuantity());
                }else{
                    returnMap.put(nutrient.getLabel(), nutrient);
                }
            }
        }
        ArrayList<Nutritents2ElectricBoogaloo> returnArray = new ArrayList<>();
        for(Map.Entry<String, Nutritents2ElectricBoogaloo> entry : returnMap.entrySet()){
            returnArray.add(entry.getValue());
        }
        return(returnArray);
    }
}