package com.exmple.myfirstmsappshometest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exmple.myfirstmsappshometest.adapters.NewsRecyclerView;
import com.exmple.myfirstmsappshometest.adapters.OnNewsListener;
import com.exmple.myfirstmsappshometest.models.NewsModel;
import com.exmple.myfirstmsappshometest.viewmodels.NewsListViewModel;

import java.util.List;


public class HomeFragment extends Fragment implements OnNewsListener {

    private RecyclerView recyclerView;
    private NewsRecyclerView newsRecyclerViewAdapter;
    private NewsListViewModel newsListViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.home_recyclerview);

        newsListViewModel = new ViewModelProvider(this).get(NewsListViewModel.class);

        configureRecyclerView();
        observeAnyChange();
        searchNewsApi("articles");

        return rootView;
    }

    private void observeAnyChange() {

        newsListViewModel.getNews().observe(getViewLifecycleOwner(), new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(List<NewsModel> newsModels) {
                //Observing for any data changed
                if (newsModels != null) {
                    newsRecyclerViewAdapter.setmNews(newsModels);
                }
            }
        });
    }

    private void searchNewsApi(String query) {
        newsListViewModel.searchNewsApi(query);
    }

    private void configureRecyclerView() {
        newsRecyclerViewAdapter = new NewsRecyclerView(this);
        recyclerView.setAdapter(newsRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onNewsClick(int position) {
        NewsModel NewsModel = newsRecyclerViewAdapter.getSelectedNews(position);
        Uri uri = Uri.parse(NewsModel.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onFavoritesClick(int position) {

    }


}