package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.emailEditText
import kotlinx.android.synthetic.main.activity_sign_up.passwordEditText

class SignUp : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        txtSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        btn_SignUp.setOnClickListener {
            userSignUpValidate()
        }
    }
    private fun signUp(){
        
        if (emailEditText.text.toString().isEmpty()){
            emailEditText.error = getString(R.string.email_error)
            emailEditText.requestFocus()
            return
        }
        if (passwordEditText.text.toString().isEmpty()){
            passwordEditText.error = getString(R.string.password_error)
            passwordEditText.requestFocus()
            return
        }
        if (cPasswordEditText.text.toString().isEmpty()){
            cPasswordEditText.error = getString(R.string.not_match_error)
            cPasswordEditText.requestFocus()
            return
        } else if (cPasswordEditText.text.toString() != passwordEditText.text.toString()){
            cPasswordEditText.error = getString(R.string.not_match_error)
        }

    }

}
