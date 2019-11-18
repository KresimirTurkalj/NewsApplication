package com.example.newsapplication.viewpager

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object ArticleHelper {
    const val KEY_IMAGE_URI = "imageUri"
    const val KEY_TEXT = "text"

    fun getArticlesFromJson(fileName: String, context: Context): ArrayList<ArticlePageData> {

        val articles = ArrayList<ArticlePageData>()

        try {
            val jsonString = loadJsonFromFile(fileName, context)
            val json = JSONObject(jsonString)
            val jsonArticles = json.getJSONArray("articles")

            for (index in 0 until jsonArticles.length()) {
                val articleImage = jsonArticles.getJSONObject(index).getString(KEY_IMAGE_URI)
                val articleText = jsonArticles.getJSONObject(index).getString(KEY_TEXT)
                articles.add(ArticlePageData(articleImage,articleText))
            }
        } catch (e: JSONException) {
            return articles
        }

        return articles
    }

    private fun loadJsonFromFile(filename: String, context: Context): String {
        var json = ""

        try {
            val input = context.assets.open(filename)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            json = buffer.toString(Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return json
    }
}