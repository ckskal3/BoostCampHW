package org.chokh.boostcamphw.activity.contract;

import org.chokh.boostcamphw.adapter.contract.MovieAdapterContract;
import org.chokh.boostcamphw.data.MovieItem;

import java.util.ArrayList;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private MovieAdapterContract.Model adapterModel;
    private MovieAdapterContract.View adapterView;


    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }


    @Override
    public void loadItems(ArrayList<MovieItem.MovieDetail> items, boolean isClear) {
        ArrayList<MovieItem.MovieDetail> movieItems =  new ArrayList<>();
        movieItems.addAll(items);
        if(isClear){
            adapterModel.clearItem();
        }
        adapterModel.addAllItem(movieItems);
        adapterView.notifyAdapter();
    }

    @Override
    public void setImageAdapterModel(MovieAdapterContract.Model adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void setImageAdapterView(MovieAdapterContract.View adapterView) {
        this.adapterView = adapterView;
    }
}
