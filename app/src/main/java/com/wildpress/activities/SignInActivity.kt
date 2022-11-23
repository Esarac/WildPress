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
import com.wildpress.model.Exercise
import com.wildpress.model.User

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

    private fun register(view: View){
        Firebase.auth.createUserWithEmailAndPassword(
            binding.signInEmailTextEdit.text.toString(),
            binding.signInPasswordTextEdit.text.toString()
        ).addOnSuccessListener {
            val id = Firebase.auth.currentUser?.uid
            val userName = binding.signInUsernameTextEdit.text.toString()
            val firstName = binding.signInFirstNameTextEdit.text.toString()
            val lastName = binding.signInLastNameTextEdit.text.toString()
            val aboutMe = binding.signInAboutMeTextEdit.text.toString()
            val exercises = arrayListOf<Exercise>()
            val user = User(id!!,userName,firstName, lastName,aboutMe,exercises);
            Firebase.firestore.collection("users").document(id).set(user).addOnSuccessListener{
                //sendVerificationEmail()
                finish()

            }

        }.addOnFailureListener{
            Toast.makeText(this, it.message,Toast.LENGTH_LONG).show()
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