package com.example.rickandmortyapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import static com.example.rickandmortyapi.AppConstants.CHARACTER_LISTS;
import static com.example.rickandmortyapi.AppConstants.CURRENT_CHARACTER;

public class DetailsActivity extends AppCompatActivity implements ExampleAdapter.OnListListener {
    private static final String ALIVE = "Alive";
    private static final String DEAD = "Dead";
    public TextView textViewName;
    public TextView textViewLocation;
    public TextView textViewFirstSeenEpisode;
    public TextView textViewStatus;
    public TextView textViewAlsoFrom;
    public ImageView imageViewImage;
    private RecyclerView recyclerView;
    private ExampleAdapter exampleAdapter;
    private ArrayList<Character> characterList;
    private ArrayList<Character> newCharacterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.back);
        setContentView(R.layout.details);

        Character currentCharacter = (Character) getIntent().getSerializableExtra(CURRENT_CHARACTER);
        this.characterList = (ArrayList<Character>) getIntent().getSerializableExtra(CHARACTER_LISTS);

        String currentCharacterOrigin = currentCharacter.getOrigin();
        int currentCharacterId = currentCharacter.getId();
        newCharacterList = new ArrayList<>();

        for (Character c: characterList){
            if (c.getOrigin().equals(currentCharacterOrigin) && (c.getId() != currentCharacterId)){
                newCharacterList.add(c);
            }
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        textViewName = findViewById(R.id.characterName);
        textViewName.setText(currentCharacter.getName());

        textViewLocation = findViewById(R.id.lastKnownLocation);
        textViewLocation.setText(currentCharacter.getLocation());

        textViewFirstSeenEpisode = findViewById(R.id.firstSeen);
        textViewFirstSeenEpisode.setText(currentCharacter.getEpisode());

        textViewStatus = findViewById(R.id.status);
        textViewStatus.setText(currentCharacter.getStatus());
        if (currentCharacter.getStatus().equals(ALIVE)){
            textViewStatus.setTextColor(getResources().getColor(R.color.green));
        } else if (currentCharacter.getStatus().equals(DEAD)){
            textViewStatus.setTextColor(getResources().getColor(R.color.red));
        }

        textViewAlsoFrom = findViewById(R.id.alsoFrom);
        textViewAlsoFrom.setText("Also from \"" + currentCharacter.getOrigin() + "\"");

        imageViewImage = findViewById(R.id.bigImageView);
        Picasso.with(this).load(currentCharacter.getImgUrl()).resize(400, 400).into(imageViewImage);

        exampleAdapter = new ExampleAdapter(DetailsActivity.this, newCharacterList, DetailsActivity.this);
        recyclerView.setAdapter(exampleAdapter);
    }

    @Override
    public void onListClick(int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(CURRENT_CHARACTER, newCharacterList.get(position));
        intent.putExtra(CHARACTER_LISTS, characterList);
        startActivity(intent);
    }
}