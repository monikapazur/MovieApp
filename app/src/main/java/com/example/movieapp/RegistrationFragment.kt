package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*

class RegistrationFragment : BaseFragment() {
    private val fbAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSignClick()

    }

    private fun setupSignClick() {
        signUpButton.setOnClickListener {
            val email = emailSignUp.text.trim().toString()
            val password = passwordSignUp.text.trim().toString()
            val passwordAgain = passwordAgainSignUp.text.trim().toString()
            if(email.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()){
                Toast.makeText(requireContext(),"fill all of the fields",Toast.LENGTH_SHORT).show()
            }
            else{
                if(password == passwordAgain){
                    fbAuth.createUserWithEmailAndPassword(email,password)
                        .addOnSuccessListener { auth->
                            if(auth.user != null) startApp()

                        }
                        .addOnFailureListener { exc->
                            Toast.makeText(requireContext(), "sth wrong with log", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("log error", exc.message.toString())
                        }
                }

            }
        }
    }
}