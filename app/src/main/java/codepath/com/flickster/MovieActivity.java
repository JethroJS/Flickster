package codepath.com.flickster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import codepath.com.flickster.adapters.Movieadapter;
import codepath.com.flickster.models.Movie;
import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private static final String Movie_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    List<Movie> movies;

    //Add RecyclerView support library to the Gradle build file  - DONE
    //Define a model class to use as the data source - DONE
    //Add a RecyclerView to your activity to display the items - DONE
    //Create a custom row layout XML file to visualize the item - DONE
    //Create a RecyclerView.Adapter and ViewHolder to render the item
    //Bind the adapter to the data source to populate the RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        final Movieadapter adapter = new Movieadapter(this, movies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvMovies.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Movie_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray movieJsonArray = response.getJSONArray("results" );
                    movies.addAll(Movie.fromJsonArray(movieJsonArray));
                    adapter.notifyDataSetChanged();
                    Log.d( "smile",movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
