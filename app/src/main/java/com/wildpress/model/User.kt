package com.wildpress.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
class User (val id: String="", val username: String="", val firstName: String="", val lastName: String="", val aboutMe: String="", val listOfExercise: ArrayList<Exercise> = arrayListOf<Exercise>(), val listOfDiet: ArrayList<Diet> = arrayListOf<Diet>(),val workOutSet: Workout = Workout("","",0,0,0, ArrayList())): Parcelable{

}
