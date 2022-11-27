package com.wildpress.activities

import android.app.Activity
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreateExerciseBinding
import com.wildpress.model.Muscle
import com.wildpress.model.User
import com.google.gson.Gson
import com.wildpress.model.Exercise
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CreateExerciseActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding : ActivityCreateExerciseBinding
    private lateinit var user: User
    private var uriImage : Uri? = null
    private var imgName: String = ""

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
            if(uriImage != null){
                uploadImage()
                uploadExercise()
            }else{
                Toast.makeText(this@CreateExerciseActivity, "Select an image", Toast.LENGTH_SHORT).show()
            }
        }

        binding.exerciseCreImage.setOnClickListener{
            selectImage()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun uploadExercise(){
        val user = loadUser()
        val exerciseName = binding.exerciseCreNameEditText.text.toString()
        var muscleToTrain = binding.exerciseCreMuscleSpinner.selectedItem.toString()
        var exerciseDescription = binding.exerciseCreDescriptionEditText.text.toString()
        val exercise = Exercise(imgName,exerciseName,Muscle.valueOf(muscleToTrain), exerciseDescription);
        val exercises = user!!.listOfExercise
        exercises.add(exercise)
        val loggedUser = Firebase.auth.currentUser
        val userId = loggedUser!!.uid

        if (user==null || loggedUser == null){
            //val intent = Intent(this, M)
            finish()
            return
        } else{
            this.user = user
            Firebase.firestore.collection("users").document(userId).update("listOfExercise", exercises).addOnSuccessListener {
                Firebase.firestore.collection("users").document(userId).get().addOnSuccessListener {
                    val userOnDataBase = it.toObject(User::class.java)
                    saveUserLocal(userOnDataBase!!)
                }.addOnCompleteListener{
                    finish()
                }
            }.addOnFailureListener {
//                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun loadUser(): User?{
        val sp = getSharedPreferences("WildPress", MODE_PRIVATE)
        val json = sp.getString("user", "NO_USER")
        if(json == "NO_USER"){
            return null
        }else{
            return Gson().fromJson(json, User::class.java)
        }
    }
    private fun saveUserLocal(user: User){
        val sp = getSharedPreferences("WildPress", MODE_PRIVATE)
        val json = Gson().toJson(user)
        sp.edit().putString("user", json).apply()
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
            uriImage = data?.data!!
            binding.exerciseCreImage.setImageURI(uriImage)
        }
    }

    private fun uploadImage(){
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        imgName = formatter.format(now)
        val fileName = imgName
        val storageReference  = FirebaseStorage.getInstance().getReference("exerciseImages/$fileName")

        if(uriImage != null){
            storageReference.putFile(uriImage!!)
                .addOnSuccessListener {
                    binding.exerciseCreImage.setImageURI(null)
//                    Toast.makeText(this@CreateExerciseActivity, "Successfully uploaded", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
//                    Toast.makeText(this@CreateExerciseActivity, "Failed to upload", Toast.LENGTH_SHORT).show()
                }
        }

    }
}
