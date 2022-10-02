package com.wildpress.components

import androidx.appcompat.app.AppCompatActivity
import com.wildpress.R

class Toolbar {
    fun showToolbar(activity: AppCompatActivity, title: String, backButton: Boolean) {
        activity.setSupportActionBar(activity.findViewById(R.id.toolbar))
        activity.supportActionBar?.title = title
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(backButton)
    }
}