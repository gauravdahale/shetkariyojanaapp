package com.gtech.yojana.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.gtech.yojana.MainActivity
import com.gtech.yojana.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    private lateinit var register: Button
    private val TAG = "LoginActivity"
    private lateinit var login: Button
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mEmail: EditText = binding.emailInputLayout.editText!!
        val mPassword: EditText = binding.passwordInputLayout.editText!!
        login = binding.login
        register = binding.register
        register.setOnClickListener {
            if (mEmail.text.isNotEmpty() && mPassword.text.isNotEmpty()) {
            if(mPassword.text.length<6){
                Toast.makeText(applicationContext, "Password should be of minimum 6 characters",Toast.LENGTH_LONG).show()
           return@setOnClickListener }
                registerUser(mEmail.text.toString(), mPassword.text.toString())
            } else if (mEmail.text.isEmpty()) {
                mEmail.error = "Enter Email"
                mEmail.requestFocus()

            } else if (mPassword.text.isEmpty()) {

                mPassword.error = "Enter Password"
                mPassword.requestFocus()
            }
        }
        login.setOnClickListener {
            if (mEmail.text.isNotEmpty() && mPassword.text.isNotEmpty()) {
                loginUser(mEmail.text.toString(), mPassword.text.toString())
            } else if (mEmail.text.isEmpty()) {
                mEmail.error = "Enter Email"
                mEmail.requestFocus()
            } else if (mPassword.text.isEmpty()) {
                mPassword.error = "Enter Password"
                mPassword.requestFocus()
            }

        }
    }

    private fun loginUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(applicationContext, "User Registered", Toast.LENGTH_SHORT).show()

            movetoMainScreen()

        }.addOnFailureListener {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            movetoMainScreen()
            Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun movetoMainScreen() {
        val i = Intent(this, MainActivity::class.java)
        finish()
        startActivity(i)
    }

}