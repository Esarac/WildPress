package com.wildpress.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding : ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize toolbar
        Toolbar().showToolbar(this, "New Post", true)

        //Listeners
        //Submit button
        binding.postSubmitBtn.setOnClickListener {
            onSupportNavigateUp()
            if(binding.postMainTextArea.text.toString() == ""){
                //Post action
            }
        }

        //Image button
        binding.addImageBtn.setOnClickListener {
            onSupportNavigateUp()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}