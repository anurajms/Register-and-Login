package com.example.firebaserealtimedatabaseexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private var mFirebaseDatabaseInstance: FirebaseFirestore?=null
    private var userID:String? = null
    private var emailAddress:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()



        tv_login.setOnClickListener {
            startActivity(Intent(this@Register,Login::class.java))
            finish()
        }
     btnregister.setOnClickListener {
         register()
     }
    }


    private fun register() {
        if(etemailregister.text.isEmpty()){
            etemailregister.error = "Enter Email"
        }
        else if (etpasswordregister.text.isEmpty()){
            etpasswordregister.error = "Enter password"
        }
        else {
            var email = etemailregister.text.toString()
            val password = etpasswordregister.text.toString()
            var auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Registration Successful",Toast.LENGTH_SHORT).show()


                    // get referance to users node
                   val mFirebaseDatabase = database.getReference("Users")
                    val user = auth.currentUser
                    val userID = user!!.uid
                    val email = user!!.email
                    val myUser = User(userID,email.toString())
                    mFirebaseDatabase!!.child(userID).setValue(myUser)
                    mFirebaseDatabaseInstance?.collection("users")?.document(userID!!)?.set(myUser)
                    startActivity(Intent(this@Register,SignedInActivity::class.java))
                }
                else{
                    Toast.makeText(this,"Registration Failure",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}