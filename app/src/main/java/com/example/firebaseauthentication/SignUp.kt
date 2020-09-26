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
import kotlinx.android.synthetic.main.activity_sign_up.*

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

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }
    private fun userSignUpValidate(){
        if (etEmail.text.toString().isEmpty()){
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            //cancel user sign up in case of error
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()){
            etEmail.error = "Enter a valid email"
            etEmail.requestFocus()
            return
        }
        if (etPassword.text.toString().isEmpty()){
            etPassword.error = "Password is required"
            etPassword.requestFocus()
            return
        }
        val emailTxt = findViewById<EditText>(R.id.etEmail)
        val passwordTxt = findViewById<EditText>(R.id.etPassword)
        val email = emailTxt.text.toString()
        val password = passwordTxt.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    //val user = auth.currentUser

                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    //                    val  user = auth.currentUser
                    //                    updateUI(user)
                    Log.i("Main", "User created with id ${task.result?.user?.uid}")

                } else{
                    Toast.makeText(baseContext, "Authentication failed!", Toast.LENGTH_SHORT).show()

                }
            }
    }

}
