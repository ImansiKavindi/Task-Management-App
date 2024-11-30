package com.example.ticked

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ticked.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Timer
import kotlin.math.log10

class profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        /*val Navigatenew: LinearLayout = findViewById(R.id.mypro1)
        Navigatenew.setOnClickListener {
            val intent = Intent(this, myProfile::class.java)
            startActivity(intent)

        }

        val Navigate3: LinearLayout = findViewById(R.id.statbt)
        Navigate3.setOnClickListener {
            val intent = Intent(this, statistic::class.java)
            startActivity(intent)

        }

        val Navigate4: LinearLayout = findViewById(R.id.loc1)
        Navigate4.setOnClickListener {
            val intent = Intent(this, location::class.java)
            startActivity(intent)

        }

        val Navigate5: LinearLayout = findViewById(R.id.setbtn)
        Navigate5.setOnClickListener {
            val intent = Intent(this, settings::class.java)
            startActivity(intent)

        }

        val Navigatepro: LinearLayout = findViewById(R.id.logout3)
        Navigatepro.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)

        }*/

        // Bottom Navigation Setup
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.page_1
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {


                R.id.page_2 -> {
                    startActivity(Intent(this, homepage::class.java))
                    true
                }
                R.id.page_2 ->  {
                    startActivity(Intent(this, Calender::class.java))
                    true
                }
                R.id.page_3 -> {
                    startActivity(Intent(this, Tasklist::class.java))
                    true
                }
                R.id.page_4 ->  {
                    startActivity(Intent(this, Timer::class.java))
                    true
                }
                R.id.page_5 -> true
                else -> false
            }
        }


    }
}