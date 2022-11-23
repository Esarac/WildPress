package com.wildpress.activities

import android.content.Intent
import android.content.SharedPreferences
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

    //Shared Preferences
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("WildPress", MODE_PRIVATE)

        //Remember Me
        val rememberMe = sharedPreferences.getBoolean("rememberMe", false)
        if(rememberMe){
            val loggedUserAsString = sharedPreferences.getString("user",null)

            if(loggedUserAsString != null){
                var loggedUser = Gson().fromJson(loggedUserAsString, User::class.java)
                goMainPage(loggedUser)
            }
        }


        //Listeners
        binding.logBtn.setOnClickListener {
            val userEmail = binding.logUsernameText.text.toString()
            val password = binding.PasswordText.text.toString()
            
            val message = checkRequiredFields()
            if(message.isEmpty()) {
                Firebase.auth.signInWithEmailAndPassword(userEmail, password).addOnSuccessListener {
                    val loggedUser = Firebase.auth.currentUser
                    Firebase.firestore.collection("users").document(loggedUser!!.uid).get().addOnSuccessListener {
                        val userOnDataBase = it.toObject(User::class.java)
                        if(binding.logRememberCheckBox.isChecked){
                            saveRememberMeLocal()
                        }

                        goMainPage(userOnDataBase!!)
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

        binding.logSignBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        binding.logForgotBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun checkRequiredFields(): String {
        var message = ""
        val values = arrayOf(Pair("username", binding.logUsernameText.text.toString().trim()), Pair("password", binding.PasswordText.text.toString().trim()))

        for (i in values.indices) {
            if( values[i].second.isEmpty()) {
                message = "Please fill the " + values[i].first + " field"
                break
            }
        }
        return message
    }

    private fun saveRememberMeLocal(){
        sharedPreferences.edit().putBoolean("rememberMe", true).apply()
    }

    private fun saveUserLocal(user: User){
        val userJson = Gson().toJson(user)
        sharedPreferences.edit().putString("user", userJson).apply()
    }

    private fun goMainPage(user: User){
        saveUserLocal(user)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}