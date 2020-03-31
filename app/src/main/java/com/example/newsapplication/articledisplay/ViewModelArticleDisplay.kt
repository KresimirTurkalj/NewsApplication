package com.example.newsapplication.articledisplay

import android.content.Intent
import com.example.newsapplication.data.ArticleData
import com.example.newsapplication.newsdisplay.ActivityNewsDisplay
import java.io.Serializable

class ViewModelArticleDisplay(private val intent: Intent){
    private lateinit var arrayOfArticleData: Array<ArticleData>
    lateinit var arrayOfWebsiteLinks: Array<String>
        private set
    var startPosition = 0
        private set

    init {
        createArrayOfWebsiteLinks()
    }

    private fun createArrayOfWebsiteLinks(){
        val result = intent.getSerializableExtra(ActivityNewsDisplay.EXTRA_ARTICLE_ARRAY) as Array<Serializable>
        arrayOfArticleData = result.mapNotNull { it as? ArticleData }.toTypedArray()
        startPosition = intent.getIntExtra(ActivityNewsDisplay.EXTRA_ARTICLE_POSITION, 0)
        arrayOfWebsiteLinks = Array(arrayOfArticleData.size) { arrayOfArticleData[it].url }
    }
}