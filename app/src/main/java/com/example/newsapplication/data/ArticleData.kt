package com.example.newsapplication.data

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class ArticleData(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String) : Serializable
