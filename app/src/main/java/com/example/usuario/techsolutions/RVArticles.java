package com.example.usuario.techsolutions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RVArticles extends RecyclerView.Adapter<RVArticles.ViewHolder>{

    private ArrayList<Article> mDataset;

    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewAutor;

        // each data item is just a string in this case
        public ViewHolder(View v) {
            super(v);
            textViewTitle = v.findViewById(R.id.etTitle);
            textViewAutor = v.findViewById(R.id.etContent);
        }
    }

    public RVArticles(ArrayList<Article> mDataset, Context mContext) {
        this.mDataset = mDataset;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rv_articles, parent,false);
        return new RVArticles.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewTitle.setText(mDataset.get(position).getTitle());
        holder.textViewAutor.setText(mDataset.get(position).getAutor());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
