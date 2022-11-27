package com.wildpress.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.wildpress.components.ExerciseRecyclerView
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityExerciseBinding
import com.wildpress.model.Exercise
import com.wildpress.model.User

class ExerciseActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityExerciseBinding
    private lateinit var user: User

    //Properties
    private var exercises = ArrayList<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize toolbar
        Toolbar().showToolbar(this, "Exercise", true)

        //Listeners
        binding.exerciseCreateBtn.setOnClickListener {
            startActivity(Intent(this, CreateExerciseActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        loadExercises()

        binding.exerciseRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

        val exerciseAdapter = ExerciseRecyclerView(this.exercises)
        exerciseAdapter.notifyDataSetChanged()
        binding.exerciseRecyclerView.adapter = exerciseAdapter

        //Listener

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun loadExercises(){
        val loggedUser = Firebase.auth.currentUser
        val userId = loggedUser!!.uid
        val user = loadUser()

        if(user==null || loggedUser==null){
            finish()
            return
        } else{
            this.user = user
            exercises = user.listOfExercise
            Firebase.firestore.collection("users").document(userId).get().addOnSuccessListener {
                val userOnDataBase = it.toObject(User::class.java)
                saveUserLocal(userOnDataBase!!)
            }
            Toast.makeText(this, exercises.toString(), Toast.LENGTH_SHORT).show()
        }



    }
    private fun loadUser(): User?{
        val sp = getSharedPreferences("WildPress", MODE_PRIVATE)
        val json = sp.getString("user", "NO_USER")
        if(json == "NO_USER"){
            return null
        }else{
            return Gson().fromJson(json, User::class.java)
        }
    }
    private fun saveUserLocal(user: User){
        val sp = getSharedPreferences("WildPress", MODE_PRIVATE)
        val json = Gson().toJson(user)
        sp.edit().putString("user", json).apply()
    }


}