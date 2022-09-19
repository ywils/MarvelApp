package com.android.marvelapp.ui

import com.android.marvelapp.data.model.Thumbnail

object ComicUtil {
    fun prepareImageThumbnail(thumbnail: Thumbnail) : String {
        val httpsThumbnail = thumbnail.path.replace("http", "https")
        if (httpsThumbnail.endsWith(".${thumbnail.extension}")) {
            return httpsThumbnail
        }
        return "${httpsThumbnail}.${thumbnail.extension}"
    }

}