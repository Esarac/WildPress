package com.wildpress.model

import android.os.Parcelable
import com.wildpress.R
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
class Post (val text: String, val user1: String, val user2: String? = null, val pic: File? = null): Cardable, Parcelable {
    override fun getImage(): Int {
        return R.drawable.ic_google
    }

    override fun getTitle(): String {
        return user1
    }
}