package com.zero.retrofitjava;


import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyGET{
    String value() default "";
}

@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
@interface MyPath {
    String value();

    boolean encoded() default false;
}

public class OkHttpDemo {

    static interface GitHubApi{
        @MyGET("/users/{user}/repos")
        okhttp3.Call listRepos(@MyPath("user") String user);

        @MyGET("/users/{user}/repos")
        okhttp3.Call listRepos1(@MyPath("zero") String user);

    }

    public void listRepos(){
        //1.创建一个OkHttpClient
        OkHttpClient client = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .get()
                .url("https://api.github.com//users/zerovip/repos")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Zero","e: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Zero",response.body().toString());
            }
        });

    }

    public void listReposEx(){
        GitHubApi gitHubApi = (GitHubApi) Proxy.newProxyInstance(GitHubApi.class.getClassLoader(), new Class<?>[]{GitHubApi.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //method -> listRepos
                        //常见的用法 method.invoke()  反射

                        //retrofit
//                        @MyGET("/users/{user}/repos")//注解里面包含了url的相对路径
//                        okhttp3.Call listRepos(@MyPath("user") String user);

                        boolean isMyGETPresent = method.isAnnotationPresent(MyGET.class);
                        if(!isMyGETPresent)return null;
                        MyGET myGET = method.getAnnotation(MyGET.class);
                        // https://api.github.com/users/zerovip/repos
                        HttpUrl.Builder urlBuilder;
                        Annotation[][] parameterAnnotationsArray = method.getParameterAnnotations();
                        Annotation[] parameterAnnotation = parameterAnnotationsArray[0];
                        MyPath myPath = (MyPath)parameterAnnotation[0];
                        String path = myPath.value();
                        Log.i("Zero","path: " +path);
                        HttpUrl baseUrl = HttpUrl.get("https://api.github.com/");
                        String url = myGET.value();
                        url = url.replace("{"+path+"}",(String)args[0]);
                        urlBuilder = baseUrl.newBuilder(url);

                        Log.i("Zero","url: " + urlBuilder.build().toString());
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        OkHttpClient client = new OkHttpClient.Builder()
                                .addInterceptor(httpLoggingInterceptor)
                                .sslSocketFactory(createSSLSocketFactory(),mMyTrustManager)
                                .build();
                        //创建一个Request
                        Request request = new Request.Builder()
                                .get()
                                .url(urlBuilder.build().toString())
                                .build();
                        return client.newCall(request);
                    }
                });
        gitHubApi.listRepos("zerovip").enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Zero","Ex e: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Zero",response.body().toString());
            }
        });
    }

    private MyTrustManager mMyTrustManager;

    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            mMyTrustManager = new MyTrustManager();
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{mMyTrustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }
}



//实现X509TrustManager接口
 class MyTrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}

//实现HostnameVerifier接口
 class TrustAllHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}

