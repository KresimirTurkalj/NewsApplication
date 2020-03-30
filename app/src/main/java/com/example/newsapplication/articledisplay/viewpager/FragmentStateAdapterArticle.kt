package com.example.newsapplication.articledisplay.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class FragmentStateAdapterArticle(
    holderFragmentActivity: FragmentActivity,
    private val arrayOfWebsiteLinks: Array<String>
) :
    FragmentStateAdapter(holderFragmentActivity) {
    override fun createFragment(position: Int): Fragment {

        return FragmentArticle.newInstance(arrayOfWebsiteLinks[position])
    }

    override fun getItemCount(): Int {
        return arrayOfWebsiteLinks.size
    }
}