package com.example.firebaserealtimedatabaseexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        btn_register_register.setOnClickListener {
            startActivity(Intent(this@Login,Register::class.java))
            finish()
        }
        btn_login.setOnClickListener {
            login()
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser !=null){
            startActivity(Intent(this,SignedInActivity::class.java))
            finish()
        }
    }

    private fun login() {
        if (et_email_login.text.isEmpty()){
            et_email_login.error = "Enter email"
        }
        else if(et_password_login.text.isEmpty()){
            et_password_login.error = "Enter password"
        }
        else {
            var email = et_email_login.text.toString()
            var password = et_password_login.text.toString()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                task -> if (task.isSuccessful){
                    startActivity(Intent(this,SignedInActivity::class.java))
                finish()
                }
                else{
                    Toast.makeText(this,"error loggin in",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}