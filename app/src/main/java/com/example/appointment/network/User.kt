package com.example.appointment.network

data class User(var name:String ="" , var mobile:String = "",var token:String = ""){

    val userToken:Int
    get() = token.toInt()
}

