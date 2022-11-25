package com.wildpress.model

import android.os.Parcelable
import com.wildpress.R
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
class Post (val text: String, val user1: String, val user2: String? = null, val pic: File? = null): Cardable, Parcelable {
    override fun getImage(): String {
        return "https://media.tenor.com/JotmWjykcjcAAAAd/cbum-skinny.gif"
    }

    override fun getTitle(): String {
        return user1
    }
}