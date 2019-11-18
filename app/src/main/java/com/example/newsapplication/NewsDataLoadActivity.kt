package com.example.newsapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.newsapplication.retrofit.NewsResponseData
import com.example.newsapplication.retrofit.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDataLoadActivity : AppCompatActivity() {

    companion object{
        const val source = "bbc-news"
        const val sortBy = "top"
        const val apiKey = "6946d0c07a1c4555a4186bfcade76398"
    }

    private val newsService = NewsService.instance
    private val newsApiCall = newsService.getNewsArticles(source, sortBy, apiKey)
    private val newsCallback = object : Callback<NewsResponseData> {
        override fun onFailure(call: Call<NewsResponseData>?, t: Throwable?) {
            finishActivityWithResult(Activity.RESULT_CANCELED, null)

        }
        override fun onResponse(call: Call<NewsResponseData>?, responseData: Response<NewsResponseData>?) {
            finishActivityWithResult(Activity.RESULT_OK, responseData?.body())
        }

        private fun finishActivityWithResult(result: Int, body: NewsResponseData?) {
            val returnIntent = Intent(this@NewsDataLoadActivity,MainActivity::class.java)
            if(body != null) {
                returnIntent.putExtra(MainActivity.EXTRA_RESPONSE, body)
            }
            setResult(result, returnIntent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_data_load)
        newsApiCall.enqueue(newsCallback)
        addRotationToLoadingImage()
    }

    private fun addRotationToLoadingImage() {
        val imageView = findViewById<ImageView>(R.id.image_view_loading)
        val rotatingAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation)
        imageView.startAnimation(rotatingAnimation)
    }
}
