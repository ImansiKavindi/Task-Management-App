package com.example.ticked

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ticked.R

class onboardScreen3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboardscrenn3)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        val btnNavigate: Button = findViewById(R.id.btnNavigate3)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        val skpNavigate: TextView = findViewById(R.id.skpNavigate3)
        skpNavigate.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

    }
}