package com.example.firebaseauthentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauthentication.common.Constants.REQUEST_CODE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListeners()
    }
    private fun initListeners(){
        join.setOnClickListener { initRegister() }
        loginButton.setOnClickListener { signIn() }
        googleSignIn.setOnClickListener { googleSignIn() }
    }
    override fun onStart() {
        super.onStart()
        loggedInStatus()
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
                Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                initHome()
            }
            .addOnFailureListener {
                Toast.makeText(this, getString(R.string.logine_error), Toast.LENGTH_SHORT).show()
            }
    }
    private fun initHome(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
    private fun googleSignIn(){
        val token = BuildConfig.WEB_KEY
        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, REQUEST_CODE)
    }
    private fun loggedInStatus(){
        if (auth.currentUser != null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
    private fun initRegister(){
        val intent = Intent(this, SignUp::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE){
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let { getAccountCredentials(it) }
        }
    }
    //associates google account with firebase to actually sign in users
    private fun getAccountCredentials(account: GoogleSignInAccount){
        val authCredentials = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(authCredentials).await()
                //initHome()
                withContext(Dispatchers.Main){
                    Toast
                        .makeText(this@LoginActivity,
                            getString(R.string.success_login), Toast.LENGTH_LONG).show()
                }
            }
            catch (e: IOException){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
