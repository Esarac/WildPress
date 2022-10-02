package com.wildpress.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wildpress.R
import com.wildpress.components.Toolbar

class DietActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet)

        Toolbar().showToolbar(this, "Diet", false)
    }
}