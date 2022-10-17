package com.wildpress.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Exercise(val name: String = "", val description: String = ""): Parcelable {

}
