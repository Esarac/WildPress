package com.wildpress.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreatePostBinding
import java.text.SimpleDateFormat
import java.util.*

class CreatePostActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding : ActivityCreatePostBinding
    private lateinit var urimage : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize toolbar
        Toolbar().showToolbar(this, "New Post", true)

        //Listeners
        //Submit button
        binding.postSubmitBtn.setOnClickListener {

            if(binding.postMainTextArea.text.toString() != ""){
                //Post action
                uploadImage()
            }
        }

        //Image button
        binding.addImageBtn.setOnClickListener {
            selectImage()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun selectImage(){
        val intent = Intent();
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){
            urimage = data?.data!!
            binding.textViewImage.setText("Image loaded")
        }
    }

    private fun uploadImage(){
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference  =FirebaseStorage.getInstance().getReference("postImages/$fileName")

        storageReference.putFile(urimage)
            .addOnSuccessListener {
                binding.textViewImage.setText("")
                Toast.makeText(this@CreatePostActivity, "Successfully uploaded", Toast.LENGTH_SHORT).show()
                if(progressDialog.isShowing) progressDialog.dismiss()
                onSupportNavigateUp()
            }.addOnFailureListener{
                if(progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@CreatePostActivity, "Failed to upload", Toast.LENGTH_SHORT).show()
            }
    }
}