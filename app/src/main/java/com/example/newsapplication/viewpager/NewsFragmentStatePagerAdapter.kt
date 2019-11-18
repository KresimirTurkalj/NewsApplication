package com.example.newsapplication.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class NewsFragmentStatePagerAdapter(fragmentManager: FragmentManager, private val arrayOfArticleData: Array<ArticlePageData>) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return ArticleFragment.newInstance(arrayOfArticleData[position])
    }

    override fun getCount(): Int {
        return arrayOfArticleData.size
    }
}