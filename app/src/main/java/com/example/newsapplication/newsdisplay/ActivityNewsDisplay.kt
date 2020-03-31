package com.example.newsapplication.newsdisplay

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.articledisplay.ActivityArticleDisplay
import com.example.newsapplication.data.ArticleData
import com.example.newsapplication.data.NewsResponseData
import com.example.newsapplication.data.NewsResponseObserver
import com.example.newsapplication.newsdisplay.recyclerview.ItemClickObserver
import com.example.newsapplication.newsdisplay.recyclerview.NewsRecyclerViewAdapter
import com.example.newsapplication.service.ServiceNewsLoad


class ActivityNewsDisplay : AppCompatActivity(), NewsResponseObserver, ItemClickObserver {

    companion object{
        const val EXTRA_ARTICLE_ARRAY = "com.example.newsapplication.extra.Array<ArticleData>"
        const val EXTRA_ARTICLE_POSITION = "com.example.newsapplication.extra.Int"
    }

    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var viewModelNewsDisplay: ViewModelNewsDisplay

    private val newsServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {}
        override fun onServiceConnected(name: ComponentName, service: IBinder) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initializeViewModel()
        bindToNewsDataLoadService()
    }

    private fun initializeViewModel() {
        viewModelNewsDisplay = ViewModelNewsDisplay(this)
        viewModelNewsDisplay.addObserver(this)
    }

    private fun bindToNewsDataLoadService() {
        bindService(Intent(this, ServiceNewsLoad::class.java), newsServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onNewsChange(newsResponseData: NewsResponseData?) {
        if (newsResponseData != null) {
            createNewsRecyclerView(newsResponseData)
        } else {
            showDialogForFailure()
        }
    }

    private fun createNewsRecyclerView(newsResponseData: NewsResponseData) {
        setContentView(R.layout.activity_news_list)
        newsRecyclerView = findViewById(R.id.recycler_view_news)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = NewsRecyclerViewAdapter(this, newsResponseData.getArticles())
    }

    private fun showDialogForFailure() {
        AlertDialog.Builder(this)
            .setTitle(R.string.title_call_failed)
            .setMessage(R.string.text_call_failed)
            .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int -> }
            .create().show()
    }

    override fun onItemClicked(arrayOfArticleData: Array<ArticleData>, position: Int) {
        val intent = Intent(this, ActivityArticleDisplay::class.java)
        intent.putExtra(EXTRA_ARTICLE_ARRAY, arrayOfArticleData)
        intent.putExtra(EXTRA_ARTICLE_POSITION, position)
        startActivity(intent)
    }
}
