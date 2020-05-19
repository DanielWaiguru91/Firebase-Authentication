package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        txtSignUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }
        btn_Login.setOnClickListener {
            loginValidate()
        }
        auth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(currentUser: FirebaseUser?){
        if (currentUser != null){
            startActivity(Intent(this, Home::class.java))

        }
    }
    private fun loginValidate(){
        if (etLoginEmail.text.toString().isEmpty()){
            etLoginEmail.error = "Email is required"
            etLoginEmail.requestFocus()
            //cancel user sign up in case of error
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etLoginEmail.text.toString()).matches()){
            etLoginEmail.error = "Enter a valid email"
            etLoginEmail.requestFocus()
            return
        }
        if (etLoginPassword.text.toString().isEmpty()){
            etLoginPassword.error = "Password is required"
            etLoginPassword.requestFocus()
            return
        }

    }
}
