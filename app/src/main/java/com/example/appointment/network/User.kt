package com.example.appointment.network

import android.icu.text.DateFormat
import com.google.firebase.firestore.FieldValue
import java.sql.Timestamp

data class User(var name:String ="" , var mobile:String = "",var token:String = ""){

    val userToken:Int
    get() = token.toInt()
}


