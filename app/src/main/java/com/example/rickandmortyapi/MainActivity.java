package com.example.rickandmortyapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.rickandmortyapi.AppConstants.CHARACTER_LISTS;
import static com.example.rickandmortyapi.AppConstants.CURRENT_CHARACTER;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnListListener {
    private static final int numberOfCharacterPages = 34;
    private static final int numberOfEpisodePages = 3;
    private RecyclerView recyclerView;
    private ExampleAdapter exampleAdapter;
    private ArrayList<Character> characterList;
    private ArrayList<String> episodeList;
    private RequestQueue requestQueue;
    int incrementedPageNumber = 1;
    int episodesPageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        characterList = new ArrayList<>();
        episodeList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        getEpisodes(String.valueOf(episodesPageNumber));
    }

    private void getCharacters(String pageNumber) {
        String url = "https://rickandmortyapi.com/api/character?page=" + pageNumber;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    parseCharactersData(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    private void parseCharactersData(JSONArray jsonArray){
        try{
            if (jsonArray.length() > 0){
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject character = jsonArray.getJSONObject(i);

                    int id = character.getInt("id");
                    String name = character.getString("name");
                    String origin = character.getJSONObject("origin").getString("name");
                    String location = character.getJSONObject("location").getString("name");

                    String[] s = character.getJSONArray("episode").getString(0).split("/");
                    String episode = episodeList.get(parseInt(s[s.length-1]) - 1);

                    String status = character.getString("status");
                    String imageUrl = character.getString("image");

                    characterList.add(new Character(id, name, origin, location, episode, status, imageUrl));
                }
                incrementedPageNumber++;
                if (incrementedPageNumber <= numberOfCharacterPages) {
                    getCharacters(String.valueOf(incrementedPageNumber));
                }else{
                    exampleAdapter = new ExampleAdapter(MainActivity.this, characterList, MainActivity.this);
                    recyclerView.setAdapter(exampleAdapter);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getEpisodes(String page){
        String url = "https://rickandmortyapi.com/api/episode?page=" + page;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    parseEpisodesData(jsonArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    public void parseEpisodesData(JSONArray jsonArray){
        try{
            if (jsonArray.length() > 0){
                for(int i =0; i < jsonArray.length(); i++){
                    JSONObject episode = jsonArray.getJSONObject(i);
                    episodeList.add(episode.getString("name"));
                }
                episodesPageNumber++;
                if (episodesPageNumber <= numberOfEpisodePages) {
                    getEpisodes(String.valueOf(episodesPageNumber));
                } else{
                    getCharacters(String.valueOf(incrementedPageNumber));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListClick(int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(CURRENT_CHARACTER, characterList.get(position));
        intent.putExtra(CHARACTER_LISTS, characterList);
        startActivity(intent);
    }
}