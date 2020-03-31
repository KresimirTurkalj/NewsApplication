package com.example.newsapplication.file

import android.content.Context
import com.example.newsapplication.data.ArticleData
import com.example.newsapplication.data.NewsResponseData
import java.io.File
import java.util.*

class FileStorage(context: Context) {

    companion object{
        private const val millisToMinutes = 60000
        private const val fileName = "news_response"
        private const val endLine = "\n"
    }

    private val file = File(context.filesDir, fileName)
    private lateinit var fileData: MutableList<String>

    fun isNewsResponseUpdated(): Boolean{
        return if(!file.canRead()){
            false
        } else{
            fileData = file.readLines().toMutableList()
            isFreshNews(fileData[0].toLong())
        }
    }

    private fun isFreshNews(time: Long): Boolean {
        return Date().time - time < 5 * millisToMinutes
    }

    fun loadNews(): NewsResponseData{
        fileData.removeAt(0)
        val status = fileData.first().also { fileData.remove(it) }
        val mutableListOfArticleData = mutableListOf<ArticleData>()
        for(i in fileData.indices step 6){
            mutableListOfArticleData.add(ArticleData(
                fileData[i],
                fileData[i+1],
                fileData[i+2],
                fileData[i+3],
                fileData[i+4],
                fileData[i+5]
            ))
        }
        return NewsResponseData(status, mutableListOfArticleData.toTypedArray())
    }

    fun saveNews(newsResponseData: NewsResponseData){
        if(!file.exists()) file.createNewFile()
        val articles = newsResponseData.getArticles()
        file.writeText(Date().time.toString() + endLine)
        file.appendText(newsResponseData.getStatus() + endLine)
        for(article in articles) run {
            file.appendText(article.author + endLine)
            file.appendText(article.title + endLine)
            file.appendText(article.description + endLine)
            file.appendText(article.url + endLine)
            file.appendText(article.urlToImage + endLine)
            file.appendText(article.publishedAt + endLine)
        }
    }
}
