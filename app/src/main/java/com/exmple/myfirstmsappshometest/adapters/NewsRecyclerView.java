package com.exmple.myfirstmsappshometest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.exmple.myfirstmsappshometest.R;
import com.exmple.myfirstmsappshometest.models.NewsModel;

import java.util.List;

public class NewsRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsModel> mNews;
    private final OnNewsListener onNewsListener;

    public NewsRecyclerView(OnNewsListener onNewsListener) {
        this.onNewsListener = onNewsListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        return new NewsViewHolder(view, onNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((NewsViewHolder) holder).title.setText(mNews.get(position).getTitle());
        ((NewsViewHolder) holder).release_date.setText(mNews.get(position).getPublishedAt());
        ((NewsViewHolder) holder).content.setText(mNews.get(position).getContent());

        Glide
                .with(holder.itemView.getContext())
                .load(mNews.get(position).getUrlToImage())
                .into(((NewsViewHolder) holder).imageView);

        ((NewsViewHolder)holder).favoritesBtn.setVisibility(View.INVISIBLE);

    }


    @Override
    public int getItemCount() {
        return mNews != null ? mNews.size() : 0;
    }

    public void setmNews(List<NewsModel> mNews) {
        this.mNews = mNews;
        notifyDataSetChanged();
    }

    public NewsModel getSelectedNews(int position) {
        return mNews != null && mNews.size() > 0 ? mNews.get(position) : null;
    }
}
