package com.example.tienda_app;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView imageViewThumbnail;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewPrice;
    private TextView textViewTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);


        imageViewThumbnail = findViewById(R.id.imageViewThumbnail);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewTags = findViewById(R.id.textViewTags);

        int productId = getIntent().getIntExtra("productId", -1);
        if (productId != -1) {
            loadProductDetails(productId);
        } else {
            Toast.makeText(this, "ID del producto no válido", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadProductDetails(int productId) {
        String url = "https://dev-backed-u-compensar.pantheonsite.io/api/apis-app-detalle-producto/" + productId;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic cmVzdDpyZXN0");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String productName = response.getString("title");
                            String productDescription = response.getString("description");
                            double productPrice = response.getDouble("price");
                            String thumbnailUrl = response.getString("image");

                            textViewTitle.setText(productName);
                            textViewDescription.setText(productDescription);
                            textViewPrice.setText(String.valueOf(productPrice));

                            Glide.with(ProductDetailsActivity.this)
                                    .load(thumbnailUrl)
                                    .placeholder(R.drawable.placeholder_image) // Placeholder de imagen
                                    .into(imageViewThumbnail);

                            JSONArray tagsArray = response.getJSONArray("tags");
                            StringBuilder tagsBuilder = new StringBuilder();
                            for (int i = 0; i < tagsArray.length(); i++) {
                                tagsBuilder.append(tagsArray.getString(i));
                                if (i < tagsArray.length() - 1) {
                                    tagsBuilder.append(", ");
                                }
                            }
                            String tags = tagsBuilder.toString();
                            textViewTags.setText(tags);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            showError("Error al analizar la respuesta JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ProductDetails", "Error: " + error.getMessage());
                        showError("Error de red al cargar detalles del producto");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Manejar el clic en la flecha hacia atrás
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
