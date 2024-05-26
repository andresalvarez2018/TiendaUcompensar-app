package com.example.tienda_app;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tienda_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private boolean isLoginFragmentVisible = true;
    private static final int PRODUCTOS_ID = R.id.productos;
    private static final int CARRITO_ID = R.id.carrito;
    private static final int REGISTRO_ID = R.id.registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new ProductosFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == PRODUCTOS_ID) {
                selectedFragment = new ProductosFragment();
                isLoginFragmentVisible = false;
            } else if (itemId == CARRITO_ID) {
                selectedFragment = new CarritoFragment();
                isLoginFragmentVisible = false;
            } else if (itemId == REGISTRO_ID) {
                selectedFragment = new CerrarSesionFragment();
                isLoginFragmentVisible = false;
            }
            replaceFragment(selectedFragment);
            toggleBottomNavigationView();
            return true;
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void toggleBottomNavigationView() {
        if (isLoginFragmentVisible) {
            binding.bottomNavigationView.setVisibility(View.GONE);
        } else {
            binding.bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    public void switchToMainFragment() {
        isLoginFragmentVisible = false;
        replaceFragment(new ProductosFragment());
        toggleBottomNavigationView();
    }
}
