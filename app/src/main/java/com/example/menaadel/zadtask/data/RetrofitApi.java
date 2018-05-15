package com.example.menaadel.zadtask.data;

import com.example.menaadel.zadtask.data.model.Repository;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MenaAdel on 1/16/2018.
 */

public interface RetrofitApi {

    //String base_url="https://api.github.com/users/square/repos?page=";

    @GET("users/square/repos")
    Call<List<Repository>> gitRepositories(@Query("page") int page, @Query("per_page") int per_page);
}
