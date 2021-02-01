package com.zero.retrofitjava;


import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;



public class RetrofitDemo {

    //2. 定义API
    static interface GitHubApi{
        @GET("/users/{user}/repos")
        Call<List<Repo>> listRepos(@Path("user") String user);

        @GET("/users/{user}/repos")
        Observable<List<Repo>> listReposRx(@Path("user") String user);
    }

    public void listRepos(){
        //1. 创建一个retrofit对象
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")//http resetful
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        //3. 获取api实例
        GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

        //进行请求
        gitHubApi.listRepos("zerovip").enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                Log.i("Zero","result: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e("Zero","t: " + t.getMessage());
            }
        });
    }

    public void listReposRx(){
        //1. 创建一个retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")//http resetful
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        //3. 获取api实例
        GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

        //进行请求

        gitHubApi.listReposRx("zerovip")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Repo>>() {
                    @Override
                    public void accept(List<Repo> repos) throws Exception {
                        Log.i("Zero","rxjava repos: " + repos);
                    }
                });
    }
}
