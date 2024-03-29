package com.makaroni.topmovies.main;

import com.arellomobile.mvp.MvpView;
import com.makaroni.topmovies.data.MovieResponse;

import java.util.List;

public interface MainView extends MvpView {
    void showMovies(List<MovieResponse.Movie> movies);
    void showError(int e);
    void showToast(String s,int duration);
}
