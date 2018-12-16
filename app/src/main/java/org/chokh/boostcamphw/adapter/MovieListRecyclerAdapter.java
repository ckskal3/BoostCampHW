package org.chokh.boostcamphw.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.chokh.boostcamphw.R;
import org.chokh.boostcamphw.adapter.contract.MovieAdapterContract;
import org.chokh.boostcamphw.adapter.holder.MovieListRecyclerViewHolder;
import org.chokh.boostcamphw.data.MovieItem;
import org.chokh.boostcamphw.listener.LoadMoreItemRecyclerViewListener;
import org.chokh.boostcamphw.listener.RecyclerViewItemClickListener;

import java.util.ArrayList;

public class MovieListRecyclerAdapter extends RecyclerView.Adapter<MovieListRecyclerViewHolder> implements MovieAdapterContract.Model, MovieAdapterContract.View {
    private ArrayList<MovieItem.MovieDetail> movieItems = new ArrayList<>();
    private LoadMoreItemRecyclerViewListener loadMoreItemRecyclerViewListener;

    private int totalCount;


    @NonNull
    @Override
    public MovieListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieListRecyclerViewHolder movieListRecyclerViewHolder, int position) {
        if (position == getItemCount() - 3 && getItemCount() < totalCount) { //현재 위치가 마지막위치보다 3개 전일때 loadmore 함수 호출
            if (loadMoreItemRecyclerViewListener != null) {
                loadMoreItemRecyclerViewListener.onLoadMore(position);
            }
        }

        movieListRecyclerViewHolder.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showMovieDetailInChromeTabsIntent(movieListRecyclerViewHolder.itemView.getContext(),position);
            }
        });
        movieListRecyclerViewHolder.setItem(movieItems.get(position));
        movieListRecyclerViewHolder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return this.movieItems.size();
    }

    @Override
    public void addItem(MovieItem.MovieDetail item) {
        this.movieItems.add(item);
    }

    @Override
    public void addAllItem(ArrayList<MovieItem.MovieDetail> items) {
        this.movieItems = items;
    }

    @Override
    public void clearItem() {
        this.movieItems.clear();
    }

    @Override
    public void showMovieDetailInChromeTabsIntent(Context context, int position) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                .setCloseButtonIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_name))
                .setStartAnimations(context, R.anim.right_in, R.anim.left_out)
                .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .setToolbarColor(ContextCompat.getColor(context,R.color.colorPrimary));

        CustomTabsIntent intent = builder.build();
        intent.launchUrl(context,Uri.parse(getItem(position).link));
    }

    @Override
    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public MovieItem.MovieDetail getItem(int position) {
        return this.movieItems.get(position);
    }

    public void setLoadMoreItemRecyclerViewListener(LoadMoreItemRecyclerViewListener loadMoreItemRecyclerViewListener) {
        this.loadMoreItemRecyclerViewListener = loadMoreItemRecyclerViewListener;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
