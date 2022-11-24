package com.wildpress.activities

import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreateWorkoutBinding
import com.wildpress.model.Workout


class CreateWorkout : AppCompatActivity() {

    //Binding
    private lateinit var binding : ActivityCreateWorkoutBinding

    //Properties
    private lateinit var workout : Workout;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize toolbar
        Toolbar().showToolbar(this, "New Workout", true)

        binding.workoutCreRoundsRest1.minValue = 0
        binding.workoutCreRoundsRest1.maxValue = 60
        binding.workoutCreRoundsRest1.value = 1
        binding.workoutCreRoundsRest1.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })

        binding.workoutCreRoundsRest2.minValue = 0
        binding.workoutCreRoundsRest2.maxValue = 59
        binding.workoutCreRoundsRest2.value = 0
        binding.workoutCreRoundsRest2.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })

        binding.workoutCreExerciseRest1.minValue = 0
        binding.workoutCreExerciseRest1.maxValue = 60
        binding.workoutCreExerciseRest1.value = 1
        binding.workoutCreExerciseRest1.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })

        binding.workoutCreExerciseRest2.minValue = 0
        binding.workoutCreExerciseRest2.maxValue = 59
        binding.workoutCreExerciseRest2.value = 0
        binding.workoutCreExerciseRest2.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })

        binding.workoutCreRounds.minValue = 1
        binding.workoutCreRounds.maxValue = 30
        binding.workoutCreRounds.value = 1
        //Listeners
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}