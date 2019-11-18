package com.example.newsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.newsapplication.retrofit.ArticleData
import com.example.newsapplication.viewpager.ArticlePageData
import com.example.newsapplication.viewpager.NewsFragmentStatePagerAdapter

class ArticleDisplayActivity : AppCompatActivity() {

    private lateinit var newsViewPager: ViewPager
    private lateinit var newsPageAdapter: NewsFragmentStatePagerAdapter
    private lateinit var arrayOfArticlePageData: Array<ArticlePageData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_display)
        getArticleDataExtra()
        createViewPagerAdapter()
    }

    private fun getArticleDataExtra(){
        val arrayOfArticleData = intent.getParcelableArrayExtra(MainActivity.EXTRA_RESPONSE) as Array<ArticleData>
        arrayOfArticlePageData = Array(arrayOfArticleData.size) {
            ArticlePageData(arrayOfArticleData[it].urlToImage, arrayOfArticleData[it].url) } //TODO url change for actual text from HTML on that url
    }

    private fun createViewPagerAdapter() {
        newsViewPager = findViewById(R.id.view_pager_news)
        newsPageAdapter = NewsFragmentStatePagerAdapter(supportFragmentManager,arrayOfArticlePageData)
        newsViewPager.adapter = newsPageAdapter
    }
}
