package com.shurik.memwor_22.content.html

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import androidx.recyclerview.widget.RecyclerView
import com.shurik.memwor_22.R

class HtmlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val webView: WebView = itemView.findViewById(R.id.web_view)

    @SuppressLint("SetJavaScriptEnabled")
    fun bind(post: HtmlItem) {
        webView.settings.javaScriptEnabled = true
        webView.loadData(post.htmlContent, "text/html; charset=utf-8", "UTF-8")
    }
}
