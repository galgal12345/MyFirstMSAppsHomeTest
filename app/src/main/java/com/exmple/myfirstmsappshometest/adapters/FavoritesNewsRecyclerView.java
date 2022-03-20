package com.exmple.myfirstmsappshometest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.exmple.myfirstmsappshometest.R;
import com.exmple.myfirstmsappshometest.models.NewsModel;
import com.exmple.myfirstmsappshometest.utils.Credentials;

import java.util.List;

public class FavoritesNewsRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<NewsModel> mNews;
    private final OnNewsListener onNewsListener;

    public FavoritesNewsRecyclerView(OnNewsListener onNewsListener) {
        this.onNewsListener = onNewsListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_list_items,parent, false);
        return new FavoritesViewHolder(view, onNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


//            ((FavoritesViewHolder)holder).title.setText(mNews.get(position).getTitle());
//            ((FavoritesViewHolder)holder).release_date.setText(mNews.get(position).getPublishedAt());
//            ((FavoritesViewHolder)holder).content.setText(mNews.get(position).getContent());
//
//            Glide
//                    .with(holder.itemView.getContext())
//                    .load(mNews.get(position).getUrlToImage())
//                    .into(((FavoritesViewHolder)holder).imageView);


    }


    @Override
    public int getItemCount() {
        return mNews != null ? mNews.size() : 0;
    }

    public void setmNews(List<NewsModel> mNews) {
        this.mNews = mNews;
        notifyDataSetChanged();
    }

    public NewsModel getSelectedNews(int position){
        return mNews != null && mNews.size() > 0 ? mNews.get(position) : null;
    }
}
