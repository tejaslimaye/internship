package com.jobdetails.shant.getjobdetails.Network;

import com.jobdetails.shant.getjobdetails.Common.Constant;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIEnrollUser {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitObj() {

        java.net.Proxy proxy = new Proxy(Proxy.Type.HTTP,  new InetSocketAddress("127.0.0.1", 18080));
        OkHttpClient client = new OkHttpClient.Builder().proxy(proxy)/*.readTimeout(120, TimeUnit.SECONDS).connectTimeout(120,TimeUnit.SECONDS)*/.build();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://18.206.222.73" + Constant.enrollUserPort)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
