package com.gtech.yojana.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.gtech.yojana.MainActivity
import com.gtech.yojana.R
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
        val mName: EditText = binding.nameInputLayout.editText!!
        val mNumber: EditText = binding.numberInputLayout.editText!!
        val mVerify: EditText = binding.verifyInputLayout.editText!!
        login = binding.login
        register = binding.register
        checkFormStatus()
        register.setOnClickListener {
            when {
                mEmail.text.isNotEmpty() && mPassword.text.isNotEmpty() && mName.text.isNotEmpty() && mNumber.text.isNotEmpty() -> {
                    if (mPassword.text.length < 6) {
                        Toast.makeText(
                            applicationContext,
                            "Password should be of minimum 6 characters",
                            Toast.LENGTH_LONG
                        ).show()
                        return@setOnClickListener
                    }
                    registerUser(
                        mEmail.text.toString(),
                        mPassword.text.toString(),
                        mNumber.text.toString(),
                        mName.text.toString(),
                        mVerify.text.toString()
                    )
                }
                mEmail.text.isEmpty() -> {
                    mEmail.error = "Enter Email"
                    mEmail.requestFocus()

                }
                mPassword.text.isEmpty() -> {

                    mPassword.error = "Enter Password"
                    mPassword.requestFocus()
                }
                mName.text.isEmpty() -> {

                    mName.error = "Enter Name"
                    mName.requestFocus()
                }
                mNumber.text.isEmpty() -> {

                    mNumber.error = "Enter Number"
                    mNumber.requestFocus()
                }
                mVerify.text.isEmpty() -> {
                    mVerify.error = "Enter Password Confirm"
                    mVerify.requestFocus()
                }
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

    private fun checkFormStatus() {
        if (binding.radioLogin.isChecked) {
            binding.nameInputLayout.visibility = View.GONE
            binding.numberInputLayout.visibility = View.GONE
            binding.register.visibility = View.GONE
            binding.verifyInputLayout.visibility = View.GONE

            binding.login.visibility = View.VISIBLE
        } else if (binding.radioRegister.isChecked) {
            binding.nameInputLayout.visibility = View.VISIBLE
            binding.numberInputLayout.visibility = View.VISIBLE
            binding.verifyInputLayout.visibility = View.VISIBLE
            binding.login.visibility = View.GONE
            binding.register.visibility = View.VISIBLE
        }
        binding.radiogroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_login -> {
                    binding.nameInputLayout.visibility = View.GONE
                    binding.numberInputLayout.visibility = View.GONE
                    binding.register.visibility = View.GONE
                    binding.login.visibility = View.VISIBLE
                }
                R.id.radio_register -> {
                    binding.nameInputLayout.visibility = View.VISIBLE
                    binding.numberInputLayout.visibility = View.VISIBLE
                    binding.verifyInputLayout.visibility = View.VISIBLE
                    binding.login.visibility = View.GONE
                    binding.register.visibility = View.VISIBLE
                }
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

    private fun registerUser(
        email: String,
        password: String,
        number: String,
        name: String,
        verify: String
    ) {
        if (password != verify) {
            Toast.makeText(this, "Password and Confirm Password are not same", Toast.LENGTH_SHORT)
                .show()
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val map = HashMap<String, Any>()
            map.put("name", name)
            map.put("number", number)
            map.put("email", email)
            map.put("password", password)
            FirebaseDatabase.getInstance().reference.child("users").child(it.user?.uid.toString())
                .setValue(map).addOnSuccessListener {
                    movetoMainScreen()
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT)
                        .show()

                }
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