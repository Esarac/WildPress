package com.wildpress.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.R
import com.wildpress.activities.CreateExerciseActivity
import com.wildpress.activities.ExerciseActivity
import com.wildpress.components.CardRecyclerView
import com.wildpress.databinding.FragmentWorkoutBinding
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

        this.workouts.add(Workout("Push up", "Exercise n1"))
        this.workouts.add(Workout("Incline up", "Exercise n2"))
        this.workouts.add(Workout("Decline up", "Exercise n3"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.layoutManager = LinearLayoutManager(context)
        this.adapter = CardRecyclerView(this.workouts)
        binding.exerciseRecyclerView.layoutManager = this.layoutManager
        binding.exerciseRecyclerView.adapter = this.adapter

        //Listeners
        binding.createButton.setOnClickListener {
            adapter.addItem(Workout(binding.nameEditText.text.toString(), binding.descriptionEditText.text.toString()))
        }
        binding.workoutCreateBtn.setOnClickListener {//Change
            startActivity(Intent(activity, CreateExerciseActivity::class.java))
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