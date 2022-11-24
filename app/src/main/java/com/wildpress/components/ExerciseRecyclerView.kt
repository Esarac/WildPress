package com.wildpress.components

import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.wildpress.R
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.wildpress.model.Exercise

class ExerciseRecyclerView(exercises: ArrayList<Exercise>, selected: TextView? = null, readyBtn: Button? = null) : RecyclerView.Adapter<ExerciseRecyclerView.ExerciseViewHolder>(){
    private val exercises: ArrayList<Exercise> = exercises
    private var itemSelectedList = mutableListOf<Int>()
    private val selectedTextView: TextView? = selected
    private var selectMode: Boolean = false
    private val readyBtn: Button? = readyBtn

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
        fun changeTextViewColor(backgroundColor: Int, textColor: Int) {
            exerciseName.setBackgroundColor(backgroundColor)
            exerciseName.setTextColor(textColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.exercise_view, parent, false)
        return ExerciseViewHolder(view)
    }

    private fun select(item: Exercise, viewHolder: ExerciseViewHolder, position: Int) {
        itemSelectedList.add(position)
        item.selected = true
        Log.i("Information", "LongClick -> position: $position")
        viewHolder.changeTextViewColor(Color.rgb(255, 102, 36), Color.WHITE)
        val items = itemSelectedList.size.toString() + " Selected"
        selectedTextView?.text = items
    }

    private fun unSelect(item: Exercise, viewHolder: ExerciseViewHolder, position: Int) {
        itemSelectedList.remove(position)
        item.selected = false
        Log.i("Information", "Click -> position: $position")
        viewHolder.changeTextViewColor(Color.WHITE, Color.rgb(255, 102, 36))
        var items = itemSelectedList.size.toString() + " Selected"
        if(itemSelectedList.size == 0) {
            items = "Select exercises"
        }
        selectedTextView?.text = items
    }

    override fun onBindViewHolder(viewHolder: ExerciseViewHolder, i: Int) {
        val item = exercises[i]
        viewHolder.setItem(item)

        viewHolder.itemView.setOnLongClickListener {
            if(!item.selected){
                select(item, viewHolder, i)

                if(itemSelectedList.size == 1) {
                    selectMode = true
                    readyBtn?.isEnabled = true
                }
            }
            true
        }

        viewHolder.itemView.setOnClickListener {
            if(selectMode && !item.selected) {
                select(item, viewHolder, i)
            }
            else {
                unSelect(item, viewHolder, i)

                if(itemSelectedList.size == 0) {
                    selectMode = false
                    readyBtn?.isEnabled = false
                }


            }
        }
    }

    override fun getItemCount(): Int = exercises.size

    fun addExercise(exercise: Exercise){
        this.exercises.add(exercise)
        notifyItemInserted(exercises.size - 1)
    }

    fun getSelectedItems(): MutableList<Int> {
        return itemSelectedList
    }
}