package com.wildpress.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wildpress.R
import com.wildpress.activities.CreateDietActivity
import com.wildpress.activities.CreatePostActivity
import com.wildpress.databinding.FragmentFeedBinding

class Feed : Fragment(R.layout.fragment_feed) {

    private var _binding : FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newPostBtn.setOnClickListener {
            startActivity(Intent(activity, CreatePostActivity::class.java))
            println("Funciona")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}