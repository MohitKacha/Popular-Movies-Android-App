package android.mohit.popularmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.mohit.popularmovie.adapter.MovieListAdapter;
import android.mohit.popularmovie.model.Movie;
import android.mohit.popularmovie.model.MovieData;
import android.mohit.popularmovie.utils.Constant;
import android.mohit.popularmovie.utils.JsonParsing;
import android.mohit.popularmovie.utils.MovieItemClickListner;
import android.mohit.popularmovie.utils.NetworkClients;
import android.mohit.popularmovie.utils.NetworkResponse;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkResponse, MovieItemClickListner {


    private RecyclerView movieListRecyclerView;
    private TextView noDataTV;
    private List<Movie> movieList;
    private  NetworkClients networkClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieListRecyclerView = findViewById(R.id.rv_movie_list);
        noDataTV = findViewById(R.id.no_data_tv);
        networkClients = new NetworkClients(this, this);
        getPopularMovieList();


    }



    @Override
    public void responseFromNetwork(String data) {
        Log.d("response", data);
        JsonParsing jsonParsing = new JsonParsing();
        MovieData movieData = jsonParsing.parseMovieResponse(data);
        movieList = movieData.getResults();
        Log.d("res", movieList.size() + "");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setUpMovieListAdapter();
            }
        });
    }

    private void setUpMovieListAdapter() {
        MovieListAdapter movieListAdapter = new MovieListAdapter(this, movieList, this);
        movieListRecyclerView.setAdapter(movieListAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        movieListRecyclerView.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void onItemClicked(int itemClicked) {
        Log.d("click", "onTem called");
        Movie movie = movieList.get(itemClicked);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movieObject", movie);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movieoption,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.movie_most_popular){
            getPopularMovieList();
        }else if(itemId == R.id.movie_top_rated){
            getTopRatedMovieList();
        }


        return super.onOptionsItemSelected(item);
    }

    private void getPopularMovieList(){
        if (networkClients.isInternetAvailable()) {
            networkClients.getMovieData(Constant.BASE_URL + Constant.POPULAR_MOVIE + Constant.API_KEY);
        } else {
            noDataTV.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Phone is not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void getTopRatedMovieList(){
        if (networkClients.isInternetAvailable()) {
            networkClients.getMovieData(Constant.BASE_URL + Constant.TOP_RATED_MOVIE + Constant.API_KEY);
        } else {
            noDataTV.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Phone is not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }
}
