package com.wildpress.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.activities.ExerciseAdapter
import com.wildpress.databinding.FragmentWorkoutBinding
import com.wildpress.model.Exercise

class Workout : Fragment() {

    //Binding
    private var _binding : FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    //Properties
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: ExerciseAdapter
    private var exercises = ArrayList<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.exercises.add(Exercise("Push up", "Exercise n1"))
        this.exercises.add(Exercise("Incline up", "Exercise n2"))
        this.exercises.add(Exercise("Decline up", "Exercise n3"))
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
        this.adapter = ExerciseAdapter(this.exercises)
        binding.exerciseRecyclerView.layoutManager = this.layoutManager
        binding.exerciseRecyclerView.adapter = this.adapter

        //Listeners
        binding.createButton.setOnClickListener {
            adapter.addExercise(Exercise(binding.nameEditText.text.toString(), binding.descriptionEditText.text.toString()))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}