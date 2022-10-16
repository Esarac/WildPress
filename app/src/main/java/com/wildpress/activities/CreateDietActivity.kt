package com.wildpress.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreateDietBinding

class CreateDietActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding : ActivityCreateDietBinding

    override fun onCreate(savedInstanceStat: Bundle?) {
        super.onCreate(savedInstanceStat)

        binding = ActivityCreateDietBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize toolbar
        Toolbar().showToolbar(this, "New Diet", true)

        //Listeners
        binding.dietCreSubmitBtn.setOnClickListener {
            onSupportNavigateUp()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}