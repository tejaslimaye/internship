package com.jobdetails.shant.getjobdetails.Network;

import com.jobdetails.shant.getjobdetails.Common.Constant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {


    private static Retrofit retrofit = null;

    public static Retrofit getJobDetailJSON() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.mainURL + Constant.urlPortNo)
//                .baseUrl("https://api.myjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }


}
