package com.wildpress.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivitySigInBinding

class SigInActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding : ActivitySigInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigInBinding.inflate(layoutInflater)
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
        onBackPressed()
        return true
    }
}