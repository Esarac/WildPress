package com.example.test170822

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.test170822.models.Exercise

class ExerciseAdapter(exercises: ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseView>(){

        private val exercises = exercises;

        class ExerciseView(view: View) : RecyclerView.ViewHolder(view){
                var exercise: Exercise? = null

                var onDeleteListener: OnDelete? = null

                var exerciseName : TextView
                var exerciseDescription : TextView
                var deleteButton : Button

                init{
                        exerciseName = view.findViewById(R.id.exerciseName)
                        exerciseDescription = view.findViewById(R.id.exerciseDescription)
                        deleteButton = view.findViewById(R.id.deleteButton)

                        deleteButton.setOnClickListener{
                                exercise?.let{
                                        onDeleteListener?.onDelete(it)
                                }
                        }
                }

                interface OnDelete{
                        fun onDelete(exercise: Exercise){

                        }
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseView {
                val inflater = LayoutInflater.from(parent.context)
                val row = inflater.inflate(R.layout.view_holder_exercises, parent, false)
                return ExerciseView(row)
        }

        override fun onBindViewHolder(holder: ExerciseView, index: Int) {
                val exercise = exercises[index]
                holder.exercise = exercise
                holder.exerciseName.text = exercise.name
                holder.exerciseDescription.text = exercise.description
        }

        override fun getItemCount(): Int = exercises.size

        fun addExercise(exercise: Exercise){
                this.exercises.add(exercise)
                notifyItemInserted(exercises.size - 1)
        }
}