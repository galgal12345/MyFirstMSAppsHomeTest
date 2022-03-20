package com.exmple.myfirstmsappshometest.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.exmple.myfirstmsappshometest.requests.MyService;
import com.exmple.myfirstmsappshometest.AppExecutors;
import com.exmple.myfirstmsappshometest.models.NewsModel;
import com.exmple.myfirstmsappshometest.responses.NewsSearchResponse;
import com.exmple.myfirstmsappshometest.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class NewsApiClient {

    //LiveData
    private final MutableLiveData<List<NewsModel>> mNews;
    private static NewsApiClient instance;
    private RetrieveNewsRunnable retrieveNewsRunnable;


    public static NewsApiClient getInstance() {
        if (instance == null){ instance = new NewsApiClient(); }
        return instance;
    }

    public LiveData<List<NewsModel>> getNews() {
        return mNews;
    }

    private NewsApiClient(){
        mNews = new MutableLiveData<>();
    }

    public void searchNewsApi(String query){

        if (retrieveNewsRunnable != null){ retrieveNewsRunnable = null; }
        retrieveNewsRunnable = new RetrieveNewsRunnable(query);

        final Future myHandler = AppExecutors.getInstance().getmNetworkIO().submit(retrieveNewsRunnable);

        //canceling the retrofit call
        AppExecutors.getInstance().getmNetworkIO().schedule(() -> { myHandler.cancel(true); },3000, TimeUnit.MILLISECONDS);
    }

    //Retrieving data from restapi by runnable  class
    private class RetrieveNewsRunnable implements Runnable{

        private String query;
        boolean cancelRequest;

        public RetrieveNewsRunnable(String query) {
            this.query = query;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response obj
            try {

                Response response = getNews(query).execute();
                if (cancelRequest){return;}

                if (response.code() == 200)
                {
                    assert response.body() != null;
                    List<NewsModel> list = new ArrayList<>(((NewsSearchResponse)response.body()).getNewsModels());
                    if (query.equals("articles"))
                    {
                        //sending data to live data
                        Log.v("Tag", "msg: " + response.body().toString());
                        mNews.postValue(list);
                    }
                    else {
                        List<NewsModel> currentNews = mNews.getValue();
                        currentNews.addAll(list);
                        mNews.postValue(currentNews);
                    }
                }
                else
                {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error " + error);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mNews.postValue(null);
            }

        }

        //search Method/query
        private Call<NewsSearchResponse> getNews(String query){
            return MyService.getNewsApi().searchNews(Credentials.API_KEY, query);
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling search request");
            cancelRequest = true;

        }
    }
}
