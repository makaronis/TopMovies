package com.makaroni.topmovies.main;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
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
    private Button button ;

    @InjectPresenter
    MainPresenter mainPresenter;
    @ProvidePresenter
    MainPresenter provideMainPresenter() {
        return new MainPresenter(getApplicationContext());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        mainPresenter.loadMovies();
        button = findViewById(R.id.button_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.loadMovies();
                button.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void showMovies(List<MovieResponse.Movie> movies) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerAdapter = new MovieRecyclerAdapter(movies,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showError(int code) {
        switch (code){
            case 1 :
                String notificationError = "You can't set that date \n Please, set another date.";
                Toast.makeText(this,notificationError,Toast.LENGTH_LONG).show();
                break;
            case 2 :
                String requestError = "An error occurred with loading movies \n Please, check your internet connection";
                Toast.makeText(this,requestError,Toast.LENGTH_LONG).show();
                button.setVisibility(View.VISIBLE);
                break;

        }

    }

    @Override
    public void showToast(String s,int duration) {
        Toast.makeText(MainActivity.this,s,duration).show();
    }


    @Override
    public void scheduleViewing(final String movie) {
        //After time is set, we call scheduleViewing and make notification
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                mainPresenter.scheduleViewing(movie,calendar);
            }};
        final TimePickerDialog timePicker = new TimePickerDialog(MainActivity.this,timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        //Time picker is showing after date is set
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                timePicker.show();
            }
        };
        new DatePickerDialog(MainActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }
}
