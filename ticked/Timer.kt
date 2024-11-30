package com.example.ticked

import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ticked.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Timer : AppCompatActivity() {

    private lateinit var timePicker: TimePicker
    private lateinit var startButton: Button
    private lateinit var stopButton: Button // Stop button for alarm
    private lateinit var timerTextView: TextView
    private var timer: CountDownTimer? = null
    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        timePicker = findViewById(R.id.timePicker)
        startButton = findViewById(R.id.startTimerButton)
        stopButton = findViewById(R.id.stopAlarmButton) // Initialize stop button
        timerTextView = findViewById(R.id.timerTextView)


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
                R.id.page_4 -> true
                R.id.page_5 -> {
                    startActivity(Intent(this, profile::class.java))
                    true
                }
                else -> false
            }
        }


        timePicker.setIs24HourView(true)

        startButton.setOnClickListener {
            val hours = timePicker.hour
            val minutes = timePicker.minute

            val totalTimeInMillis = (hours * 60 * 60 + minutes * 60) * 1000L

            startTimer(totalTimeInMillis)
        }

        // Handle stop button click to stop the alarm
        stopButton.setOnClickListener {
            stopAlarm()
        }
    }

    private fun startTimer(timeInMillis: Long) {
        timer?.cancel() // Cancel any existing timer
        ringtone?.stop() // Stop any existing ringtone if playing

        timer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                playAlarm()
                timerTextView.text = "00:00"
            }
        }.start()
    }

    private fun playAlarm() {
        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(applicationContext, alarmUri)
        ringtone?.play()
    }

    // Method to stop the alarm
    private fun stopAlarm() {
        ringtone?.stop() // Stop the ringtone
    }
}
