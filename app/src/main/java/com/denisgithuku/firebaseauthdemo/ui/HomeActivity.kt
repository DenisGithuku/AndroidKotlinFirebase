package com.denisgithuku.firebaseauthdemo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denisgithuku.firebaseauthdemo.MainActivity
import com.denisgithuku.firebaseauthdemo.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        val user_id = intent.getStringExtra("user_id")
        val user_email = intent.getStringExtra("email_id")

        binding.userId.text = "User id :: $user_id"
        binding.userEmail.text = "Email id :: $user_email"

        binding.logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(binding.root)
    }
}