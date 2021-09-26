package com.example.firebaserealtimedatabaseexample

import android.os.Build.ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_database.*

class DatabaseActivity : AppCompatActivity() {
    private var db : FirebaseFirestore? = null
    private var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)
        db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        var userId = user?.uid
      //  getDataOneTime()
       addUserDataChangeListener()
    }

    private fun addUserDataChangeListener() {
        val user = FirebaseAuth.getInstance().currentUser
        var userID = user?.uid
        val docRef = db?.collection("users")?.document(userID!!)
        docRef?.addSnapshotListener { snapshot, error ->
            if (error!=null){
                Log.w(TAG,"Listen failed",error)
            }
            if (snapshot!=null && snapshot.exists()){
                val user = snapshot.toObject(User::class.java)
                editTextTextPersonName.setText(user?.ID)
                editTextTextEmailAddress.setText(user?.email)
            }

        }
    }

    private fun getDataOneTime() {
        // in one time update if you make the change in firestore database its might not be
        // reflected immediately and make take some time
        val user = FirebaseAuth.getInstance().currentUser
        var userID = user?.uid
        val docRef = db?.collection("users")?.document(userID!!)
        docRef?.get()?.addOnSuccessListener { snapshot ->
            val user = snapshot.toObject(User::class.java)
            val id = user?.ID.toString()
            val email = user?.email.toString()
            editTextTextPersonName.setText(id)
            editTextTextEmailAddress.setText(email)
        }
    }

    fun onUpdateClicked(view: View){
        val ID = editTextTextPersonName.text.toString()
        val email = editTextTextEmailAddress.text.toString()
        updateUser(ID,email)
    }

    private fun updateUser(id: String, email: String) {
        var currentuser = FirebaseAuth.getInstance().currentUser
        val userID = currentuser?.uid

        if (editTextTextEmailAddress.text.isNotEmpty() && editTextTextPersonName.text.isNotEmpty()){
            val myRef = db?.collection("users")?.document(userID!!)
            myRef!!.update("ID",id)
            myRef!!.update("email",email)
            Toast.makeText(this,"updated",Toast.LENGTH_SHORT).show()


        }
        else {
            Toast.makeText(this,"failed to update",Toast.LENGTH_SHORT).show()
        }


    }

    fun onDeleteClicked(view:View){
        val userID = FirebaseAuth.getInstance().currentUser?.uid
     db!!.collection("users").document(userID!!).delete().addOnSuccessListener {
         Toast.makeText(this,"deleted",Toast.LENGTH_SHORT).show()
     }.addOnFailureListener { Toast.makeText(this,"unable to delete",Toast.LENGTH_SHORT).show() }
        editTextTextEmailAddress.setText("")
        editTextTextPersonName.setText("")


    }
    companion object {
        private val TAG = DatabaseActivity::class.java.simpleName
    }
}