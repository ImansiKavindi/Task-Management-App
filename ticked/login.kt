package com.example.ticked

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ticked.R

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        val btnNavigate: TextView = findViewById(R.id.btnsignin)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        val logbtnNavigate: Button = findViewById(R.id.btnlogin)
        logbtnNavigate.setOnClickListener {
            val intent = Intent(this, homepage::class.java)
            startActivity(intent)

        }
    }
}