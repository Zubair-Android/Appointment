package com.example.appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SpleshActivity : AppCompatActivity() {
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splesh)


        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SpleshActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)

    }
}
