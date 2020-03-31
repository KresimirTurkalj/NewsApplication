package com.example.newsapplication.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.newsapplication.data.NewsResponseData
import com.example.newsapplication.file.FileStorage
import com.example.newsapplication.retrofit.NewsService.Companion.instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ServiceNewsLoad : Service() {

    companion object {
        const val ACTION_SERVICE_LOADED = "com.example.newsapplication.service.NewsLoadService.action.SERVICE_LOADED"
        const val EXTRA_ARTICLES = "com.example.newsapplication.service.NewsLoadService.extra.EXTRA_ARTICLES"
        const val apiKey = "6946d0c07a1c4555a4186bfcade76398"
        const val sortBy = "top"
        const val source = "bbc-news"
    }

    class NewsServiceBinder : Binder()
    private val newsServiceBinder = NewsServiceBinder()
    private val newsApiCall = instance.getNewsArticles(source, sortBy, apiKey)
    private val fileStorage by lazy { FileStorage(this) }
    private val newsCallback = object : Callback<NewsResponseData> {

        override fun onFailure(call: Call<NewsResponseData?>?, t: Throwable) {
            sendNewsResponseBroadcast(null)
        }

        override fun onResponse(call: Call<NewsResponseData?>?, responseData: Response<NewsResponseData?>?) {
            sendNewsResponseBroadcast(responseData?.body())
            if (responseData != null) {
                val it = responseData.body()
                if (it != null) {
                   fileStorage.saveNews(it)
                }
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
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

    fun sendNewsResponseBroadcast(body: NewsResponseData?) {
        val broadcastIntent = Intent(ACTION_SERVICE_LOADED)
        broadcastIntent.putExtra(EXTRA_ARTICLES, body)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
        stopSelf()
    }
}