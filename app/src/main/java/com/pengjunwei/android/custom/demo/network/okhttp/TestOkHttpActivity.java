package com.pengjunwei.android.custom.demo.network.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class TestOkHttpActivity extends AppCompatActivity{
    private OkHttpClient mOkHttpClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new StethoInterceptor());
        mOkHttpClient = builder.build();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.client(mOkHttpClient).baseUrl("www.baidu.com");
        Retrofit retrofit = retrofitBuilder.build();
    }
}
