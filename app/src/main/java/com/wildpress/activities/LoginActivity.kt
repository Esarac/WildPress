package com.wildpress.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.wildpress.databinding.ActivityLoginBinding
import com.wildpress.model.User

class LoginActivity : AppCompatActivity() {

    //Binding
    private  lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Listeners
        binding.logBtn.setOnClickListener {
            val userEmail = binding.logUsernameText.text.toString()
            val password = binding.PasswordText.text.toString()

            //startActivity(Intent(this, MainActivity::class.java))
            Firebase.auth.signInWithEmailAndPassword(userEmail, password).addOnSuccessListener {
                val loggedUser = Firebase.auth.currentUser
                Firebase.firestore.collection("users").document(loggedUser!!.uid).get().addOnSuccessListener {
                    val userOnDataBase = it.toObject(User::class.java)
                    saveUserLocal(userOnDataBase!!)
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
            }.addOnFailureListener{
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        binding.logGoogleBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.logSignBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        binding.logForgotBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun saveUserLocal(user: User){
        val sp = getSharedPreferences("WildPress", MODE_PRIVATE)
        val json = Gson().toJson(user)
        sp.edit().putString("user", json).apply()
    }

}