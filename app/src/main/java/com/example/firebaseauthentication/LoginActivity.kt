package com.example.firebaseauthentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauthentication.auth.auth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListeners()
    }
    private fun initListeners(){
        join.setOnClickListener { initRegister() }
        loginButton.setOnClickListener { signIn() }
    }
    override fun onStart() {
        super.onStart()
    }
    private fun signIn() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
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
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                Toast.makeText(this, getString(R.string.logine_error), Toast.LENGTH_SHORT).show()
            }
    }
    private fun initRegister(){
        val intent = Intent(this, SignUp::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
