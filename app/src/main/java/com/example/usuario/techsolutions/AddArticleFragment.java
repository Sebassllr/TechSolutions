package com.example.usuario.techsolutions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddArticleFragment extends Fragment {

    /**
     * La idea es que este fragment reciba un bundle dependiendo si se va a agregar o a editar un artículo
     * El XML tiene edit text deshabilitados si es para mostrarlo, si es para editar, entonces se habilitan
    */

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_article, container, false);
    }

}
