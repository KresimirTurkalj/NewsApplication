package com.example.newsapplication.newsdisplay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.newsapplication.data.NewsResponseData
import com.example.newsapplication.data.NewsResponseObservable
import com.example.newsapplication.data.NewsResponseObserver
import com.example.newsapplication.service.ServiceNewsLoad.Companion.EXTRA_ARTICLES
import com.example.newsapplication.service.ServiceNewsLoad.Companion.ACTION_SERVICE_LOADED

class ViewModelNewsDisplay(context: Context) : NewsResponseObservable{
    private val listOfNewsObserver = mutableListOf<NewsResponseObserver>()
    private var newsResponseData: NewsResponseData? = null
    init {
        LocalBroadcastManager.getInstance(context).registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                newsResponseData = intent.getSerializableExtra(EXTRA_ARTICLES) as NewsResponseData?
                notifyObserversAboutNews()
            }
        }, IntentFilter(ACTION_SERVICE_LOADED))
    }

    override fun addObserver(newsResponseObserver: NewsResponseObserver) {
        listOfNewsObserver.takeUnless {it.contains(newsResponseObserver) }?.add(newsResponseObserver)
    }

    override fun removeObserver(newsResponseObserver: NewsResponseObserver) {
        listOfNewsObserver.takeIf { it.contains(newsResponseObserver) }?.add(newsResponseObserver)
    }

    override fun notifyObserversAboutNews() {
        for (observer in listOfNewsObserver){
            observer.onNewsChange(newsResponseData)
        }
    }
}