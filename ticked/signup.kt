package com.example.ticked

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ticked.R

class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        val btnNavigate: TextView = findViewById(R.id.btnlog)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        val backnavigate: ImageView = findViewById(R.id.backbtn)
        backnavigate.setOnClickListener {
            finish()
        }

        val regbtnNavigate: Button = findViewById(R.id.regbtn)
        regbtnNavigate.setOnClickListener {
            val intent = Intent(this, homepage::class.java)
            startActivity(intent)
        }
    }
}