package com.wildpress.activities

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityWorkoutStartBinding
import com.wildpress.model.Exercise
import com.wildpress.model.Workout

class WorkoutStartActivity: AppCompatActivity() {
    //Binding
    private lateinit var binding : ActivityWorkoutStartBinding

    //Properties
    private lateinit var workout: Workout;
    private var exercise: Exercise? = null;

    private var roundIndex: Int = 0;
    private var exerciseIndex: Int = 0;

    private var restTimer: Int = 0;
    private var globalTimer: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("workout", Workout::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra<Workout>("workout")
        }

        //Initialize Screen
        if (workout != null) {
            this.workout = workout;
            this.exercise = this.workout.exercises[exerciseIndex]
            //Text
            binding.workoutStartExerciseNameText.text= this.exercise?.name

            //Toolbar
            Toolbar().showToolbar(this, this.workout.getTitle(), true)

            //Button
            binding.workoutStartPrevBtn.isEnabled = false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    fun nextExercise(){
        if(this.exercise == null) {
            this.exercise = this.workout.exercises[exerciseIndex]

            binding.workoutStartExerciseNameText.text = this.exercise?.name
        }
        else{
            this.exercise = null

            // Update Rest Timer
            if( exerciseIndex == (this.workout.exercises.size - 1) )
                this.restTimer = this.workout.roundRest
            else
                this.restTimer = this.workout.exerciseRest

            // Update Indexes
            this.exerciseIndex += 1

            if(this.exerciseIndex >= this.workout.exercises.size){
                this.exerciseIndex = 0
                this.roundIndex += 1
                if(this.roundIndex >= this.workout.rounds){
                    // Finish Workout
                }
            }

            binding.workoutStartExerciseNameText.text = "Rest"
        }
    }
}