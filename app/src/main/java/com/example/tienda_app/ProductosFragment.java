package com.example.tienda_app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductosFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializar la cola de solicitudes de Volley
        requestQueue = Volley.newRequestQueue(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productList = new ArrayList<>();
        adapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(adapter);

        loadProducts();

        return view;
    }

    private void loadProducts() {
        String url = "https://dev-backed-u-compensar.pantheonsite.io/api/apis-app-list-productos";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic cmVzdDpyZXN0");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray productsArray = new JSONArray(response);
                            for (int i = 0; i < productsArray.length(); i++) {
                                JSONObject productObject = productsArray.getJSONObject(i);
                                int id = productObject.getInt("id");
                                String title = productObject.getString("title");
                                String description = productObject.getString("description");
                                double price = productObject.getDouble("price");
                                String thumbnailUrl = productObject.getString("image");

                                Product product = new Product();
                                product.setId(id);
                                product.setTitle(title);
                                product.setDescription(description);
                                product.setPrice(price);
                                product.setThumbnailUrl(thumbnailUrl);

                                productList.add(product);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showError("Error al analizar la respuesta JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON_RESPONSE", error.getMessage());
                        showError("Error de red al cargar productos");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }
}
