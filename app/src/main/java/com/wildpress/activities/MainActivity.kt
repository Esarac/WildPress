package com.wildpress.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wildpress.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.wildpress.components.Toolbar
import com.wildpress.fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var bottomBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Properties
        this.bottomBar = findViewById(R.id.bottomNavbar)

        //Initialize Toolbar and Bottom Navbar
        Toolbar().showToolbar(this, "Workout", false)
        bottomBar.selectedItemId = R.id.workoutFragment
        replaceFragment(Workout())

        //Listeners
        bottomBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.feedFragment -> {
                    replaceFragment(Feed())
                    Toolbar().updateToolbar(this, "Feed")
                }
                R.id.dietFragment -> {
                    replaceFragment(Diet())
                    Toolbar().updateToolbar(this, "Diet")
                }
                R.id.workoutFragment -> {
                    replaceFragment(Workout())
                    Toolbar().updateToolbar(this, "Workout")
                }
                R.id.scheduleFragment -> {
                    replaceFragment(Schedule())
                    Toolbar().updateToolbar(this, "Schedule")
                }
                R.id.profileFragment -> {
                    replaceFragment(Profile())
                    Toolbar().updateToolbar(this, "Profile")
                }
                else ->{}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameContainer, fragment)
        fragmentTransaction.commit()
    }

    
}