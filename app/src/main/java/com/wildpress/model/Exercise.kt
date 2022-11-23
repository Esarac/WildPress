package com.wildpress.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Exercise(val image: String="", val name: String = "", val muscle: String="", val description: String = "", var selected: Boolean = false): Parcelable {

}
