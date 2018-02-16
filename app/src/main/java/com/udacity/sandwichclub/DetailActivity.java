package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich mSandwich = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            mSandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        /****************
         * Place of origin *
         ****************/
        TextView originTv = findViewById(R.id.origin_tv);
        String origin = mSandwich.getPlaceOfOrigin();
        if (origin.isEmpty()) {
            originTv.setText("-");
        } else {
            originTv.setText(origin);
        }

        /****************
         * Also known as *
         ****************/
        TextView alsoKnownAsTv = findViewById(R.id.also_known_as_tv);
        List<String> alsoKnownAsList = mSandwich.getAlsoKnownAs();

        String joinedAlsoKnownAs = TextUtils.join(", ", alsoKnownAsList);

        if (joinedAlsoKnownAs.isEmpty()) {
            alsoKnownAsTv.setText("-");
        } else {
            alsoKnownAsTv.setText(joinedAlsoKnownAs);
        }

        /****************
         * Ingredients *
         ****************/
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = mSandwich.getIngredients();

        String joinedIngredients = TextUtils.join(", ", ingredientsList);

        if (joinedIngredients.isEmpty()) {
            ingredientsTv.setText("-");
        } else {
            ingredientsTv.setText(joinedIngredients);
        }

        /****************
         * Description *
         ****************/
        TextView descriptionTv = findViewById(R.id.description);
        String description = mSandwich.getDescription();
        if (description.isEmpty()) {
            descriptionTv.setText("-");
        } else {
            descriptionTv.setText(description);
        }
    }
}
