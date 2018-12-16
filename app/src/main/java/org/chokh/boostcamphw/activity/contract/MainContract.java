package org.chokh.boostcamphw.activity.contract;

import org.chokh.boostcamphw.adapter.contract.MovieAdapterContract;
import org.chokh.boostcamphw.data.MovieItem;

import java.util.ArrayList;

public interface MainContract {
    interface View{
        void searchMovieList(String searchKey, int startIndex, boolean isClear);
    }
    interface Presenter {
        void attachView(View view);

        void detachView();

        void loadItems(ArrayList<MovieItem.MovieDetail> items, boolean isClear);

        void setImageAdapterModel(MovieAdapterContract.Model adapterModel);

        void setImageAdapterView(MovieAdapterContract.View adapterView);
    }
}
