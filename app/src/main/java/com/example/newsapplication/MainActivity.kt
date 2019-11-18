package com.example.newsapplication

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.recyclerview.NewsRecyclerViewAdapter
import com.example.newsapplication.retrofit.ArticleData
import com.example.newsapplication.retrofit.NewsResponseData

class MainActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_ARTICLE_LOAD = 1
        const val EXTRA_RESPONSE = "com.example.newsapplication.extra.NewsResponseData"
    }

    private lateinit var newsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startNewsDataLoadActivity()
    }

    private fun startNewsDataLoadActivity() {
        val intent = Intent(this, NewsDataLoadActivity::class.java)
        startActivityForResult(intent, REQUEST_ARTICLE_LOAD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ARTICLE_LOAD) {
            if (resultCode == Activity.RESULT_OK) {
                val newsResponseData = data?.getParcelableExtra<NewsResponseData?>(EXTRA_RESPONSE)
                if(newsResponseData != null){
                    createNewsRecyclerView(newsResponseData.articles)
                }
            }
            else{
                showDialogForFailure()
            }
        }
    }

    private fun createNewsRecyclerView(articleData: Array<ArticleData>) {
        newsRecyclerView = findViewById(R.id.recycler_view_news)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = NewsRecyclerViewAdapter(articleData)
    }

    private fun showDialogForFailure() {
        AlertDialog.Builder(this)
            .setTitle(R.string.title_call_failed)
            .setMessage(R.string.text_call_failed)
            .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int ->
            }
            .create()
            .show()
    }
}
