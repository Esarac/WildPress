package com.wildpress.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreateDietBinding
import com.wildpress.model.Diet
import com.wildpress.model.User

class CreateDietActivity : AppCompatActivity() {

    private var imageUri: Uri? = null

    //Binding
    private lateinit var binding : ActivityCreateDietBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceStat: Bundle?) {
        super.onCreate(savedInstanceStat)

        binding = ActivityCreateDietBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize toolbar
        Toolbar().showToolbar(this, "New Diet", true)

        //Listeners
        binding.dietCreSubmitBtn.setOnClickListener {
            uploadDiet()
            onSupportNavigateUp()
        }

        binding.dietCreImage.setOnClickListener{
            openGallery()
        }
    }

    private val startGalleryForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            imageUri = it.data?.data
            binding.dietCreImage.setImageURI(imageUri)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startGalleryForResult.launch(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun uploadDiet(){
        val user = loadUser()
        val image = imageUri.toString()
        val dietName = binding.dietCreNameEditText.text.toString()
        var dietDescription = binding.dietCreDescriptionEditTex.text.toString()
        var dietIngredients = binding.dietCreIngredientsEditText.text.toString()
        val diet = Diet(dietName,dietDescription, dietIngredients);
        val diets = user!!.listOfDiet
        diets.add(diet)
        val loggedUser = Firebase.auth.currentUser
        val userId = loggedUser!!.uid

        if (user==null || loggedUser == null){
            //val intent = Intent(this, M)
            finish()
            return
        } else{
            this.user = user
            Firebase.firestore.collection("users").document(userId).update("listOfDiet",diets).addOnSuccessListener {
                Toast.makeText(this, "Hola ${user.username}", Toast.LENGTH_LONG).show()
            }
            Firebase.firestore.collection("users").document(userId).get().addOnSuccessListener {
                val userOnDataBase = it.toObject(User::class.java)
                saveUserLocal(userOnDataBase!!)
                finish()
            }
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