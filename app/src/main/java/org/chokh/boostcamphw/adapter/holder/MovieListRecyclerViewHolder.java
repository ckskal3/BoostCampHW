package org.chokh.boostcamphw.adapter.holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.chokh.boostcamphw.R;
import org.chokh.boostcamphw.data.MovieItem;
import org.chokh.boostcamphw.listener.RecyclerViewItemClickListener;

public class MovieListRecyclerViewHolder extends RecyclerView.ViewHolder {
    private ImageView movieImage;
    private TextView movieTitle, movieOpenYear, movieDirector, movieActor;
    private RatingBar movieRate;
    private LinearLayout linearLayout;

    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public MovieListRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        init(itemView);

    }

    private void init(View view) {
        movieImage = view.findViewById(R.id.movie_image);
        movieTitle = view.findViewById(R.id.movie_title);
        movieOpenYear = view.findViewById(R.id.movie_open_year);
        movieDirector = view.findViewById(R.id.movie_director);
        movieActor = view.findViewById(R.id.movie_actor);
        movieRate = view.findViewById(R.id.movie_rate);
        linearLayout = view.findViewById(R.id.movie_list_back);
    }

    public void onBind(final int position){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewItemClickListener.onItemClick(position);
            }
        });
    }

    public void setOnItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    public void setItem(@Nullable MovieItem.MovieDetail item) {

        Glide.with(this.itemView.getContext()).load(item.image).into(movieImage);
        movieTitle.setText(item.title);
        movieOpenYear.setText(item.pubDate);
        movieDirector.setText(item.director);
        movieActor.setText(item.actor);
        movieRate.setRating(item.userRating / 2);
    }

}