package com.android.marvelapp.data.repository

import com.android.marvelapp.data.model.ComicResponse
import com.android.marvelapp.data.network.ApiUtils
import com.android.marvelapp.data.network.MarvelApi
import java.util.*
import javax.inject.Inject

class ComicRepository
@Inject
constructor(
    private val apiClient: MarvelApi
    ) {

    suspend fun getComic(comicID: Int) : ComicResponse {
        val timestamp = Calendar.getInstance().timeInMillis.toString()
        return apiClient.getComic(
            id = comicID,
            ts = timestamp,
            hash = ApiUtils.getMd5String(timestamp)
        )
    }
}