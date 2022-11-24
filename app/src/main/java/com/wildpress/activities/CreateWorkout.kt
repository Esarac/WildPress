package com.wildpress.activities

import android.content.Intent
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreateWorkoutBinding
import com.wildpress.model.Workout


class CreateWorkout : AppCompatActivity() {

    //Binding
    private lateinit var binding : ActivityCreateWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize toolbar
        Toolbar().showToolbar(this, "New Workout", true)

        //Round Rest
        binding.workoutCreRoundsRest1.minValue = 0
        binding.workoutCreRoundsRest1.maxValue = 60
        binding.workoutCreRoundsRest1.value = 1
        binding.workoutCreRoundsRest1.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })

        binding.workoutCreRoundsRest2.minValue = 0
        binding.workoutCreRoundsRest2.maxValue = 59
        binding.workoutCreRoundsRest2.value = 0
        binding.workoutCreRoundsRest2.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })

        //Exercise Rest
        binding.workoutCreExerciseRest1.minValue = 0
        binding.workoutCreExerciseRest1.maxValue = 60
        binding.workoutCreExerciseRest1.value = 1
        binding.workoutCreExerciseRest1.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })

        binding.workoutCreExerciseRest2.minValue = 0
        binding.workoutCreExerciseRest2.maxValue = 59
        binding.workoutCreExerciseRest2.value = 0
        binding.workoutCreExerciseRest2.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })

        //Rounds
        binding.workoutCreRounds.minValue = 1
        binding.workoutCreRounds.maxValue = 30
        binding.workoutCreRounds.value = 1


        fun checkRequiredFields(): String {
            var message = ""
            val values = arrayOf(Pair("workout name", binding.workoutCreNameEditText.text.toString().trim()))

            for (i in values.indices) {
                if( values[i].second.isEmpty()) {
                    message = "Please fill the " + values[i].first + " field"
                    break
                }
            }
            return message
        }

        //Listeners
        binding.workoutCreSubmitBtn.setOnClickListener {
            val message = checkRequiredFields()
            if(message.isEmpty()) {
                val roundRest = (binding.workoutCreRoundsRest1.value * 60) + binding.workoutCreRoundsRest2.value
                val exerciseRest = (binding.workoutCreExerciseRest1.value * 60) + binding.workoutCreExerciseRest2.value
                val workout = Workout(name = binding.workoutCreNameEditText.text.toString(),
                   description = binding.workoutCreDescriptionEditText.text.toString(), rounds = binding.workoutCreRounds.value,
                    roundRest = roundRest, exerciseRest = exerciseRest)

                val intent = Intent(this, WorkoutSelectExerciseActivity::class.java)
                intent.putExtra("workout", workout)
                startActivity(intent)
            }
            else {
               Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}