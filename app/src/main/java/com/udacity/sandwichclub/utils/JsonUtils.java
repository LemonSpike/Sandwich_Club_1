package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject("name");
            sandwich.setMainName(name.getString("mainName"));
            sandwich.setDescription(sandwichJson.getString("description"));
            sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
            sandwich.setImage(sandwichJson.getString("image"));
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            alsoKnownAsArray = ArrayUtils.nullToEmptyJSON(alsoKnownAsArray);
            List<String> alsoKnownAsList = new ArrayList<String>();
            for(int i = 0; i < alsoKnownAsArray.length(); i++){
                alsoKnownAsList.add(alsoKnownAsArray.get(i).toString());
            }
            sandwich.setAlsoKnownAs(alsoKnownAsList);
            JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
            ingredientsArray = ArrayUtils.nullToEmptyJSON(ingredientsArray);
            List<String> ingredientsList = new ArrayList<String>();
            for(int i = 0; i < ingredientsArray.length(); i++){
                ingredientsList.add(ingredientsArray.get(i).toString());
            }
            sandwich.setIngredients(ingredientsList);
        } catch (JSONException jsonError) {
            jsonError.printStackTrace();
        }
        return sandwich;
    }
}
