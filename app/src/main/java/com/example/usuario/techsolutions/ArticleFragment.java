package com.example.usuario.techsolutions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArticleFragment extends Fragment {

    private DatabaseReference databaseReference;

    private View view;
    private RecyclerView mRecyclerDates;
    private RVArticles rvArticles;
    private LinearLayoutManager mLinearLayoutManager;

    public static ArrayList<Article> mDataTest = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_article, container, Boolean.FALSE);
        initializer();
        return view;
    }

    private void initializer(){
        getRvData();
    }

    private void setRvArticles(){
        mRecyclerDates = view.findViewById(R.id.rv_articles) ;
        mRecyclerDates.setHasFixedSize(true);

        mLinearLayoutManager =  new GridLayoutManager(view.getContext(), 2);
        mRecyclerDates.setLayoutManager(mLinearLayoutManager);
        mRecyclerDates.setItemAnimator(new DefaultItemAnimator());
        rvArticles = new RVArticles(mDataTest, view.getContext());
        mRecyclerDates.setAdapter(rvArticles);
    }

    private void getRvData(){
        databaseReference.child(Article.ARTICLE_NODE_NAME)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Article object = snapshot.getValue(Article.class);
                            mDataTest.add(object);
                        }
                        setRvArticles();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
