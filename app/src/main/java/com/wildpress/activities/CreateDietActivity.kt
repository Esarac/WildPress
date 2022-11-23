package com.wildpress.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreateDietBinding

class CreateDietActivity : AppCompatActivity() {

    private var imageUri: Uri? = null

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

        binding.dietCreImage.setOnClickListener{
            openGallery()
        }
    }

    private val startGalleryForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            imageUri = it.data?.data
            binding.dietCreImage.setImageURI(imageUri)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startGalleryForResult.launch(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}