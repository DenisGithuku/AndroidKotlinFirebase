package com.denisgithuku.firebaseauthdemo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.denisgithuku.firebaseauthdemo.R
import com.denisgithuku.firebaseauthdemo.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        binding.helperText.setOnClickListener {
            this.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerButton.setOnClickListener {

            val emailInput = binding.inputEmail.text.toString()
            val passwordInput = binding.inputPassword.text.toString()

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "Email or password cannot be empty",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            Snackbar.make(
                                requireActivity().findViewById(android.R.id.content),
                                "Signed in as $emailInput",
                                Snackbar.LENGTH_SHORT
                            ).show()

                            val intent = Intent(requireActivity(), HomeActivity::class.java)
                                .apply {
                                    this.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    this.putExtra("user_id", firebaseUser.uid)
                                    this.putExtra("email_id", emailInput)
                                }
                            startActivity(intent)
                        } else {
                            Snackbar.make(
                                requireActivity().findViewById(android.R.id.content),
                                task.exception?.message.toString(),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
            }


        }
        return binding.root
    }
}