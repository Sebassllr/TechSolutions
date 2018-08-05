package com.example.usuario.techsolutions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddArticleFragment extends Fragment implements View.OnClickListener{

    /**
     * La idea es que este fragment reciba un bundle dependiendo si se va a agregar o a editar un artículo
     * El XML tiene edit text deshabilitados si es para mostrarlo, si es para editar, entonces se habilitan
    */

    private View view;

    private Transacciones transacciones = new Transacciones();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_article, container, false);
        initializer();
        return view;
    }

    private void initializer(){
        view.findViewById(R.id.btnAdd).setOnClickListener(this);
        Bundle bundle = getArguments();
        if(bundle != null){
            Boolean mDocNum = bundle.getBoolean("AddArticle");
            if(mDocNum){
                view.findViewById(R.id.etTitle).setEnabled(Boolean.TRUE);
                view.findViewById(R.id.etContent).setEnabled(Boolean.TRUE);
            }
        }
        if(RVArticles.clickedArticle != null){
            ((TextView) view.findViewById(R.id.etTitle)).setText(RVArticles.clickedArticle.getTitle());
            ((TextView) view.findViewById(R.id.etContent)).setText(RVArticles.clickedArticle.getContent());
            (view.findViewById(R.id.btnAdd)).setVisibility(View.GONE);
            RVArticles.clickedArticle = null;
        }
    }

    @Override
    public void onClick(View v) {
        EditText editTextTitle = view.findViewById(R.id.etTitle);
        EditText editTextContent = view.findViewById(R.id.etContent);
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        String idEntregable = transacciones.databaseReference.push().getKey();
        transacciones.registrarArtículo(title,  content, transacciones.firebaseAuth.getCurrentUser().getUid(), idEntregable);
    }
}
