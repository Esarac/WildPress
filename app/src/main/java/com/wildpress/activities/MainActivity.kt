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

        Toolbar().showToolbar(this, "Workout", false)
        this.bottomBar = findViewById(R.id.bottomNavbar)
        replaceFragment(Workout())

        bottomBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.feedFragment -> replaceFragment(Feed())
                R.id.dietFragment -> replaceFragment(Diet())
                R.id.workoutFragment -> replaceFragment(Workout())
                R.id.scheduleFragment -> {
                    replaceFragment(Schedule())
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