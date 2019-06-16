package com.abbyy.task01.network;

import com.abbyy.task01.beans.ArticleModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LingvoApi {

    @Headers("Content-Type: application/json")
    @POST("api/v1.1/authenticate")
    Single<String> get(@Header("Authorization") String value);

    @GET("api/v1/Article")
    Single<ArticleModel> translate(@Query("heading") String text,
                                         @Query("dict") String dictionary,
                                         @Query("srcLang") String sourceLang,
                                         @Query("dstLang") String destinationLang);
}
