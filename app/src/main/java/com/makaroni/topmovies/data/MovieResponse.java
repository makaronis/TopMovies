package com.makaroni.topmovies.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("total_results")
    @Expose
    public int resultsCount;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;
    @SerializedName("results")
    @Expose
    public List<Movie> results = null;

    public List<Movie> getResults() {
        return results;
    }

    public int getResultsCount() {
        return resultsCount;
    }

    public class Movie {
        @SerializedName("vote_average")
        @Expose
        public double rating;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("poster_path")
        @Expose
        public String posterPath;
        @SerializedName("overview")
        @Expose
        public String overview;
        @SerializedName("release_date")
        @Expose
        public String releaseDate;

        public int getRating() {
            double convertedRating = rating * 10 ;
            return (int) convertedRating;
        }

        public String getTitle() {
            return title;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public String getOverview() {
            return overview;
        }

        public String getReleaseDate() {
            return releaseDate;
        }
    }

}
