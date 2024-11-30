package com.example.ticked

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

private val handler = Handler(Looper.getMainLooper())

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasklist)

        handler.postDelayed({
            startActivity(Intent(this, onboardScreen1::class.java))
            finish()
        }, 2500L)
    }
}