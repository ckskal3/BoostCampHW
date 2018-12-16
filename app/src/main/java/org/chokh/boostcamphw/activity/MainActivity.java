package org.chokh.boostcamphw.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.chokh.boostcamphw.MyApplication;
import org.chokh.boostcamphw.R;
import org.chokh.boostcamphw.activity.contract.MainPresenter;
import org.chokh.boostcamphw.adapter.MovieListRecyclerAdapter;
import org.chokh.boostcamphw.data.MovieItem;
import org.chokh.boostcamphw.listener.LoadMoreItemRecyclerViewListener;
import org.chokh.boostcamphw.utils.APIInterface;
import org.chokh.boostcamphw.utils.NetworkStatus;
import org.chokh.boostcamphw.utils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends BaseActivity implements View.OnClickListener, LoadMoreItemRecyclerViewListener {

    private EditText searchKeyEditTextView;
    private Button searchButton;
    private RecyclerView movieListRecyclerView;
    private MovieListRecyclerAdapter movieListRecyclerAdapter;

    private ArrayList<MovieItem.MovieDetail> items = new ArrayList<>();

    private APIInterface apiInterface;
    private MainPresenter mainPresenter;
    private LinearLayoutManager layoutManager;
    private InputMethodManager inputMethodManager;

    private String oldSearchKey, newSearchKey;
    private int totalCount;

    private static final int DEFAULT_ITEM_COUNT = 10;//서버로 부터 10개씩 데이터 받아옴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setView();

        setViewListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.getConnectivityStatus(getApplicationContext()) == NetworkStatus.TYPE_NOT_CONNECTED) {
            Toast.makeText(getApplicationContext(), getString(R.string.inter_connect_plz), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    public void init() {
        searchButton = findViewById(R.id.search_button);
        searchKeyEditTextView = findViewById(R.id.search_key_edit_text_view);
        movieListRecyclerView = findViewById(R.id.movie_list_recycler_view);

        apiInterface = RetrofitClient.getClient().create(APIInterface.class);

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        movieListRecyclerAdapter = new MovieListRecyclerAdapter();

        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


    }

    @Override
    protected void setView() {
        mainPresenter.setImageAdapterModel(movieListRecyclerAdapter);
        mainPresenter.setImageAdapterView(movieListRecyclerAdapter);

        movieListRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        movieListRecyclerView.setLayoutManager(layoutManager);
        movieListRecyclerView.setAdapter(movieListRecyclerAdapter);
    }

    @Override
    protected void setViewListener() {
        searchButton.setOnClickListener(this);

        movieListRecyclerAdapter.setLoadMoreItemRecyclerViewListener(this);

    }


    @Override
    public void searchMovieList(final String searchKey, int startIndex, final boolean isClear) {
        MyApplication.newInstance().onDialogShow(this);

        Call<MovieItem> call = apiInterface.doGetMovieListRetrofit2(searchKey, DEFAULT_ITEM_COUNT, startIndex);
        call.enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(@NonNull Call<MovieItem> call, @NonNull retrofit2.Response<MovieItem> response) {
                if (response.body() != null) {
                    totalCount = response.body().total;
                    movieListRecyclerAdapter.setTotalCount(totalCount);
                    for (MovieItem.MovieDetail movieDetail : response.body().items) {
                        movieDetail.title = movieDetail.title.replace("<b>", "");
                        movieDetail.title = movieDetail.title.replace("</b>", "");

                        items.add(movieDetail);
                    }
                    MyApplication.newInstance().onDialogHide();
                    mainPresenter.loadItems(items, isClear);
                    if (response.body().total < 1)
                        Toast.makeText(getApplicationContext(), String.format(getString(R.string.no_search_result_kr), searchKey), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {
                MyApplication.newInstance().onDialogHide();
            }
        });
    }

    @Override
    public void onLoadMore(int pageIndex) {
        searchMovieList(newSearchKey, pageIndex + 4, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                inputMethodManager.hideSoftInputFromWindow(searchKeyEditTextView.getWindowToken(), 0);

                boolean isClear = false;
                oldSearchKey = searchKeyEditTextView.getText().toString();

                if (oldSearchKey.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.search_key_plz, Toast.LENGTH_SHORT).show();
                } else {
                    newSearchKey = oldSearchKey;
                    if (items.size() != 0 && movieListRecyclerAdapter.getItemCount() != 0) {
                        items.clear();
                        isClear = true;
                    }
                    searchMovieList(newSearchKey, 1, isClear);
                }
                break;

            default:
                break;
        }

    }

}
