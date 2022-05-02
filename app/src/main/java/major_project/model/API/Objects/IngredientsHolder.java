package major_project.model.API.Objects;

import java.util.ArrayList;

public class IngredientsHolder {
    private ArrayList<Ingredients> ingredients;
    public IngredientsHolder(Ingredients ingredient){
        this.ingredients = new ArrayList<>();
        this.ingredients.add(ingredient);
    }
}
