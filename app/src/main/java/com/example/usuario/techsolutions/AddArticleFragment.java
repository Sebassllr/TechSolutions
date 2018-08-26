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

    private Boolean isEdit = Boolean.FALSE;

    private View view;

    private Transacciones transacciones = new Transacciones();

    private Article article;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_article, container, false);
        initializer();
        return view;
    }

    private void initializer(){
        view.findViewById(R.id.btnAdd).setOnClickListener(this);
        view.findViewById(R.id.etTitle).setEnabled(Boolean.FALSE);
        view.findViewById(R.id.etContent).setEnabled(Boolean.FALSE);
        view.findViewById(R.id.etTag).setEnabled(Boolean.FALSE);
        (view.findViewById(R.id.btnAdd)).setVisibility(View.GONE);
        Bundle bundle = getArguments();
        if(RVArticles.isEditing != null){
            view.findViewById(R.id.etTitle).setEnabled(Boolean.TRUE);
            view.findViewById(R.id.etContent).setEnabled(Boolean.TRUE);
            view.findViewById(R.id.etTag).setEnabled(Boolean.TRUE);
            RVArticles.isEditing = null;
            (view.findViewById(R.id.btnAdd)).setVisibility(View.VISIBLE);
            Button button = view.findViewById(R.id.btnAdd);
            button.setText("Editar artículo");
            isEdit = Boolean.TRUE;
        }
        if(bundle != null){
            (view.findViewById(R.id.btnAdd)).setVisibility(View.VISIBLE);
            Boolean mDocNum = bundle.getBoolean("AddArticle");
            if(mDocNum){
                view.findViewById(R.id.etTitle).setEnabled(Boolean.TRUE);
                view.findViewById(R.id.etContent).setEnabled(Boolean.TRUE);
            }
        }
        if(RVArticles.clickedArticle != null){
            ((TextView) view.findViewById(R.id.etTitle)).setText(RVArticles.clickedArticle.getTitle());
            ((TextView) view.findViewById(R.id.etContent)).setText(RVArticles.clickedArticle.getContent());
            ((TextView) view.findViewById(R.id.etTag)).setText(RVArticles.clickedArticle.getTag());
            article = RVArticles.clickedArticle;
            RVArticles.clickedArticle = null;
        }
    }

    @Override
    public void onClick(View v) {
        EditText editTextTitle = view.findViewById(R.id.etTitle);
        EditText editTextContent = view.findViewById(R.id.etContent);
        EditText editTextTag = view.findViewById(R.id.etTag);
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        String tag = editTextTag.getText().toString();
        if(!isEdit){
            String idEntregable = transacciones.databaseReference.push().getKey();
            transacciones.registrarArtículo(title,  content, tag, transacciones.firebaseAuth.getCurrentUser().getUid(), idEntregable);
        }else{
            article.setContent(content);
            article.setTitle(title);
            article.setTag(tag);
            transacciones.insertar(Article.ARTICLE_NODE_NAME, article.getId(), article);
        }
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
