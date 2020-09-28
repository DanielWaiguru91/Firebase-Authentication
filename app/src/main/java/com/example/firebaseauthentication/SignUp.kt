package com.example.firebaseauthentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initListeners()
    }
    private fun initListeners() {
        registerButton.setOnClickListener { signUp() }
    }
    private fun signUp(){
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val cPassword = cPasswordEditText.text.toString()
        if (email.isEmpty()){
            emailEditText.error = getString(R.string.email_error)
            emailEditText.requestFocus()
            return
        }
        if (password.isEmpty()){
            passwordEditText.error = getString(R.string.password_error)
            passwordEditText.requestFocus()
            return
        }
        if (cPassword.isEmpty()){
            cPasswordEditText.error = getString(R.string.not_match_error)
            cPasswordEditText.requestFocus()
            return
        } else if (cPassword != password){
            cPasswordEditText.error = getString(R.string.not_match_error)
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
                initUi()
            }
            .addOnFailureListener {
                Toast.makeText(this, getString(R.string.failure), Toast.LENGTH_SHORT).show()
            }
    }
    private fun initUi(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
