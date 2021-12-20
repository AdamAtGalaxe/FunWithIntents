package com.galaxe.funwithintents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Month
import java.util.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun call(v: View){
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + "8455878182")
        startActivity(dialIntent)
    }
    fun date(v: View){

        val startDate = LocalDateTime.of(2021, Month.DECEMBER, 31, 0, 0)
        val endDate = LocalDateTime.of(2022, Month.JANUARY, 1, 1, 0)
        val c: Calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"))
        c.set(2021, 11, 31, 23, 0,0)
        val c2: Calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"))
        c2.set(2022, 0, 1, 1, 0,0)
        //val calendarEvent: Calendar = Calendar.Builder().setDate().setTimeOfDay(,0).build()

        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra("beginTime", c.timeInMillis)
        intent.putExtra("allDay", false)
        intent.putExtra("eventLocation", "Adam's house")
        intent.putExtra("rule", "FREQ=ONCE")
        intent.putExtra("endTime", c2.timeInMillis)
        intent.putExtra("title", "New Years Eve Covid Super Spreader!")
        startActivity(intent)


    }
    fun share(v: View){

    }
    fun unknown(v: View){

    }
}