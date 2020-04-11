package android.mohit.popularmovie.utils;

import android.mohit.popularmovie.model.Movie;
import android.mohit.popularmovie.model.MovieData;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

public class JsonParsing {

    public MovieData parseMovieResponse(String response){

        Gson gson = new Gson();
        MovieData movieResponse = gson.fromJson(response,MovieData.class);

        List<Movie> movieList = movieResponse.getResults();

        for(Movie movie : movieList){
            Log.d("res", movie.getTitle());
        }

        return  movieResponse;

    }

}
