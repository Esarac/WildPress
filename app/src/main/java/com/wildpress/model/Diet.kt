package com.wildpress.model

import com.wildpress.R

class Diet(val name: String = "", val description: String = "", val ingredients: String = ""): Cardable {
    override fun getImage(): Int {
        return R.drawable.ic_google
    }

    override fun getTitle(): String {
        return name
    }
}