package com.wildpress.components

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.wildpress.R
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.wildpress.model.Exercise

class ExerciseRecyclerView(exercises: ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseRecyclerView.ExerciseViewHolder>(){
    private val exercises: ArrayList<Exercise> = exercises;

    open inner class ExerciseViewHolder(view: View) : ViewHolder<Exercise>(view){
        var exerciseName : TextView
        var exerciseDescription : TextView

        init{
            exerciseName = view.findViewById(R.id.exerciseName)
            exerciseDescription = view.findViewById(R.id.exerciseDescription)
        }

        override fun setItem(exercise: Exercise) {
            super.setItem(exercise)
            this.exerciseName.text = exercise.name
            this.exerciseDescription.text = exercise.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_exercises, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ExerciseViewHolder, i: Int) {
        viewHolder.setItem(exercises[i])
    }

    override fun getItemCount(): Int = exercises.size

    fun addExercise(exercise: Exercise){
        this.exercises.add(exercise)
        notifyItemInserted(exercises.size - 1)
    }
}