package com.example.newsapplication.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R

class NewsRecyclerViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    val articleImageView = view.findViewById(R.id.image_view_article) as ImageView
    val articleTextView = view.findViewById(R.id.text_view_article) as TextView
}