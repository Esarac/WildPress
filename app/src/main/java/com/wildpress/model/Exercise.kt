package com.wildpress.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Exercise(val image: String="", var name: String = "", val muscle: Muscle=Muscle.CHEST, val description: String = "", var selected: Boolean = false, val repetitions: Int = 12): Parcelable {

}
