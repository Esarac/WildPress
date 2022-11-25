package com.wildpress.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivitySignInBinding
import com.wildpress.model.Diet
import com.wildpress.model.Exercise
import com.wildpress.model.User
import com.wildpress.model.Workout

class SignInActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding : ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Initialize toolbar
        Toolbar().showToolbar(this, "Sign In", true)
        //Listeners
        binding.sigInSignInBtn.setOnClickListener(::register)
    }

    private fun checkRequiredFields(): String {
        var message = ""
        val values = arrayOf(Pair("email", binding.signInEmailTextEdit.text.toString().trim()), Pair("username", binding.signInUsernameTextEdit.text.toString().trim()),
            Pair("password", binding.signInPasswordTextEdit.text.toString().trim()), Pair("confirm password",  binding.signInConfirmPasswordTextEdit.text.toString().trim()))

        for (i in values.indices) {
            if( values[i].second.isEmpty()) {
                message = "Please fill the " + values[i].first + " field"
                break
            }
        }

        if(message.isEmpty() && binding.signInPasswordTextEdit.text.toString().trim() != binding.signInConfirmPasswordTextEdit.text.toString().trim()) {
            message = "Password does not match"
        }

        return message
    }

    private fun register(view: View){
        val message = checkRequiredFields()
        if(message.isEmpty()){
            Firebase.auth.createUserWithEmailAndPassword(
                binding.signInEmailTextEdit.text.toString(),
                binding.signInPasswordTextEdit.text.toString()
            ).addOnSuccessListener {
                val id = Firebase.auth.currentUser?.uid
                val userName = binding.signInUsernameTextEdit.text.toString()
                val firstName = binding.signInFirstNameTextEdit.text.toString()
                val lastName = binding.signInLastNameTextEdit.text.toString()
                val aboutMe = binding.signInAboutMeTextEdit.text.toString()
                val listOfExercise = arrayListOf<Exercise>()
                val listOfDiet = arrayListOf<Diet>()
                val workOut = Workout("","",0,0,0, ArrayList())
                val user = User(id!!,userName,firstName, lastName,aboutMe,listOfExercise,listOfDiet,workOut);
                Firebase.firestore.collection("users").document(id).set(user).addOnSuccessListener{
                    //sendVerificationEmail()
                    finish()
                }
            }.addOnFailureListener{
                Toast.makeText(this, it.message,Toast.LENGTH_LONG).show()
            }
        }
        else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
    private fun sendVerificationEmail() {
        Firebase.auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
            Toast.makeText(this, "Verfique su email", Toast.LENGTH_LONG).show()
        }?.addOnFailureListener {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}