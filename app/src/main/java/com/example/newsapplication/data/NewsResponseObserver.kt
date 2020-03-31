package com.example.newsapplication.data

interface NewsResponseObserver {
    fun onNewsChange(newsResponseData: NewsResponseData?)
}