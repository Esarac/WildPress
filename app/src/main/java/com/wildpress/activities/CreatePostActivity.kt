package com.wildpress.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreatePostBinding
import java.io.File

class CreatePostActivity : AppCompatActivity() {

    private var imageUri: Uri? = null

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
            openGallery()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private val startGalleryForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            imageUri = it.data?.data
            binding.textViewImage.setText("Image loaded")
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startGalleryForResult.launch(intent)
    }
}