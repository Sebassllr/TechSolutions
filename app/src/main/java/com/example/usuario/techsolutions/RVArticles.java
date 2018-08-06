package com.example.usuario.techsolutions;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RVArticles extends RecyclerView.Adapter<RVArticles.ViewHolder>{

    private ArrayList<Article> mDataset;

    private Context mContext;

    public static Boolean isEditing;

    public static Article clickedArticle;

    private Transacciones transacciones = new Transacciones();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;

        LinearLayout linearLayout;
        // each data item is just a string in this case
        public ViewHolder(View v) {
            super(v);
            textViewTitle = v.findViewById(R.id.tvTitle);
            linearLayout = v.findViewById(R.id.llRv);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textViewTitle.setText(mDataset.get(position).getTitle());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedArticle = mDataset.get(position);
                MainFragentActivity activity = (MainFragentActivity) mContext;
                AddArticleFragment articleFragment = new AddArticleFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frFragment, articleFragment).addToBackStack(null).commit();
            }
        });
        if(isEditing != null && isEditing){
            holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    transacciones.delete(mDataset.get(position).getId());
                    removeAt(position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void removeAt(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataset.size());
    }
}
