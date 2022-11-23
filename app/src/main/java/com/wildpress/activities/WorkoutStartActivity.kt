package com.wildpress.activities

import android.os.Build
import android.os.Bundle
import java.util.Formatter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityWorkoutStartBinding
import com.wildpress.model.Exercise
import com.wildpress.model.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorkoutStartActivity: AppCompatActivity() {
    //Constant
    private val MOTIVATIONAL_QUOTES: Array<String> = arrayOf(
        "Let's get wild",
        "No pain, no gain",
        "Yeah buddy!",
        "Zyzz is watching us",
        "Embrace masculinity",
        "You mirin brah?",
        "King",
        "You got this",
        "Lightweight baby!",
    )

    //Binding
    private lateinit var binding : ActivityWorkoutStartBinding

    //Properties
    private lateinit var workout: Workout;

    // Aux
    private var index: Int = -1;
    private var restTimer: Int = -1;
    private var globalTimer: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Timer
        startTimers()

        val workout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("workout", Workout::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra<Workout>("workout")
        }

        //Initialize Screen
        if (workout != null) {
            this.workout = workout;
            nextExercise();

            //Toolbar
            Toolbar().showToolbar(this, this.workout.getTitle(), true)
        }

        //OnClick
        binding.workoutStartNextBtn.setOnClickListener {
            nextClick()
        }

        binding.workoutStartPrevBtn.setOnClickListener {
            prevExercise()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    // Timer fun
    private fun startTimers(){
        //Global
        lifecycleScope.launch(Dispatchers.IO){
            while(true){
                withContext(Dispatchers.Main){
                    delay(1000)
                    updateGlobalTimer()
                }
            }
        }

        //Rest
        lifecycleScope.launch(Dispatchers.IO){
            while(true){
                if(restTimer >= 0){
                    withContext(Dispatchers.Main){
                        delay(1000)
                        updateRestTimer()
                    }
                }
            }
        }
    }

    private fun updateGlobalTimer(){
        this.globalTimer += 1

        binding.workoutStartGlobalTimerText.text = timeFormat(this.globalTimer)
    }

    private fun updateRestTimer(){
        if(this.restTimer > 0){
            this.restTimer -= 1

            binding.workoutStartRestTimerText.text = timeFormat(this.restTimer)
        }
        else if(this.restTimer == 0){
            this.restTimer = -1

            nextExercise()
        }
    }

    //Navigation fun
    private fun nextClick(){
        val isResting: Boolean = this.restTimer >= 0;

        if(isResting){
            this.restTimer = -1;
            nextExercise();
        }
        else
            nextRest();
    }

    private fun nextRest(){
        val exerciseIndex: Int = index % this.workout.exercises.size;

        val isLastExerciseOfRound: Boolean = exerciseIndex == this.workout.exercisesSize()-1;
        if(isLastExerciseOfRound)
            this.restTimer = this.workout.roundRest;
        else
            this.restTimer = this.workout.exerciseRest;

        binding.workoutStartExerciseNameText.text = "Rest"
        binding.workoutStartRestTimerText.text = timeFormat(this.restTimer)
    }

    private fun nextExercise(){
        val isLastExercise: Boolean = index == this.workout.totalExercisesSize()-1;

        if(!isLastExercise){
            this.index += 1
            updateExercise()
        }
        else{
            onSupportNavigateUp()
        }
    }

    private fun prevExercise(){
        val isFirstExercise: Boolean = index == 0;
        val isResting: Boolean = this.restTimer >= 0;

        if(!isFirstExercise){
            this.restTimer = -1
            this.index -= 1
            updateExercise()
        }
        else if(isResting){
            this.restTimer = -1
            updateExercise()
        }
    }

    private fun updateExercise(){
        val exerciseIndex: Int = index % this.workout.exercises.size;
        val actualExercise: Exercise = this.workout.exercises[exerciseIndex];

        binding.workoutStartExerciseNameText.text = actualExercise.name

        val quoteIndexLast = (MOTIVATIONAL_QUOTES.size-1)
        binding.workoutStartRestTimerText.text = MOTIVATIONAL_QUOTES[(0..quoteIndexLast).random()]
    }

    //Aux fun
    private fun timeFormat(durationSeconds: Int): String{
        var hours = 0
        var minutes = 0
        var seconds = durationSeconds
        if (seconds >= 3600) {
            hours = seconds / 3600
            seconds -= hours * 3600
        }
        if (seconds >= 60) {
            minutes = seconds / 60
            seconds -= minutes * 60
        }
        return Formatter().format("%1\$02d:%2\$02d:%3\$02d", hours, minutes, seconds).toString()
    }
}