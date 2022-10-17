package com.wildpress.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityWorkoutBinding
import com.wildpress.model.Workout

class WorkoutActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding : ActivityWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("workout", Workout::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra<Workout>("workout")
        }


        //Initialize toolbar
        if (workout != null) {
            Toolbar().showToolbar(this, workout.getTitle(), true)
        }

        //Listeners
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}