package com.webpromo.news;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyApi {
    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registeruser(
            @Field("nameid")String name,
            @Field("mobileid")String mobile,
            @Field("usernameid")String username,
            @Field("passwordid")String password
    );

    @GET("test.json")
    Call<List<Mydata>> getdata();
}