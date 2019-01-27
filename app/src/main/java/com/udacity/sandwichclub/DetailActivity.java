package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.ArrayUtils;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mIngredientsImageView;
    private TextView mAlsoKnownTitleTV;
    private TextView mAlsoKnownAsTV;
    private TextView mDetailIngredientsTV;
    private TextView mIngredientsTV;
    private TextView mDescriptionTitleTV;
    private TextView mDescriptionTV;
    private TextView mOriginTV;
    private TextView mPlaceOfOriginTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mIngredientsImageView = findViewById(R.id.image_iv);
        mAlsoKnownTitleTV = findViewById(R.id.also_known_title_tv);
        mAlsoKnownAsTV = findViewById(R.id.also_known_tv);
        mDetailIngredientsTV = findViewById(R.id.detail_ingredients_label);
        mIngredientsTV = findViewById(R.id.ingredients_tv);
        mDescriptionTitleTV = findViewById(R.id.description_title_tv);
        mDescriptionTV = findViewById(R.id.description_tv);
        mOriginTV = findViewById(R.id.origin_tv);
        mPlaceOfOriginTV = findViewById(R.id.place_of_origin_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mIngredientsImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mDescriptionTitleTV.setText(R.string.detail_description_label);
        String description = sandwich.getDescription();
        if (description == null || description.isEmpty()) {
            mDescriptionTV.setText("N/A");
        } else {
            mDescriptionTV.setText(description);
        }
        mPlaceOfOriginTV.setText(R.string.detail_place_of_origin_label);
        String origin = sandwich.getPlaceOfOrigin();
        if (origin == null || origin.isEmpty()) {
            mOriginTV.setText("Unknown");
        } else {
            mOriginTV.setText(origin);
        }
        mAlsoKnownTitleTV.setText(R.string.detail_also_known_as_label);
        List<String> alsoItemsList = ArrayUtils.nullToEmptyStrings(sandwich.getAlsoKnownAs());
        for (String alsoItem: alsoItemsList) {
            mAlsoKnownAsTV.append(alsoItem);
            if (alsoItemsList.indexOf(alsoItem) != alsoItemsList.size() - 1) {
                mAlsoKnownAsTV.append("\n");
            }
        }
        if (alsoItemsList.isEmpty()) {
            mAlsoKnownAsTV.setText("N/A");
        }
        mDetailIngredientsTV.setText(R.string.detail_ingredients_label);
        List<String> ingredientsList = ArrayUtils.nullToEmptyStrings(sandwich.getIngredients());
        for (String ingredient: ingredientsList) {
            mIngredientsTV.append(ingredient);
            if (ingredientsList.indexOf(ingredient) != ingredientsList.size() - 1) {
                mIngredientsTV.append("\n");
            }
        }
        if (ingredientsList.isEmpty()) {
            mIngredientsTV.setText("N/A");
        }
    }
}
