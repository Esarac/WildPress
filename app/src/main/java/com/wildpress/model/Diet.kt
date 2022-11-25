package com.wildpress.model

import android.os.Parcelable
import com.wildpress.R
import kotlinx.parcelize.Parcelize

@Parcelize
class Diet(val name: String = "", val description: String = "", val ingredients: String = ""): Cardable, Parcelable {

    override fun getImage(): String {
        return "https://www.comedera.com/wp-content/uploads/2021/12/ensalada-de-lechuga1.jpg"
    }

    override fun getTitle(): String {
        return name
    }
}