package com.wildpress.fragments

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.wildpress.activities.LoginActivity
import com.wildpress.databinding.FragmentProfileBinding
import android.content.SharedPreferences

class Profile : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileLogoutBtn.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
            if(activity != null){
                var sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("WildPress", MODE_PRIVATE)
                sharedPreferences.edit().remove("user").apply()
                sharedPreferences.edit().remove("rememberMe").apply()
            }

        }

        binding.editProfilePictureBtn.setOnClickListener {
            openGallery()
        }
    }

    private val startGalleryForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            imageUri = it.data?.data
            binding.profProfilePicture.setImageURI(imageUri)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startGalleryForResult.launch(intent)
    }

}