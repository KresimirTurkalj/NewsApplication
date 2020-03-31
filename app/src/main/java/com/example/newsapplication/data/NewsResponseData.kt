package com.example.newsapplication.data

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class NewsResponseData(
    private val status: String,
    private val articles: Array<ArticleData>) :Serializable{

    fun getArticles(): Array<ArticleData>{
        return articles
    }

    fun getStatus():String{
        return status
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsResponseData

        if (status != other.status) return false
        if (!articles.contentEquals(other.articles)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + articles.contentHashCode()
        return result
    }

}