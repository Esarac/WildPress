package com.wildpress.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivitySignInBinding

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun register(view: View){
        Log.e("prueba", binding.signInEmailTextEdit.text.toString())
        Firebase.auth.createUserWithEmailAndPassword(
            binding.signInEmailTextEdit.text.toString(),
            binding.signInPasswordTextEdit.text.toString()
        ).addOnSuccessListener {
            finish();
        }
    }
}