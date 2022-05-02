package major_project.model.API.Objects;

import java.util.ArrayList;

public class Ingredients {
    private int quantity;
    private String measureURI;
    private String foodId;


    public Ingredients(int quantity, String measureURI, String foodId){
        this.quantity = quantity;
        this.measureURI = measureURI;
        this.foodId = foodId;
    }

}
