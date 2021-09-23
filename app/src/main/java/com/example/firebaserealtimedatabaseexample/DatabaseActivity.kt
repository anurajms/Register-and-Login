package com.example.firebaserealtimedatabaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class DatabaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)
    }

    fun onUpdateClicked(view: View){

    }
    fun onDeleteClicked(view:View){

    }
    companion object {
        private val TAG = DatabaseActivity::class.java.simpleName
    }
}