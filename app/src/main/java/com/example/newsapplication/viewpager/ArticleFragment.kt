package com.example.newsapplication.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.newsapplication.R
import com.squareup.picasso.Picasso

class ArticleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        val view = inflater.inflate(R.layout.fragment_page_article, container, false)
        val articleTextView = view.findViewById<TextView>(R.id.fragment_text_view)
        val articleImageView = view.findViewById<ImageView>(R.id.fragment_image_view)
        articleTextView.text = args!!.getString(ArticleHelper.KEY_TEXT)
        Picasso.get().load(
            resources.getIdentifier(
                args.getString(ArticleHelper.KEY_IMAGE_URI),
                "drawable",
                activity?.packageName
            )
        )
            .into(articleImageView)

        return view
    }

    companion object {
        fun newInstance(articlePageData: ArticlePageData): ArticleFragment {

            val args = Bundle()
            args.putString(ArticleHelper.KEY_IMAGE_URI, articlePageData.articleImageUri)
            args.putString(ArticleHelper.KEY_TEXT, articlePageData.articleText)

            val fragment = ArticleFragment()
            fragment.arguments = args
            return fragment
        }
    }

}