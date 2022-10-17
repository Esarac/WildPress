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
import com.wildpress.activities.CreateDietActivity
import com.wildpress.activities.WorkoutActivity
import com.wildpress.components.CardRecyclerView
import com.wildpress.databinding.FragmentDietBinding
import com.wildpress.model.Diet

class Diet : Fragment(R.layout.fragment_diet) {

    private var _binding : FragmentDietBinding? = null
    private val binding get() = _binding!!

    //Properties
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: CardRecyclerView<Diet>
    private var diets = ArrayList<Diet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.diets.add(Diet("Trucha a la plancha", "Diet n1", "Cocinarla"))
        this.diets.add(Diet("Batido de proteina", "Diet n2", "Batirlo"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDietBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.layoutManager = LinearLayoutManager(context)
        this.adapter = CardRecyclerView(this.diets)
        this.adapter.setOnItemClickListener(object : CardRecyclerView.onItemClickListener{
            override fun <T> onItemClick(item: T) {
                Toast.makeText(activity, "The item is: ${item.toString()}", Toast.LENGTH_SHORT).show()
            }
        })
        binding.dietRecyclerView.layoutManager = this.layoutManager
        binding.dietRecyclerView.adapter = this.adapter

        binding.createDietBtn.setOnClickListener {
            startActivity(Intent(activity, CreateDietActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}