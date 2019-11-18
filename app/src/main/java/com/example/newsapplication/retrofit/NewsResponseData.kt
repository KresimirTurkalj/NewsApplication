package com.example.newsapplication.retrofit

import android.os.Parcel
import android.os.Parcelable

data class NewsResponseData(
    val status: String,
    val source: String,
    val articles: Array<ArticleData>): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArray(ArticleData)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeString(source)
        parcel.writeTypedArray(articles, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsResponseData> {
        override fun createFromParcel(parcel: Parcel): NewsResponseData {
            return NewsResponseData(parcel)
        }

        override fun newArray(size: Int): Array<NewsResponseData?> {
            return arrayOfNulls(size)
        }
    }
}

data class ArticleData(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleData> {
        override fun createFromParcel(parcel: Parcel): ArticleData {
            return ArticleData(parcel)
        }

        override fun newArray(size: Int): Array<ArticleData?> {
            return arrayOfNulls(size)
        }
    }
}