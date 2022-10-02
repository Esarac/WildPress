package com.wildpress.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.wildpress.R
import com.wildpress.components.Toolbar

class DietActivity : AppCompatActivity() {

    //Properties
    private lateinit var createDietBtn: Button

    //Constructor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet)

        //Binding
        this.createDietBtn = findViewById(R.id.createDietBtn)

        //Creates the Toolbar
        Toolbar().showToolbar(this, "Diet", false)

        //Listeners
        createDietBtn.setOnClickListener {
            startActivity(Intent(this, CreateDietActivity::class.java))
        }
    }
}