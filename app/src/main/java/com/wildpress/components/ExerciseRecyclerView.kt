package com.wildpress.components

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.wildpress.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wildpress.model.Exercise

class ExerciseRecyclerView(exercises: ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseRecyclerView.ExerciseViewHolder>(){
    private val exercises: ArrayList<Exercise> = exercises

    open inner class ExerciseViewHolder(itemView: View) : ViewHolder<Exercise>(itemView){
        private var exerciseImage: ImageView
        private var exerciseName : TextView

        init{
            exerciseImage = itemView.findViewById(R.id.exercise_item_image)
            exerciseName = itemView.findViewById(R.id.exercise_item_title)
        }

        override fun setItem(item: Exercise) {
            super.setItem(item)
            this.exerciseImage.setImageResource(R.drawable.ic_google)
            this.exerciseName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.exercise_view, parent, false)
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