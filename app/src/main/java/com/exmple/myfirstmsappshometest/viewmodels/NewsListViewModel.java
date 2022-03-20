package com.exmple.myfirstmsappshometest.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.exmple.myfirstmsappshometest.models.NewsModel;
import com.exmple.myfirstmsappshometest.repositories.NewsRepository;

import java.util.List;

public class NewsListViewModel extends ViewModel {

    private NewsRepository newsRepository;


    public NewsListViewModel() {
        newsRepository = NewsRepository.getInstance();
    }

    public LiveData<List<NewsModel>> getNews(){
        return newsRepository.getNews();
    }

    //Calling method in view-model
    public void searchNewsApi(String query){
        newsRepository.searchNewsApi(query);
    }
}
