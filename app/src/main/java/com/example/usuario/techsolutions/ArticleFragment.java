package com.example.usuario.techsolutions;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.List;

import static com.example.usuario.techsolutions.MainActivity.calledAlready;

public class ArticleFragment extends Fragment {

    public DatabaseReference databaseReference;
    public FirebaseAuth firebaseAuth;
    public FirebaseDatabase firebaseDatabase;
    public FirebaseUser firebaseUser;
    public StorageReference mStorage;

    private View view;
    private RecyclerView mRecyclerDates;
    private RVArticles rvArticles;
    private LinearLayoutManager mLinearLayoutManager;

    public static ArrayList<Article> mDataTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_article, container, Boolean.FALSE);
        initializer();
        return view;
    }

    private void initializer(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();
        if (!calledAlready) {
            firebaseDatabase.setPersistenceEnabled(true);
            calledAlready = true;
        }
        databaseReference = firebaseDatabase.getReference();
        getRvData();
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

    private void getRvData(){
        mDataTest = new ArrayList<>();
        databaseReference.child(Article.ARTICLE_NODE_NAME)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot != null){
                                Article object = snapshot.getValue(Article.class);
                                if(object != null && !object.getDeleted()){
                                    mDataTest.add(object);
                                }
                            }
                        }
                        setRvArticles();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }


}
