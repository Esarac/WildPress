package com.wildpress.model

import android.os.Parcelable
import com.wildpress.R
import kotlinx.parcelize.Parcelize

@Parcelize
class Workout(val name: String = "", val description: String = "", val rounds: Int = 1, val exerciseRest: Int = 30, val roundRest: Int = 60, val exercises: ArrayList<Exercise> = ArrayList()): Cardable, Parcelable {
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