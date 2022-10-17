package com.wildpress.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wildpress.components.ExerciseRecyclerView
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityWorkoutBinding
import com.wildpress.model.Exercise
import com.wildpress.model.Workout

class WorkoutActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding : ActivityWorkoutBinding

    //Properties
    private lateinit var workout : Workout;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("workout", Workout::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra<Workout>("workout")
        }

        //Initialize Screen
        if (workout != null) {
            this.workout = workout;
            //Recycler View
            binding.workoutExercisesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
            binding.workoutExercisesRecyclerView.adapter = ExerciseRecyclerView(this.workout.exercises)

            //Toolbar
            Toolbar().showToolbar(this, this.workout.getTitle(), true)
        }

        //Listeners
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}