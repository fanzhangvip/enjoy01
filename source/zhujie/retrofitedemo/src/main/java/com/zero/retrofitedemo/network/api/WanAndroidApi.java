package com.zero.retrofitedemo.network.api;




import com.zero.retrofitedemo.network.bean.BaseResponse;
import com.zero.retrofitedemo.network.bean.ProjectBean;
import com.zero.retrofitedemo.network.bean.ProjectItem;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WanAndroidApi {


    // 注解里传入 网络请求 的部分URL地址
    @GET("project/tree/json")
    // getProject()是接受网络请求数据的方法
    //  RxJava 方式：Observable<..>接口形式
    Observable<ProjectBean> getProject();

    @Headers("Cache-Control:max-age=640000")
    @GET("project/tree/json")
    Call<ProjectBean> getProject1();

    @HTTP(method = "get",path = "project/tree/json",hasBody = false)
    Call<ProjectBean> getProject2(@Header("Authorization")String authorization);

    @GET("project/list/{pageIndex}/json")
    Observable<ProjectItem> getProjectItem(@Path("pageIndex") int pageIndex, @Query("cid") int cid);

    @POST("user/register")
    @FormUrlEncoded//表明这个请求体 是Form表单
    Maybe<BaseResponse> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    @POST("user/login")
    @FormUrlEncoded
    Maybe<BaseResponse> login(@Field("username") String username, @Field("password") String password);
}
