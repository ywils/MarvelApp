package com.android.marvelapp.data.network

import com.android.marvelapp.BuildConfig
import com.android.marvelapp.data.model.ComicResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/comics/{comicId}")
    suspend fun getComic(@Path("comicId") id: Int,
                         @Query("apikey") apikey: String = BuildConfig.PUBLIC_API_KEY,
                         @Query("ts") ts: String,
                         @Query("hash") hash: String
    ): ComicResponse
}