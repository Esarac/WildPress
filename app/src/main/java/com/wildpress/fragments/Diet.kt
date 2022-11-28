package com.wildpress.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.wildpress.R
import com.wildpress.activities.CreateDietActivity
import com.wildpress.components.CardRecyclerView
import com.wildpress.databinding.FragmentDietBinding
import com.wildpress.model.Diet
import com.wildpress.model.User
import com.wildpress.activities.DietViewActivity

class Diet : Fragment(R.layout.fragment_diet) {

    private var _binding : FragmentDietBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User

    //Properties
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: CardRecyclerView<Diet>
    private var diets = ArrayList<Diet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadDiets()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDietBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        loadDiets()
        this.layoutManager = LinearLayoutManager(context)
        this.adapter = CardRecyclerView(this.diets)
        this.adapter.setOnItemClickListener(object : CardRecyclerView.onItemClickListener{
            override fun <T> onItemClick(item: T) {
                val diet = item as Diet

                val intent = Intent(activity, DietViewActivity::class.java)
                intent.putExtra("diet", diet)
                startActivity(intent)
            }
        })
        binding.dietRecyclerView.layoutManager = this.layoutManager
        binding.dietRecyclerView.adapter = this.adapter
        this.adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createDietBtn.setOnClickListener {
            startActivity(Intent(activity, CreateDietActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadDiets(){
        val loggedUser = Firebase.auth.currentUser
        val userId = loggedUser!!.uid
        val user = loadUser()

        if(user==null || loggedUser==null){
            return
        } else{
            this.user = user
            diets = user.listOfDiet
            Firebase.firestore.collection("users").document(userId).get().addOnSuccessListener {
                val userOnDataBase = it.toObject(User::class.java)
                saveUserLocal(userOnDataBase!!)
            }
        }

    }
    private fun loadUser(): User?{
        val sp = this.requireActivity().getSharedPreferences("WildPress", Context.MODE_PRIVATE);
        val json = sp.getString("user", "NO_USER")
        if(json == "NO_USER"){
            return null
        }else{
            return Gson().fromJson(json, User::class.java)
        }
    }
    private fun saveUserLocal(user: User){
        if(activity!=null){
            val sp = this.requireActivity().getSharedPreferences("WildPress", Context.MODE_PRIVATE);
            val json = Gson().toJson(user)
            sp.edit().putString("user", json).apply()
        }
    }

}