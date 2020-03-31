package com.example.newsapplication.data

interface NewsResponseObservable {
    fun addObserver(newsResponseObserver: NewsResponseObserver)
    fun removeObserver(newsResponseObserver: NewsResponseObserver)
    fun notifyObserversAboutNews()
}