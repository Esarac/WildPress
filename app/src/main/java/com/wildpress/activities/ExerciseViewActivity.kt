package com.wildpress.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityExerciseViewBinding
import com.wildpress.model.Exercise


class ExerciseViewActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityExerciseViewBinding
    private lateinit var exercise: Exercise

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exercise = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("exercise", Exercise::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra<Exercise>("exercise")
        }

        if (exercise != null) {
            this.exercise = exercise;

            Glide.with(this).load(this.exercise.image).centerCrop().into(binding.exerciseImage)
            binding.exerciseNameText.text = this.exercise.name
            binding.exerciseDescriptionText.text = this.exercise.description
            binding.exerciseMuscleText.text = this.exercise.muscle.toString()
            binding.exerciseRepetitionsText.text = this.exercise.repetitions.toString()

            //Toolbar
            Toolbar().showToolbar(this, this.exercise.name, true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}