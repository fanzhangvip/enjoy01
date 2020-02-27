package com.zero.retrofitedemo.network.api;


import android.icu.text.MessagePattern;

import com.zero.retrofitedemo.network.bean.BaseResponse;
import com.zero.retrofitedemo.network.bean.ProjectBean;
import com.zero.retrofitedemo.network.bean.ProjectItem;

import java.util.Map;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface WanAndroidApi {


    // 注解里传入 网络请求 的部分URL地址
    @GET("project/tree/json")
    // getProject()是接受网络请求数据的方法
    //  RxJava 方式：Observable<..>接口形式
    Observable<ProjectBean> getProject();

    //    @Headers("Cache-Control:max-age=640000")
    @GET("project/tree/json")
    Call<ProjectBean> getProject1();

    @HTTP(method = "get", path = "project/tree/json", hasBody = false)
    Call<ProjectBean> getProject2();

    ///////上传单张图片//////

    /**
     * Multipart：表示请求实体是一个支持文件上传的Form表单，需要配合使用@Part,适用于 有文件 上传的场景
     * Part:用于表单字段,Part和PartMap与Multipart注解结合使用,适合文件上传的情况
     * PartMap:用于表单字段,默认接受的类型是Map<String,REquestBody>，可用于实现多文件上传
     * Part 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型；
     *
     * @param file 服务器指定的上传图片的key值
     * @return
     */

    @Multipart
    @POST("project/upload")
    Call<ProjectBean> upload1(@Part("file" + "\";filename=\"" + "test.png") RequestBody file);

    @Multipart
    @POST("project/xxx")
    Call<ProjectBean> upload2(@Part MultipartBody.Part file);

    ///////上传多张图片//////
    @Multipart
    @POST("project/upload")
    Call<ProjectBean> upload3(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("project/xxx")
    Call<ProjectBean> upload4(@PartMap Map<String, MultipartBody.Part> map);

    //////图文混传/////
    /**
     * @param params
     * @param files
     * @return
     */
    @Multipart
    @POST("upload/upload")
    Call<ProjectBean> upload5(@FieldMap() Map<String, String> params,
                              @PartMap() Map<String, RequestBody> files,
                              @Part("file") RequestBody file,
                              @PartMap Map<String,RequestBody> maps);

    /**
     * Part 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型；
     *
     * @param userName
     * @param passWord
     * @param file
     * @return
     */
    @Multipart
    @POST("project/xxx")
    Call<ProjectBean> upload6(@Part("username") RequestBody userName,
                              @Part("password") RequestBody passWord,
                              @Part MultipartBody.Part file);


    /**
     * 12.Streaming注解:表示响应体的数据用流的方式返回，适用于返回的数据比较大，该注解在在下载大文件的特别有用
     */
    @Streaming
    @GET
    Call<ProjectBean> downloadFile(@Url String fileUrl);

    @Headers("Cache-Control: max-age=640000")
    @GET("project/list")
    Call<ProjectBean> getMsg1();

    @Headers({ "Accept: application/vnd.github.v3.full+json","User-Agent: Retrofit-Sample-App"})
    @GET("project/{username}")
    Call<ProjectBean> getMsg2(@Path("username") String username);

    @GET("project")
    Call<ProjectBean> getProject3(@Header("Authorization") String authorization);

    @POST("project/new")
    Call<ProjectBean> createProject(@Body ProjectBean user);

    //固定或可变数组
    @FormUrlEncoded
    @POST("/list")
    Call<ResponseBody> example(@Field("name") String... names);

    @FormUrlEncoded
    @POST("/examples")
    Call<ResponseBody> example(@FieldMap Map<String, String> fields);

    @GET("/example1")
    Call<ProjectBean> example1(@HeaderMap Map<String, String> headers);


    @GET("example2/{id}")
    Call<ResponseBody> example2(@Path("id") int id);

    @GET("example3/{id}")
    Call<ResponseBody> example3(@Path("id") int id, @QueryMap Map<String, String> options);

    @GET
    Call<ResponseBody> example4(@Url String url);

    @GET("example5/{id}")
    Call<ResponseBody> example5(@Path("id") int id);

    @HTTP(method = "get", path = "project/tree/json", hasBody = false)
    Call<ProjectBean> getProject2(@Header("Authorization") String authorization);

    @GET("project/list/{pageIndex}/json")
    Observable<ProjectItem> getProjectItem(@Path("pageIndex") int pageIndex, @Query("cid") int cid);

    @POST("user/register")
    @FormUrlEncoded
//表明这个请求体 是Form表单
    Maybe<BaseResponse> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    @POST("user/login")
    @FormUrlEncoded
    Maybe<BaseResponse> login(@Field("username") String username, @Field("password") String password);
}
