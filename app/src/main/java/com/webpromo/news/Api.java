package com.webpromo.news;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
            String BASE_URL = "https://www.kalkinemedia.co.uk/old/km/";

    @GET("post.php")
    Call<List<Mydata>> getHeroes();
}
