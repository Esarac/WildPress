package com.wildpress.activities

import android.app.Activity
import android.app.ProgressDialog
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
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.wildpress.components.Toolbar
import com.wildpress.databinding.ActivityCreateDietBinding
import com.wildpress.model.Diet
import com.wildpress.model.User
import java.text.SimpleDateFormat
import java.util.*

class CreateDietActivity : AppCompatActivity() {


    //Binding
    private lateinit var binding : ActivityCreateDietBinding
    private lateinit var user: User
    private lateinit var urimage : Uri

    override fun onCreate(savedInstanceStat: Bundle?) {
        super.onCreate(savedInstanceStat)

        binding = ActivityCreateDietBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize toolbar
        Toolbar().showToolbar(this, "New Diet", true)

        //Listeners
        binding.dietCreSubmitBtn.setOnClickListener {
            uploadImage()
            uploadDiet()
        }

        binding.dietCreImage.setOnClickListener{
            selectImage()
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun uploadDiet(){
        val user = loadUser()
        val image = urimage.toString()
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
                Firebase.firestore.collection("users").document(userId).get().addOnSuccessListener {
                    val userOnDataBase = it.toObject(User::class.java)
                    saveUserLocal(userOnDataBase!!)
                    finish()
                }
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

    private fun selectImage(){
        val intent = Intent();
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){
            urimage = data?.data!!
            binding.dietCreImage.setImageURI(urimage)
        }
    }

    private fun uploadImage(){

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference  = FirebaseStorage.getInstance().getReference("dietImages/$fileName")

        storageReference.putFile(urimage)
            .addOnSuccessListener {
                binding.dietCreImage.setImageURI(null)
                Toast.makeText(this@CreateDietActivity, "Successfully uploaded", Toast.LENGTH_SHORT).show()
                onSupportNavigateUp()
            }.addOnFailureListener{
                Toast.makeText(this@CreateDietActivity, "Failed to upload", Toast.LENGTH_SHORT).show()
            }
    }
}