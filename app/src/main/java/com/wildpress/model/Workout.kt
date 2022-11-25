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

    fun totalTime(): Int {
        if((exercises.size < 1) || (rounds < 1))
            return 0

        var totalRestTime = (exerciseRest*(exercises.size-1)) + (roundRest*(rounds-1))

        var aproxExerciseTime = 15
        var totalExerciseTime = aproxExerciseTime * exercises.size * rounds

        return totalRestTime + totalExerciseTime
    }

    fun burnedCalories(): Int {//Change!
        var calories = 0
        exercises.forEach{
            calories += 20
        }
        return calories
    }

    fun exercisesSize() : Int = this.exercises.size

    fun totalExercisesSize(): Int = this.exercises.size * this.rounds
}