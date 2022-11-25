package com.wildpress.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wildpress.components.ExerciseRecyclerView
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityWorkoutBinding
import com.wildpress.model.Muscle
import com.wildpress.model.Workout
import java.util.*
import java.util.concurrent.TimeUnit

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

            //Stats
            binding.workoutRoundsValue.text = workout.rounds.toString()

            binding.workoutCaloriesValue.text = workout.burnedCalories().toString()

            binding.workoutRoundMusclesValue.text = muscleFormat(workout.trainedMuscles())

            binding.workoutTimeValue.text = timeFormat(workout.totalTime())

            binding.workoutExerciseRestValue.text = timeFormat(workout.exerciseRest)
            binding.workoutRoundRestValue.text = timeFormat(workout.roundRest)

            //Recycler View
            binding.workoutExercisesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
            binding.workoutExercisesRecyclerView.adapter = ExerciseRecyclerView(this.workout.exercises)

            //Toolbar
            Toolbar().showToolbar(this, this.workout.getTitle(), true)
        }

        //Listeners
        binding.workoutStartBtn.setOnClickListener {
            val intent = Intent(this, WorkoutStartActivity::class.java)
            intent.putExtra("workout", workout)

            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    //Aux fun
    private fun timeFormat(durationSeconds: Int): String{
        val millis = (durationSeconds*1000).toLong()
        val hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

        return when {
            hours == 0L && minutes == 0L -> String.format(
                (Formatter().format("%02d", seconds).toString()) + "s"
            )

            hours == 0L && minutes > 0L -> String.format(
                (Formatter().format("%02d:%02d", minutes, seconds).toString()) + "m"
            )

            else -> (Formatter().format("%02d:%02d:%02d", hours, minutes, seconds).toString()) + "h"
        }
    }

    private fun muscleFormat(muscles: List<Pair<Muscle, Int>>): String{
        if(muscles.isEmpty())
            return ""
        if(muscles.size == 1)
            return muscles[0].first.toString()
        if(muscles.size == 2)
            return muscles[0].first.toString() +" & "+muscles[1].first.toString()
        else
            return muscles[0].first.toString() +", "+muscles[1].first.toString()+"..."
    }
}