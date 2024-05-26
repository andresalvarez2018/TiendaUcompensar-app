package com.example.tienda_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarritoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarritoFragment extends Fragment {
    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private List<Product> productList;
    private TextView textViewTotalPrice;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);
        textViewTotalPrice = view.findViewById(R.id.textViewTotalPrice);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));
        productList = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext(), productList);
        recyclerViewCart.setAdapter(cartAdapter);

        loadCartData();

        return view;
    }

    private void loadCartData() {
        String url = "https://dummyjson.com/carts/1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray productsArray = response.getJSONArray("products");
                            for (int i = 0; i < productsArray.length(); i++) {
                                JSONObject productObject = productsArray.getJSONObject(i);
                                Product product = new Product();
                                product.setId(productObject.getInt("id"));
                                product.setTitle(productObject.getString("title"));
                                product.setPrice(productObject.getDouble("price"));
                                product.setQuantity(productObject.getInt("quantity"));
                                product.setThumbnailUrl(productObject.getString("thumbnail"));
                                productList.add(product);
                            }
                            cartAdapter.notifyDataSetChanged();


                            double totalPrice = response.getDouble("total");
                            textViewTotalPrice.setText("Total: $" + totalPrice);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error al cargar el carrito de compras", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }

}