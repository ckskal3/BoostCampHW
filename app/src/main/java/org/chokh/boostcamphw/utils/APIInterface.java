package org.chokh.boostcamphw.utils;

import org.chokh.boostcamphw.data.MovieItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIInterface {
    String CLIENT_ID = "NoYGfE7uJwrmHfq0p_Ht";
    String CLIENT_SECRET = "OGIgTK3dUS";


    @Headers({
            "X-Naver-Client-Id: " + CLIENT_ID,
            "X-Naver-Client-Secret: " + CLIENT_SECRET
    })
    @GET("movie.json?")
    Call<MovieItem> doGetMovieListRetrofit2(@Query("query") String name, @Query("display") int show, @Query("start") int start);
}
