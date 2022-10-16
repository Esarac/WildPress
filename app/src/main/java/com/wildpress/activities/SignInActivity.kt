package com.wildpress.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        Toolbar().showToolbar(this, "Sig In", true)

        //Listeners
        binding.sigInSignInBtn.setOnClickListener {
            onSupportNavigateUp()
        }
        binding.sigInCancelBtn.setOnClickListener {
            onSupportNavigateUp()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}