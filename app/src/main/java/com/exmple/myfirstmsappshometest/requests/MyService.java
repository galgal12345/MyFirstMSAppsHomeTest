package com.exmple.myfirstmsappshometest.requests;

import com.exmple.myfirstmsappshometest.utils.Credentials;
import com.exmple.myfirstmsappshometest.utils.NewsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyService {

    private static final Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final NewsApi newsApi = retrofit.create(NewsApi.class);

    public static NewsApi getNewsApi() {
        return newsApi;
    }
}
