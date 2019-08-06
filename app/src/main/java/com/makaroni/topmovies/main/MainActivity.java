package com.makaroni.topmovies.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.makaroni.topmovies.R;
import com.makaroni.topmovies.data.MovieNotification;
import com.makaroni.topmovies.data.MovieRecyclerAdapter;
import com.makaroni.topmovies.data.MovieResponse;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends MvpAppCompatActivity implements MainView, MovieNotification {
    public RecyclerView recyclerView;
    public MovieRecyclerAdapter recyclerAdapter;
    private Calendar calendar = Calendar.getInstance();
    public int date ;
    public int [] time = new int[2];


    @InjectPresenter
    MainPresenter mainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        mainPresenter.loadMovies();
        //setDate();
        //setTime();
    }

    @Override
    public void showMovies(List<MovieResponse.Movie> movies) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerAdapter = new MovieRecyclerAdapter(movies,this);
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public void showError(Exception e) {

    }



    // отображаем диалоговое окно для выбора даты
    public void setDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setTime();
            }
        };
        new DatePickerDialog(MainActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time[0]=hourOfDay;
                time[1]=minute;
            }};
        new TimePickerDialog(MainActivity.this,timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true)
                .show();
    }

    @Override
    public void scheduleViewing(String movie) {
        setDate();
    }
    // установка обработчика выбора даты

}
