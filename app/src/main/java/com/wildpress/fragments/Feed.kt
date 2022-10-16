package com.wildpress.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.R
import com.wildpress.activities.CreatePostActivity
import com.wildpress.components.CardRecyclerView
import com.wildpress.databinding.FragmentFeedBinding
import com.wildpress.model.Post

class Feed : Fragment(R.layout.fragment_feed) {

    private var _binding : FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: CardRecyclerView<Post>
    private var posts = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.posts.add(Post("Shared new routine","Esarac"))
        this.posts.add(Post("Check out my profile ;)","Expertogamer", "Voodlyc"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.layoutManager = LinearLayoutManager(context)
        this.adapter = CardRecyclerView(this.posts)
        binding.postRecyclerView.layoutManager = this.layoutManager
        binding.postRecyclerView.adapter = this.adapter

        binding.newPostBtn.setOnClickListener {
            startActivity(Intent(activity, CreatePostActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}