package major_project.model.API.Objects;

public class Measure {
    private String uri;
    private String label;
    private double weight;

    public Measure(String uri, String label, double weight){
        this.uri = uri;
        this.label = label;
        this.weight = weight;
    }
    public String toString(){
        return
                "Type: \t" + label + "\n" +
                "Weight: \t " + Math.round(weight) + " gram(s)";
    }
    public double getWeight(){
        return weight;
    }
    public void setWeight(double newWeight){
        weight = newWeight;
    }
    public String getUri(){
        return uri;
    }
}
