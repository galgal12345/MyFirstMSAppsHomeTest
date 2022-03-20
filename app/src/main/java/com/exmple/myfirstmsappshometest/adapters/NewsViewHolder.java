package com.exmple.myfirstmsappshometest.adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exmple.myfirstmsappshometest.R;

public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, release_date, content;
    ImageView imageView;
    CheckBox favoritesBtn;
    OnNewsListener onNewsListener;

    public NewsViewHolder(@NonNull View itemView, OnNewsListener onNewsListener) {
        super(itemView);

        this.onNewsListener = onNewsListener;
        title = itemView.findViewById(R.id.news_title);
        release_date = itemView.findViewById(R.id.news_publishedAt);
        content = itemView.findViewById(R.id.content);
        imageView = itemView.findViewById(R.id.news_img);
        favoritesBtn = itemView.findViewById(R.id.favorites_btn);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onNewsListener.onNewsClick(getBindingAdapterPosition());
        onNewsListener.onFavoritesClick(getBindingAdapterPosition());
    }
}
