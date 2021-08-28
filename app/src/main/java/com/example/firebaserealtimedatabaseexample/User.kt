package com.example.firebaserealtimedatabaseexample

class User {
    lateinit var name:String
    lateinit var email:String
    // default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    constructor(){}
    constructor(name: String,email:String){
        this.name = name
        this.email = email
    }
}