package com.wildpress.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.wildpress.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.wildpress.components.Toolbar
import com.wildpress.fragments.*
import com.wildpress.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var bottomBar: BottomNavigationView

    private lateinit var user: User

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

        val user = getUser()
        if (user==null || Firebase.auth.currentUser == null){
            //val intent = Intent(this, M)
            finish()
            return
        } else{
            this.user = user
            Toast.makeText(this, "Hola ${user.username}", Toast.LENGTH_LONG).show()
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun getUser():User?{
        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
             intent.getParcelableExtra("user", User::class.java)
        }
        else {
            @Suppress("DEPRECATION") intent.getParcelableExtra<User>("user")
        }

        return user
    }

}