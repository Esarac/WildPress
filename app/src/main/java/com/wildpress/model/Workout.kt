package com.wildpress.model

import com.wildpress.R

class Workout(val name: String = "", val description: String = ""): Cardable {
    override fun getImage(): Int {
        return R.drawable.ic_google
    }

    override fun getTitle(): String {
        return name
    }
}