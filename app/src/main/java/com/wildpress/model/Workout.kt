package com.wildpress.model

import android.os.Parcelable
import com.wildpress.R
import kotlinx.parcelize.Parcelize

@Parcelize
class Workout(val name: String = "", val description: String = "", val rounds: Int = 1, val exerciseRest: Int = 30, val roundRest: Int = 60, val exercises: ArrayList<Exercise> = ArrayList()): Cardable, Parcelable {
    override fun getImage(): String {
        if(exercises.size < 1)
            return ""

        var indexLast = exercises.size - 1
        return exercises[(0..indexLast).random()].image
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

        var totalExerciseTime = 0
        exercises.forEach{
            totalExerciseTime += (it.repetitions*rounds*2)//Change!
        }

        return totalRestTime + totalExerciseTime
    }

    fun burnedCalories(): Int {
        var calories = 0
        exercises.forEach{
            calories += (it.repetitions*rounds*2)//Change!
        }
        return calories
    }

    fun trainedMuscles(): List<Pair<Muscle, Int>> {
        var musclesIntensity = HashMap<Muscle, Int> ()

        exercises.forEach {
            if (musclesIntensity.containsKey(it.muscle))
                musclesIntensity[it.muscle] = (it.repetitions + musclesIntensity[it.muscle]!!)
            else
                musclesIntensity[it.muscle] = it.repetitions
        }

        return musclesIntensity.toList().sortedBy {
            it.second
        }.asReversed()
    }

    fun exercisesSize() : Int = this.exercises.size

    fun totalExercisesSize(): Int = this.exercises.size * this.rounds
}