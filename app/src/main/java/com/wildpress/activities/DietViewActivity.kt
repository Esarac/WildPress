package com.wildpress.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityDietViewBinding
import com.wildpress.databinding.ActivityExerciseViewBinding
import com.wildpress.model.Diet
import com.wildpress.model.Exercise


class DietViewActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityDietViewBinding
    private lateinit var diet: Diet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDietViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val diet = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("diet", Diet::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra<Diet>("diet")
        }

        if (diet != null) {
            this.diet = diet;

            Glide.with(this).load(this.diet.getImage()).centerCrop().into(binding.dietImage)
            binding.dietNameValue.text = this.diet.name
            binding.dietDescriptionValue.text = this.diet.description
            binding.dietIngredientsValue.text = this.diet.ingredients

            //Toolbar
            Toolbar().showToolbar(this, this.diet.name, true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}