package com.example.rickandmortyapi;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONObject;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private Context context;
    private ArrayList<Character> characterList;
    private OnListListener mOnListListener;

    public ExampleAdapter(Context context, ArrayList<Character> characterList, OnListListener onListListener){
        this.context = context;
        this.characterList = characterList;
        this.mOnListListener = onListListener;
    }


    public class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewName;
        public TextView textViewOrigin;
        public TextView textViewEpisode;
        public ImageView imageViewImage;

        OnListListener onListListener;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ExampleViewHolder(@NonNull View itemView, OnListListener onListListener) {
            super(itemView);
            imageViewImage = itemView.findViewById(R.id.characterImage);
            textViewEpisode = itemView.findViewById(R.id.characterEpisode);
            textViewOrigin = itemView.findViewById(R.id.characterOrigin);
            textViewName = itemView.findViewById(R.id.characterName);
            itemView.findViewById(R.id.listItem).setClipToOutline(true);
            this.onListListener = onListListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListListener.onListClick(getAdapterPosition());
        }
    }

    public interface OnListListener{
        void onListClick(int position);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ExampleViewHolder(v, mOnListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Character current = characterList.get(position);

        String imgUrl = current.getImgUrl();
        String name = current.getName();
        String episode = current.getEpisode();
        String origin = current.getOrigin();

        holder.textViewName.setText(name);
        holder.textViewOrigin.setText(origin);
        holder.textViewEpisode.setText(episode);
        Picasso.with(context).load(imgUrl).into(holder.imageViewImage);
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }
}
