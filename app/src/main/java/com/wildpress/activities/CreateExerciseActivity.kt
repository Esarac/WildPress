package com.wildpress.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.wildpress.R
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreateDietBinding
import com.wildpress.databinding.ActivityCreateExerciseBinding
import com.wildpress.model.Exercise
import com.wildpress.model.Muscle

class CreateExerciseActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding : ActivityCreateExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize toolbar
        Toolbar().showToolbar(this, "New Exercise", true)

        val arrayAdapter = ArrayAdapter(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, Muscle.values())
        arrayAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
        binding.exerciseCreMuscleSpinner.adapter = arrayAdapter

        //Listeners
        binding.exerciseCreCancelBtn.setOnClickListener {
            onSupportNavigateUp()
        }
        binding.exerciseCreSubmitBtn.setOnClickListener {
            onSupportNavigateUp()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
