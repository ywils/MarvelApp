package com.android.marvelapp.data.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicResponse(
    val code: Int,
    val status: String,
    val data: Data
)

@JsonClass(generateAdapter = true)
data class Data(
    val results: List<Result>
)

@JsonClass(generateAdapter = true)
data class Result(
    val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: Thumbnail,
    val images: List<Any>
)

@JsonClass(generateAdapter = true)
data class Thumbnail(
    val path: String,
    val extension: String
)


