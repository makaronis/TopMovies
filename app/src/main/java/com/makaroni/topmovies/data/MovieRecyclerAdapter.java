package com.makaroni.topmovies.data;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.makaroni.topmovies.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private List<MovieResponse.Movie> movies;
    private Context context;
    private MovieNotification notification;
    private Drawable ratingDrawable70;
    private Drawable ratingDrawable40;
    private Drawable ratingDrawable10;
    Calendar calendar;

    public MovieRecyclerAdapter(List<MovieResponse.Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
        ratingDrawable70 = ContextCompat.getDrawable(context,R.drawable.progressbar_circle_70);
        ratingDrawable40 = ContextCompat.getDrawable(context,R.drawable.progressbar_circle_40);
        ratingDrawable10 = ContextCompat.getDrawable(context,R.drawable.progressbar_circle_10);
        calendar = Calendar.getInstance();
        notification = (MovieNotification) this.context;

    }

    @NonNull
    @Override
    public MovieRecyclerAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.refactor_list_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerAdapter.MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView releaseDate;
        private TextView overview;
        private TextView ratingText;
        private ProgressBar ratingBar;
        private ImageView poster;
        private Button button;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            releaseDate = itemView.findViewById(R.id.text_date);
            overview = itemView.findViewById(R.id.text_overview);
            ratingBar = itemView.findViewById(R.id.progressbar_rating);
            ratingText = itemView.findViewById(R.id.text_rating);
            poster = itemView.findViewById(R.id.image_poster);
            button = itemView.findViewById(R.id.button);

        }
        private void bind (final MovieResponse.Movie movie){
            title.setText(movie.getTitle());
            releaseDate.setText(movie.getReleaseDate());
            overview.setText(movie.getOverview());
            int rating = movie.getRating();
            setRatingColor(rating);
            ratingBar.setProgress(rating);
            String posterPath = movie.getPosterPath();
            ratingText.setText(String.valueOf(movie.getRating()));
            if (!posterPath.isEmpty())
                Picasso.get().load("http://image.tmdb.org/t/p/w185" + posterPath).into(poster);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notification.scheduleViewing(movie.getTitle());
                }
            });

        }
        private void setRatingColor(int rating){
            if (rating>=70)
                ratingBar.setProgressDrawable(ratingDrawable70);
            else if (rating<70&&rating>=40)
                ratingBar.setProgressDrawable(ratingDrawable40);
            else
                ratingBar.setProgressDrawable(ratingDrawable10);

        }
    }
}
