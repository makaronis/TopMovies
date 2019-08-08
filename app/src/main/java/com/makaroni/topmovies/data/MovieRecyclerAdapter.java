package com.makaroni.topmovies.data;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.makaroni.topmovies.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private List<MovieResponse.Movie> movies;
    private Context context;
    private MovieNotification notification;
    private Calendar calendar;

    public MovieRecyclerAdapter(List<MovieResponse.Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
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
        private View ratingDrawable;
        private ImageView poster;
        private Button button;



        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            releaseDate = itemView.findViewById(R.id.text_date);
            overview = itemView.findViewById(R.id.text_overview);
            ratingDrawable = itemView.findViewById(R.id.view_rating);
            ratingText = itemView.findViewById(R.id.text_rating);
            poster = itemView.findViewById(R.id.image_poster);
            button = itemView.findViewById(R.id.button);

        }
        private void bind (final MovieResponse.Movie movie){
            title.setText(movie.getTitle());
            setReleaseDate(movie.getReleaseDate());
            overview.setText(movie.getOverview());
            int rating = movie.getRating();
            setRatingDrawable(rating);
            String posterPath = movie.getPosterPath();
            ratingText.setText(String.valueOf(movie.getRating()));
            Picasso.get().setLoggingEnabled(true);
            if (!posterPath.isEmpty())
                Picasso.get().load("http://image.tmdb.org/t/p/w185" + posterPath).placeholder(R.drawable.poster_example).into(poster);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notification.scheduleViewing(movie.getTitle());
                }
            });

        }
        private void setRatingDrawable(int rating){
            if (rating>=70)
                ratingDrawable.setBackgroundResource(R.drawable.rating_circle_70);
            else if (rating<70&&rating>=40)
                ratingDrawable.setBackgroundResource(R.drawable.rating_circle_40);
            else
                ratingDrawable.setBackgroundResource(R.drawable.rating_circle_10);

        }
        private void setReleaseDate(String date){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try{
                Date convertedDate = dateFormat.parse(date);
                dateFormat.applyPattern("MMMM d, yyyy");
                if (convertedDate!=null) {
                    releaseDate.setText(dateFormat.format(convertedDate));
                }else releaseDate.setText(date);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
