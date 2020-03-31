package com.example.newsapplication.articledisplay

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapplication.R
import com.example.newsapplication.articledisplay.viewpager.FragmentStateAdapterArticle


class ActivityArticleDisplay : FragmentActivity() {

    private lateinit var viewPagerArticle: ViewPager2
    private lateinit var viewModelArticleDisplay: ViewModelArticleDisplay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_display)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        viewModelArticleDisplay = ViewModelArticleDisplay(intent)
        initializeViewPagerAdapter()
    }

    private fun initializeViewPagerAdapter(){
        viewPagerArticle = findViewById(R.id.view_pager_article)
        viewPagerArticle.adapter = FragmentStateAdapterArticle(this, viewModelArticleDisplay.arrayOfWebsiteLinks)
        viewPagerArticle.currentItem = viewModelArticleDisplay.startPosition
    }
}
