package com.wildpress.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import com.wildpress.R
import com.wildpress.model.Exercise

class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: ExerciseAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var createButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText

    private var exercises = ArrayList<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.exercises.add(Exercise("Push up", "Exercise n1"))
        this.exercises.add(Exercise("Incline up", "Exercise n2"))
        this.exercises.add(Exercise("Decline up", "Exercise n3"))

        this.layoutManager =
            LinearLayoutManager(this)
        this.adapter = ExerciseAdapter(this.exercises)
        this.recyclerView = findViewById(R.id.exerciseRecyclerView)
        this.recyclerView.layoutManager = this.layoutManager
        this.recyclerView.adapter = this.adapter

        this.createButton = findViewById(R.id.createButton)
        this.nameEditText = findViewById(R.id.nameEditText)
        this.descriptionEditText = findViewById(R.id.descriptionEditText)

        createButton.setOnClickListener{
            val exercise = Exercise(nameEditText.text.toString(), descriptionEditText.text.toString())
            adapter.addExercise(exercise)
        }

    }
}