package com.wildpress.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.R
import com.wildpress.activities.CreateExerciseActivity
import com.wildpress.activities.CreateWorkout
import com.wildpress.activities.ExerciseActivity
import com.wildpress.activities.WorkoutActivity
import com.wildpress.components.CardRecyclerView
import com.wildpress.databinding.FragmentWorkoutBinding
import com.wildpress.model.Exercise
import com.wildpress.model.Workout

class Workout : Fragment(R.layout.fragment_workout) {

    //Binding
    private var _binding : FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    //Properties
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: CardRecyclerView<Workout>
    private var workouts = ArrayList<Workout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workout1 = Workout("Texas", "Calisthenic workout!", 2, 10, 20)
        workout1.addExercise(Exercise("https://www.fitnesseducation.edu.au/wp-content/uploads/2020/10/Pushups.jpg","Pull up", "The best exercise ever!"))
        workout1.addExercise(Exercise("https://mikereinold.com/wp-content/uploads/rookie-mistakes-the-pullup-main.jpg","Push up", "The best exercise ever!"))
        workout1.addExercise(Exercise("https://www.fitnesseducation.edu.au/wp-content/uploads/2020/10/Pushups.jpg","Chin up", "The best exercise ever!"))
        workout1.addExercise(Exercise("https://mikereinold.com/wp-content/uploads/rookie-mistakes-the-pullup-main.jpg","Diamond push up", "The best exercise ever!"))
        workout1.addExercise(Exercise("https://www.fitnesseducation.edu.au/wp-content/uploads/2020/10/Pushups.jpg","Wide grip pull up", "The best exercise ever!"))
        workout1.addExercise(Exercise("https://mikereinold.com/wp-content/uploads/rookie-mistakes-the-pullup-main.jpg","Close grip pull up", "The best exercise ever!"))
        this.workouts.add(workout1)

        this.workouts.add(Workout("Incline up", "Exercise n2"))
        this.workouts.add(Workout("Decline up", "Exercise n3"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.layoutManager = LinearLayoutManager(context)
        this.adapter = CardRecyclerView(this.workouts)
        this.adapter.setOnItemClickListener(object : CardRecyclerView.onItemClickListener{
            override fun <T> onItemClick(item: T) {
                //Cast item to Workout
                val workout = item as Workout
                Toast.makeText(activity, "The item is: ${workout.name}", Toast.LENGTH_SHORT).show()

                //Pass the Workout to the next Activity using the context
                val intent = Intent(activity, WorkoutActivity::class.java)
                intent.putExtra("workout", workout)
                startActivity(intent)
            }
        })
        binding.workoutRecyclerView.layoutManager = this.layoutManager
        binding.workoutRecyclerView.adapter = this.adapter

        //Listeners
        binding.workoutCreateBtn.setOnClickListener {//Change
            startActivity(Intent(activity, CreateWorkout::class.java))
        }

        binding.workoutExercisesBtn.setOnClickListener {
            startActivity(Intent(activity, ExerciseActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}