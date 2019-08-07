package com.makaroni.topmovies.main;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.makaroni.topmovies.R;
import com.makaroni.topmovies.data.MovieDbApi;
import com.makaroni.topmovies.data.MovieResponse;
import com.makaroni.topmovies.notification.NotificationHelper;
import com.makaroni.topmovies.notification.NotificationReceiver;

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
public class MainPresenter extends MvpPresenter<MainView>  {
    private Retrofit retrofit;
    private MovieDbApi api;
    private Calendar calendar = Calendar.getInstance();
    private Context context;
    private AlarmManager alarmManager;

    public MainPresenter(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(MovieDbApi.class);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
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


    public void scheduleViewing(String movie, Calendar calendar) {
            Intent intent = new Intent(context, NotificationReceiver.class);
            intent.putExtra(NotificationReceiver.movieTitle,movie);
            int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, iUniqueId, intent, 0);
            // If user set past data, a toast will show
            if (calendar.before(Calendar.getInstance())) {
                getViewState().showError("1");
                return;
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            getViewState().showToast("Notification was created", Toast.LENGTH_SHORT);

    }
}
