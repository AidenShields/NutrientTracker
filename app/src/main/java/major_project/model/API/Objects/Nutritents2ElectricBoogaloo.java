package major_project.model.API.Objects;

import java.util.HashMap;

public class Nutritents2ElectricBoogaloo {
    private String label;
    private double quantity;
    private String unit;

    public Nutritents2ElectricBoogaloo(String label, double quantity, String unit){
        this.label = label;
        this.quantity = quantity;
        this.unit = unit;
    }
    public String toString(){
        return "Name:\t\t\t" + label +
                "\n Quantity:\t\t\t" + Math.round(quantity) + " " +unit + "\n\n";
    }
    public String getLabel(){
        return label;
    }
    public double getQuantity(){
        return quantity;
    }
    public void setQuantity(double quantity){
        this.quantity = quantity;
    }
}
