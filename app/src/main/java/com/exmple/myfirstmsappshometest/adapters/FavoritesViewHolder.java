package com.exmple.myfirstmsappshometest.adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exmple.myfirstmsappshometest.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoritesViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

    TextView title, release_date, content;
    CircleImageView imageView;
    CheckBox favoritesBtn;
    OnNewsListener onNewsListener;

    public FavoritesViewHolder(@NonNull View itemView, OnNewsListener onNewsListener) {
        super(itemView);

        this.onNewsListener = onNewsListener;
        title = itemView.findViewById(R.id.favorites_list_title);
        release_date = itemView.findViewById(R.id.favorites_list_date);
        imageView = itemView.findViewById(R.id.favorites_list_img);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onNewsListener.onNewsClick(getBindingAdapterPosition());
        onNewsListener.onFavoritesClick(getBindingAdapterPosition());
    }
}
