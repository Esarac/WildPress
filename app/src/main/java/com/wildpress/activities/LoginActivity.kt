package com.wildpress.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wildpress.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    //Binding
    private  lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Listeners
        binding.logBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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

}