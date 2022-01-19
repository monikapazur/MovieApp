package com.example.movieapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavHostController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*


class LoginFragment : BaseFragment() {

    private val fbAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginClick()
        setupRegistrationClick()
    }

    private fun setupRegistrationClick() {
        signUpButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment().actionId)
        }
    }

    private fun setupLoginClick() {

        signInButton.setOnClickListener {
            val email = emailSignIn.text?.trim().toString()
            val password = passwordSignIn.text?.trim().toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                fbAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener { auth ->
                       if(auth.user != null) startApp()

                    }
                    .addOnFailureListener { exc ->
                        Toast.makeText(requireContext(), "sth wrong with log", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("log error", exc.message.toString())
                    }
            }
        }

    }


}