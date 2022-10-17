package com.wildpress.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.R
import com.wildpress.components.CardRecyclerView
import com.wildpress.components.ExerciseRecyclerView
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityExerciseBinding
import com.wildpress.databinding.ActivityWorkoutBinding
import com.wildpress.model.Exercise
import com.wildpress.model.Workout

class ExerciseActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityExerciseBinding

    //Properties
    private var exercises = ArrayList<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.exerciseRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        binding.exerciseRecyclerView.adapter = ExerciseRecyclerView(this.exercises)

        //Exercises
        this.exercises.add(Exercise("Pull up", "The best exercise ever!"))
        this.exercises.add(Exercise("Push up", "The best exercise ever!"))
        this.exercises.add(Exercise("Chin up", "The best exercise ever!"))
        this.exercises.add(Exercise("Diamond push up", "The best exercise ever!"))
        this.exercises.add(Exercise("Wide grip pull up", "The best exercise ever!"))
        this.exercises.add(Exercise("Close grip pull up", "The best exercise ever!"))

        //Initialize toolbar
        Toolbar().showToolbar(this, "Exercise", true)

        //Listeners
        binding.exerciseCreateBtn.setOnClickListener {
            startActivity(Intent(this, CreateExerciseActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}