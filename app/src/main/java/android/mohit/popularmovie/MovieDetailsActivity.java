package android.mohit.popularmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.mohit.popularmovie.model.Movie;
import android.mohit.popularmovie.utils.Constant;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView movieBackgroundImg, moviePosterImg;
    private Movie movie;
    private TextView overviewTV,movieNameTV,releaseDateTV,voteAverageTV,languageTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        if(intent.hasExtra("movieObject")){
            movie = (Movie) intent.getSerializableExtra("movieObject");
            Log.d("click", "Get into new activity"+movie.getTitle());

        }

        init();
    }

    private void init(){
        movieBackgroundImg = findViewById(R.id.movie_backdrop_img);
        moviePosterImg = findViewById(R.id.movie_poster_img);
        overviewTV = findViewById(R.id.tv_overView);
        movieNameTV = findViewById(R.id.movie_name_tv);
        releaseDateTV = findViewById(R.id.release_date_tv);
        voteAverageTV = findViewById(R.id.vote_tv);
        languageTV = findViewById(R.id.language_tv);
        setImage(movie.getBackdrop_path(),movieBackgroundImg);
        setImage(movie.getPoster_path(),moviePosterImg);
        overviewTV.setText(movie.getOverview());
        movieNameTV.setText(movie.getTitle());
        releaseDateTV.setText(movie.getRelease_date());
        voteAverageTV.setText(movie.getVote_average() +getResources().getString(R.string.rating_from));
        languageTV.setText(movie.getOriginal_language());

    }

    private void setImage(String url, ImageView imageView){
        Glide.with(this)
                .load(Constant.IMAGE_BASE_URL+ Constant.IMAGE_SIZE_780+url)
                .placeholder(android.R.color.darker_gray)
                .into(imageView);

    }
}
