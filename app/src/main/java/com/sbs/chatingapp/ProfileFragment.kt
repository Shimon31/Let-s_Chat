package com.sbs.chatingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sbs.chatingapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    lateinit var userDb: DatabaseReference


    private var userId = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        userDb = FirebaseDatabase.getInstance().reference




        requireArguments().getString(USERID)?.let {
            userId = it
            getUserByID(it)
        }

        FirebaseAuth.getInstance().currentUser?.let {
            if (userId == it.uid) {
                binding.chatWithUserBTn.text = Edit
            } else {
                binding.chatWithUserBTn.text = CHAT
            }
        }

        return binding.root
    }

    companion object {
        const val USERID = "user_id_key"
        const val Edit = "Edit Profile"
        const val CHAT = "Let's Chat"
    }

    private fun getUserByID(userId: String) {


        userDb.child(DBNODES.USER).child(userId).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {


                    snapshot.getValue(User::class.java)?.let {

                        binding.userEmail.text = it.email
                        binding.bio.text = it.bio
                        binding.fullName.text = it.fullName
                        binding.profileImage.load(it.profileImage)


                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


    }


}