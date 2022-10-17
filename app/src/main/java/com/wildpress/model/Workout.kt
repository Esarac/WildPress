package com.wildpress.model

import android.os.Parcelable
import com.wildpress.R
import kotlinx.parcelize.Parcelize

@Parcelize
class Workout(val name: String = "", val description: String = "", val exercises: ArrayList<Exercise> = ArrayList()): Cardable, Parcelable {
    override fun getImage(): Int {
        return R.drawable.ic_google
    }

    override fun getTitle(): String {
        return name
    }

    fun addExercise(exercise: Exercise){
        exercises.add(exercise)
    }
}