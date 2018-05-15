package com.example.menaadel.zadtask.data;

import com.example.menaadel.zadtask.data.model.Repository;
import com.example.menaadel.zadtask.utils.Constants;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MenaAdel on 1/16/2018.
 */

public class GetReposOperation {

    Retrofit retrofit;
    RetrofitApi retrofitApi;
    private static GetReposOperation Instance;

    private GetReposOperation(){
        retrofitApi=getRetrofitClient().create(RetrofitApi.class);
    }

    private Retrofit getRetrofitClient(){
            if (retrofit == null){
                retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
    }

    public static GetReposOperation getInstance() {
        if (Instance == null) {
            Instance = new GetReposOperation();
        }
        return Instance;
    }

    public void startOperation(int page, int perPage, final RequestsObserver observer){
        Call<List<Repository>> call = retrofitApi.gitRepositories(page, perPage);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if(observer!=null)
                    observer.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                if(observer!=null)
                    observer.onError(t.getMessage());
            }
        });
    }

}
