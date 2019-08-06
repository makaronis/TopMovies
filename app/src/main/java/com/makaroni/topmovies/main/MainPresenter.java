package com.makaroni.topmovies.main;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.makaroni.topmovies.data.MovieDbApi;
import com.makaroni.topmovies.data.MovieNotification;
import com.makaroni.topmovies.data.MovieResponse;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> implements MovieNotification {
    private Retrofit retrofit;
    private MovieDbApi api;
    private Calendar calendar = Calendar.getInstance();

    public MainPresenter() {
        retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(MovieDbApi.class);
    }
    public void loadMovies() {
        Call<MovieResponse> call = api.loadMovies(getRequestMap());
        try {
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<MovieResponse.Movie> movies = response.body().getResults();
                    int resultsCount = response.body().getResultsCount();
                    getViewState().showMovies(movies);
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public Map<String,String> getRequestMap() {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("api_key","51acab29f02b8fbb4ec0cd81b15ce9aa");
        map.put("language","en-US");
        map.put("sort_by","popularity.desc");
        map.put("include_adult","true");
        map.put("include_video","false");
        map.put("page","1");
        map.put("primary_release_year","2019");
        return map;
    }

    @Override
    public void scheduleViewing(String movie) {
        getViewState().setDate();
    }
}
