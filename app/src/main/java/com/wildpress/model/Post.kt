package com.wildpress.model

import com.wildpress.R

class Post (val text: String, val user1: String, val user2: String? = null): Cardable{
    override fun getImage(): Int {
        return R.drawable.ic_google
    }

    override fun getTitle(): String {
        return user1
    }
}