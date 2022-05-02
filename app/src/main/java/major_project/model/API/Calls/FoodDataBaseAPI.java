package major_project.model.API.Calls;

import major_project.model.API.Objects.Food;

import java.util.ArrayList;

public interface FoodDataBaseAPI {
    ArrayList<Food> callFood(String food);
    void addNutritional(Food food);
    ArrayList<String> getNutrientsNames();
}
