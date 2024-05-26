package com.example.tienda_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class CerrarSesionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        redirectToLoginActivity();

        return view;
    }

    private void redirectToLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        getActivity().finish();
    }
}
