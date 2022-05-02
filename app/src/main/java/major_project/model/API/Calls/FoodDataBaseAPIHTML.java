package major_project.model.API.Calls;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import major_project.model.API.Objects.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;

public class FoodDataBaseAPIHTML implements FoodDataBaseAPI {
    private final String authToken = "?app_id=214e6e25&app_key=205ab21162d1ccd3e972900c81197374";
    private final String requestURL = "https://api.edamam.com/api/food-database/v2/parser" + authToken;
    private final String postURL = "https://api.edamam.com/api/food-database/v2/nutrients" + authToken;

    public ArrayList<Food> callFood(String food) {
        ArrayList<Food> returnArray = new ArrayList<>();
        if (food.equals("")) {
            return null;
        }
        try {
            HttpRequest request = HttpRequest.newBuilder(new
                            URI(requestURL + "&ingr=" + food + "&nutrition-type=cooking"))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return null;
            }
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(response.body(), JsonObject.class);
            JsonArray parsedArray = (JsonArray) json.get("parsed");
            if (parsedArray.size() == 0) {
                return null;
            }
            JsonArray hintsArray = (JsonArray) json.get("hints");
            for (int i = 0; i < hintsArray.size(); i++) {
                JsonObject parse = (JsonObject) hintsArray.get(i);
                JsonObject foodJson = (JsonObject) parse.get("food");
                Food foodOutput = gson.fromJson(foodJson, Food.class);
                JsonArray measuresJson = (JsonArray) parse.get("measures");
                ArrayList<Measure> mesuresArray = new ArrayList<>();
                for (int j = 0; j < measuresJson.size(); j++) {
                    mesuresArray.add(gson.fromJson(measuresJson.get(j), Measure.class));
                }
                foodOutput.setMeasures(mesuresArray);
                returnArray.add(foodOutput);
            }
            return returnArray;


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addNutritional(Food food) {
        Gson gson = new Gson();
        IngredientsHolder holder = new IngredientsHolder(new Ingredients((int) Math.round((food.getQuantity())), food.getMeasure().getUri(), food.getFoodId()));
        String jsonPost = gson.toJson(holder);
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.edamam.com/api/food-database/v2/nutrients?app_id=214e6e25&app_key=205ab21162d1ccd3e972900c81197374")).header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPost))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return;
            }
            JsonObject jsonFull = gson.fromJson(response.body(), JsonObject.class);
            JsonObject jsonNutrients = (JsonObject) jsonFull.get("totalNutrients");
            ArrayList<Nutritents2ElectricBoogaloo> nutrients = new ArrayList<>();
            for (Map.Entry<String, JsonElement> entry : jsonNutrients.entrySet()) {
                JsonObject obj = (JsonObject) entry.getValue();
                nutrients.add(new Nutritents2ElectricBoogaloo(obj.get("label").getAsString(), obj.get("quantity").getAsDouble(), obj.get("unit").getAsString()));
            }
            food.addNutrients(nutrients);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getNutrientsNames() {
        Food food = new Food("food_at830s9amds32fb8w6ufmaopzk8n", "Pizza", new ArrayList<>());
        food.setMeasure(new Measure("http://www.edamam.com/ontologies/edamam.owl#Measure_unit", " Whole", 452), 12);
        Gson gson = new Gson();
        IngredientsHolder holder = new IngredientsHolder(new Ingredients((int) Math.round((food.getQuantity())), food.getMeasure().getUri(), food.getFoodId()));
        String jsonPost = gson.toJson(holder);
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.edamam.com/api/food-database/v2/nutrients?app_id=214e6e25&app_key=205ab21162d1ccd3e972900c81197374")).header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPost))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return new ArrayList<>();
            }
            JsonObject jsonFull = gson.fromJson(response.body(), JsonObject.class);
            JsonObject jsonNutrients = (JsonObject) jsonFull.get("totalNutrients");
            ArrayList<String> nutrients = new ArrayList<>();
            for (Map.Entry<String, JsonElement> entry : jsonNutrients.entrySet()) {
                JsonObject obj = (JsonObject) entry.getValue();
                nutrients.add(obj.get("label").getAsString());
            }
            return nutrients;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


}