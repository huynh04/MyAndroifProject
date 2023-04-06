package com.example.newsdaily.Model;

import android.content.Context;
import android.widget.Toast;

import com.example.newsdaily.Listener.OnFetchDataListener;
import com.example.newsdaily.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context ctx;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context ctx) {
        this.ctx = ctx;
    }

    public void getArticles(OnFetchDataListener listener,String category,String query){
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<APIResponse> call = callNewsApi.callHeadline("us",category,query, ctx.getString(R.string.api_key));

        try{
            call.enqueue(new Callback<APIResponse>() {
                @Override
                public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(ctx, "Error!!", Toast.LENGTH_SHORT).show();
                    }
                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<APIResponse> call, Throwable t) {
                    listener.onError("Request Failed!!");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface CallNewsApi {
        @GET("top-headlines")
        Call<APIResponse> callHeadline(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String apiKey
        );
    }
}
