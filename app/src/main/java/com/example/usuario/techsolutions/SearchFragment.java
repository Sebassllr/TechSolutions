package com.example.usuario.techsolutions;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.example.usuario.techsolutions.MainActivity.calledAlready;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private View view;

    private EditText etToSearch;

    private Button toSearch;

    public DatabaseReference databaseReference;
    public FirebaseAuth firebaseAuth;
    public FirebaseDatabase firebaseDatabase;
    public ProgressDialog progressDialog;
    public FirebaseUser firebaseUser;
    public StorageReference mStorage;

    private RecyclerView mRecyclerDates;
    private RVArticles rvArticles;
    private LinearLayoutManager mLinearLayoutManager;

    public ArrayList<Article> mDataTest = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initComponents();
        return view;
    }

    private void initComponents(){
        etToSearch = view.findViewById(R.id.etToSearch);
        toSearch = view.findViewById(R.id.btnToSearch);
        toSearch.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();
        if (!calledAlready) {
            firebaseDatabase.setPersistenceEnabled(true);
            calledAlready = true;
        }
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onClick(View v) {
        Integer id = v.getId();
        switch (id){
            case R.id.btnToSearch:{
                searchArticle(etToSearch.getText().toString().toLowerCase());
                break;
            }
        }
    }


    private void searchArticle(final String toSearch){

        databaseReference.child(Article.ARTICLE_NODE_NAME)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mDataTest = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot != null){
                                Article object = snapshot.getValue(Article.class);

                                if(object != null && !object.getDeleted()){
                                    if(object.getTag() != null && object.getTag().toLowerCase().contains(toSearch) ||
                                            object.getTitle().toLowerCase().contains(toSearch)){
                                        mDataTest.add(object);
                                    }
                                }
                            }
                        }
                        if(mDataTest.size() > 0){
                            setRvArticles();
                        }else{
                            Toast.makeText(getContext(), "No hay resultados disponibles", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

    }

    private void setRvArticles(){
        RVArticles.isEditing = null;
        mRecyclerDates = view.findViewById(R.id.rv_articles) ;
        mRecyclerDates.setHasFixedSize(true);
        mLinearLayoutManager =  new GridLayoutManager(view.getContext(), 1);
        mRecyclerDates.setLayoutManager(mLinearLayoutManager);
        mRecyclerDates.setItemAnimator(new DefaultItemAnimator());
        rvArticles = new RVArticles(mDataTest, view.getContext());
        mRecyclerDates.setAdapter(rvArticles);
    }
}
