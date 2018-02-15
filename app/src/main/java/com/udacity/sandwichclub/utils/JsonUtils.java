package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    private static final String SANDWICH_DETAILS_NAME = "name";
    private static final String SANDWICH_DETAILS_MAIN_NAME = "mainName";
    private static final String SANDWICH_DETAILS_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String SANDWICH_DETAILS_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String SANDWICH_DETAILS_DESCRIPTION = "description";
    private static final String SANDWICH_DETAILS_IMAGE = "image";
    private static final String SANDWICH_DETAILS_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException{

        JSONObject sandwichDetails = new JSONObject(json);

        JSONObject name = sandwichDetails.getJSONObject(SANDWICH_DETAILS_NAME);
        String mainName = name.getString(SANDWICH_DETAILS_MAIN_NAME);

        List<String> alsoKnownAsList = new ArrayList<>();

        JSONArray alsoKnownAs = name.getJSONArray(SANDWICH_DETAILS_ALSO_KNOWN_AS);

        for (int i = 0; i < alsoKnownAs.length(); i++) {
            String sandwich = alsoKnownAs.getString(i);
            alsoKnownAsList.add(sandwich);
        }

        String placeOfOrigin = sandwichDetails.getString(SANDWICH_DETAILS_PLACE_OF_ORIGIN);

        String description = sandwichDetails.getString(SANDWICH_DETAILS_DESCRIPTION);

        String imageUrl = sandwichDetails.getString(SANDWICH_DETAILS_IMAGE);

        List<String> ingredientsList = new ArrayList<>();

        JSONArray ingredients = sandwichDetails.getJSONArray(SANDWICH_DETAILS_INGREDIENTS);

        for (int i = 0; i < ingredients.length(); i++) {
            String ingredient = ingredients.getString(i);
            ingredientsList.add(ingredient);
        }

        return new Sandwich(
                mainName,
                alsoKnownAsList,
                placeOfOrigin,
                description,
                imageUrl,
                ingredientsList);
    }
}
