package com.sbs.chatingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.sbs.chatingapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)


        binding.createAccountTV.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginBtn.setOnClickListener {

            val email = binding.emailET.text.toString().trim()
            val password = binding.passET.text.toString().trim()

            if (isValidEmail(email) && isValidPassword(password)){
                loginUser(email, password)

            }else{
                Toast.makeText(requireContext(), "Invalid Email and Password", Toast.LENGTH_SHORT).show()
            }


        }

        return binding.root
    }

    private fun loginUser(email: String, password: String) {

        val auth = FirebaseAuth.getInstance()
         auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->

             if (task.isSuccessful){
                 val user = auth.currentUser
                 Toast.makeText(requireContext(), "Login Successful:  ${user?.email}", Toast.LENGTH_SHORT).show()
                 findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
             }else{
                 Toast.makeText(requireContext(), "Login Failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
             }

         }

    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {

        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&+=]).{8,}$"
        return password.matches(passwordRegex.toRegex())
    }

}