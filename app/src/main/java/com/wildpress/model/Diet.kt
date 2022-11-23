package com.wildpress.model

import android.os.Parcelable
import com.wildpress.R
import kotlinx.parcelize.Parcelize

@Parcelize
class Diet(val name: String = "", val description: String = "", val ingredients: String = ""): Cardable, Parcelable {

    override fun getImage(): Int {
        return R.drawable.ic_google
    }

    override fun getTitle(): String {
        return name
    }
}