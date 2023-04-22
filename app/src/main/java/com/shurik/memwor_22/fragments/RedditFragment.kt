package com.shurik.memwor_22.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shurik.memwor_22.Constants
import com.shurik.memwor_22.R
import com.shurik.memwor_22.content.ItemAdapter
import com.shurik.memwor_22.content.artems_work.Post
import com.shurik.memwor_22.retrofit.RedditApiResponse
import com.shurik.memwor_22.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback

class RedditFragment : Fragment() {
    private val accessToken = Constants.ACCESS_TOKEN_REDDIT
    private var domaines = mutableListOf("Funny", "Gaming",
        "WorldNews", "Science", "Technology")
    private var contents: MutableList<Post> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_telegram, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        itemAdapter = ItemAdapter(contents)
        recyclerView.adapter = itemAdapter

        loadRedditContent()

        return view
    }

    private fun loadRedditContent() {
        for (domain in domaines) {
            RetrofitClient.redditInstance.getRedditCommunityInfo("Bearer $accessToken", domain)
                .enqueue(object : Callback<RedditApiResponse> {
                    override fun onResponse(
                        call: Call<RedditApiResponse>,
                        response: retrofit2.Response<RedditApiResponse>
                    ) {
                        if(response.isSuccessful) {
                            val redditData = response.body()?.data
                            redditData?.children?.forEach { child ->
                                val post = Post().apply {
                                    text = child.data.text.toString()
                                    images.addAll(child.data.images)
                                    videos.addAll(child.data.videos)
                                    author = domain
                                }
                                contents.add(post)
                                Log.d("Post:", "${post.author} + ${post.images} + ${post.videos} + ${post.text}")
                            }
                            itemAdapter.updatePosts(contents)
                        }
                        else Log.d("Error", "isSuccessful = false")
                    }

                    override fun onFailure(call: Call<RedditApiResponse>, t: Throwable) {
                        // Handle error
                    }


                })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RedditFragment()
    }
}