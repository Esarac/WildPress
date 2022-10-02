package com.wildpress.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wildpress.R
import com.wildpress.components.Toolbar

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Toolbar().showToolbar(this, "Profile", false)
    }
}