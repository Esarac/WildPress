package com.wildpress.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreateExerciseBinding
import com.wildpress.model.Muscle
import com.wildpress.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.gson.Gson
import com.wildpress.databinding.ActivityLoginBinding
import com.wildpress.model.Exercise


class CreateExerciseActivity : AppCompatActivity() {
    private var imageUri: Uri? = null

    //Binding
    private lateinit var binding : ActivityCreateExerciseBinding
    private lateinit var user: User

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
        binding.exerciseCreSubmitBtn.setOnClickListener {
            uploadExercise()
            onSupportNavigateUp()
        }

        binding.exerciseCreImage.setOnClickListener{
            openGallery()
        }
    }

    private val startGalleryForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            imageUri = it.data?.data
            binding.exerciseCreImage.setImageURI(imageUri)
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
    private fun uploadExercise(){
        val image = binding.exerciseCreImage.toString()
        val exerciseName = binding.exerciseCreNameEditText.text.toString()
        var muscleToTrain = binding.exerciseCreMuscleSpinner.toString()
        var exerciseDescription = binding.exerciseCreDescriptionEditText.text.toString()
        val exercise = Exercise(image,exerciseName,muscleToTrain, exerciseDescription);
        val exercises = arrayListOf<Exercise>()
        exercises.add(exercise)
        val loggedUser = Firebase.auth.currentUser
        val userId = loggedUser!!.uid
        val user = loadUser()

        if (user==null || loggedUser == null){
            //val intent = Intent(this, M)
            finish()
            return
        } else{
            this.user = user
            //user.list.add()
            Firebase.firestore.collection("users").document(userId).update("exercises",exercises)
            Toast.makeText(this, "Hola ${user.username}", Toast.LENGTH_LONG).show()
        }
    }
    private fun loadUser():User?{
        val sp = getSharedPreferences("WildPress", MODE_PRIVATE)
        val json = sp.getString("user", "NO_USER")
        if(json == "NO_USER"){
            return null
        }else{
            return Gson().fromJson(json, User::class.java)
        }
    }
}
