package com.example.newsapplication.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.newsapplication.data.NewsResponseData
import com.example.newsapplication.file.FileStorage
import com.example.newsapplication.retrofit.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsLoadService : Service() {

    companion object {
        const val source = "bbc-news"
        const val sortBy = "top"
        const val apiKey = "6946d0c07a1c4555a4186bfcade76398"
        const val ACTION_SERVICE_LOADED =
            "com.example.newsapplication.service.NewsLoadService.action.SERVICE_LOADED"
        const val EXTRA_ARTICLES =
            "com.example.newsapplication.service.NewsLoadService.extra.EXTRA_ARTICLES"
    }

    class NewsServiceBinder : Binder()

    private val newsServiceBinder = NewsServiceBinder()
    private val newsService = NewsService.instance
    private val newsApiCall = newsService.getNewsArticles(source, sortBy, apiKey)
    private val fileStorage by lazy { FileStorage(this) }
    private val newsCallback = object : Callback<NewsResponseData> {
        override fun onFailure(call: Call<NewsResponseData>?, t: Throwable?) {
            sendNewsResponseBroadcast(null)
        }

        override fun onResponse(
            call: Call<NewsResponseData>?,
            responseData: Response<NewsResponseData>?
        ) {
            sendNewsResponseBroadcast(responseData?.body())
            responseData?.body()?.let { fileStorage.saveNews(it) }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        if (fileStorage.isNewsResponseUpdated()) {
            sendNewsResponseBroadcast(fileStorage.loadNews())
        } else {
            callNewsApi()
        }
        return newsServiceBinder
    }

    private fun callNewsApi() {
        newsApiCall.enqueue(newsCallback)
    }

    private fun sendNewsResponseBroadcast(body: NewsResponseData?) {
        val broadcastIntent = Intent(ACTION_SERVICE_LOADED)
        broadcastIntent.putExtra(EXTRA_ARTICLES, body)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
        this.stopSelf()
    }
}
