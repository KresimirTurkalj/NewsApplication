package com.example.newsapplication.newsdisplay.recyclerview

import com.example.newsapplication.data.ArticleData

interface ItemClickObserver {
    fun onItemClicked(arrayOfArticleData: Array<ArticleData>, position: Int)
}