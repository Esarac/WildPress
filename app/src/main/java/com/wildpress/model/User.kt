package com.wildpress.model
import java.io.Serializable

data class User (val id: String="", val username: String="", val firstName: String="", val lastName: String="", val aboutMe: String="", val listOfExercise: ArrayList<Exercise> = arrayListOf<Exercise>(), val listOfDiet: ArrayList<Diet> = arrayListOf<Diet>()){

}
