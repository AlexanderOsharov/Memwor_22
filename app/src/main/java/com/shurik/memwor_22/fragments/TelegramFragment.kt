package com.shurik.memwor_22.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shurik.memwor_22.content.ItemAdapter
import com.shurik.memwor_22.content.Post
import com.shurik.memwor_22.content.html.HtmlAdapter
import com.shurik.memwor_22.content.html.HtmlItem
import com.shurik.memwor_22.content.html.UrlWithHtml
import com.shurik.memwor_22.databinding.FragmentTelegramBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.util.regex.Pattern

class TelegramFragment : Fragment(){

    private lateinit var binding: FragmentTelegramBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HtmlAdapter

    var domaines = mutableListOf<String>("xor_journal", "kladovaya_knig",
        "prgbooks_archive", "+yVwn8nVbzkIxOTA6", "HtmlGram1")
    var links = mutableListOf<String>("https://t.me/xor_journal/250", "https://t.me/xor_jornal/273", "https://t.me/beautiful_planet17/4171")
    var contents: MutableList<Post> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTelegramBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = HtmlAdapter(ArrayList())

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        loadRandomUrls()
    }

    private fun loadRandomUrls() {
        val randomLinks = links.shuffled().take(10)
        adapter.updateUrls(randomLinks)

        // Загрузка следующих ссылок при прокрутке до конца списка
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView.layoutManager?.itemCount
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    loadRandomUrls()
                }
            }
        })
    }



    companion object {
        @JvmStatic
        fun newInstance() = TelegramFragment()
    }
}