package org.chokh.boostcamphw.adapter.contract;

import android.content.Context;

import org.chokh.boostcamphw.data.MovieItem;

import java.util.ArrayList;

public interface MovieAdapterContract {
    interface View{
        void showMovieDetailInChromeTabsIntent(Context context, int position);
        void notifyAdapter();
    }
    interface Model{
        void addItem(MovieItem.MovieDetail item);
        void addAllItem(ArrayList<MovieItem.MovieDetail> items);
        MovieItem.MovieDetail getItem(int position);
        void clearItem();
    }
}

