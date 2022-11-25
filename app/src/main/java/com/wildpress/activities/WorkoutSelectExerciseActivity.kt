package com.wildpress.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.wildpress.components.ExerciseRecyclerView
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityWorkoutSelectExerciseBinding
import com.wildpress.model.Exercise
import com.wildpress.model.User
import com.wildpress.model.Workout

class WorkoutSelectExerciseActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityWorkoutSelectExerciseBinding
    private lateinit var user: User
    private lateinit var workout : Workout;
    private lateinit var adapter: ExerciseRecyclerView

    //Properties
    private var exercises = ArrayList<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutSelectExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.workoutExercisesBtn.isEnabled = false

        val workout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("workout", Workout::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra<Workout>("workout")
        }

        //Initialize Screen
        if (workout != null) {
            this.workout = workout;
            //Recycler View
            binding.WorkoutExerciseRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
            adapter = ExerciseRecyclerView(this.exercises, binding.workoutExercisesBtn,  binding.workoutExercisesBtn)
            binding.WorkoutExerciseRecyclerView.adapter = adapter

            //Toolbar
            Toolbar().showToolbar(this, this.workout.getTitle(), true)
        }

        //Listeners
        binding.workoutExercisesBtn.setOnClickListener {
            val items = adapter.getSelectedItems()
            var stringExercises = ""
            for(i in items.indices) {
                workout?.addExercise(exercises[items[i]])
            }
            if (workout != null) {
                uploadWorkOut(workout)
            }

            for(i in workout?.exercises?.indices!!){
                stringExercises += workout.exercises[i].name + ", "
            }

            Toast.makeText(this, "ITEMS SIZE: " + items.size, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "EXERCISES SIZE: " + exercises.size, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, stringExercises, Toast.LENGTH_LONG).show()
        }
        //user.listOfWorkOut =
    }

    override fun onResume() {
        super.onResume()
        loadExercises()

        binding.WorkoutExerciseRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

//        Toast.makeText(this, this.exercises.toString(), Toast.LENGTH_SHORT).show()
        adapter = ExerciseRecyclerView(this.exercises, binding.exerciseSelectedExercises, binding.workoutExercisesBtn)
        adapter.notifyDataSetChanged()
        binding.WorkoutExerciseRecyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun uploadWorkOut(workOutToAdd: Workout){
        val user = loadUser()
        //val image = urimage.toString()
        val workouts = user!!.listOfWorkOut
        workouts.add(workOutToAdd)
        val loggedUser = Firebase.auth.currentUser
        val userId = loggedUser!!.uid

        if (user==null || loggedUser == null){
            //val intent = Intent(this, M)
            finish()
            return
        } else{
            this.user = user
            Firebase.firestore.collection("users").document(userId).update("listOfWorkOut", workouts).addOnSuccessListener {
                Firebase.firestore.collection("users").document(userId).get().addOnSuccessListener {
                    val userOnDataBase = it.toObject(User::class.java)
                    saveUserLocal(userOnDataBase!!)
                }.addOnCompleteListener{
                    finish()
                }
            }.addOnFailureListener {
//                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
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
            //Toast.makeText(this, exercises.toString(), Toast.LENGTH_SHORT).show()
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