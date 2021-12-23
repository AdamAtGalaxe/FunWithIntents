package com.galaxe.funwithintents

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import java.io.File
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply{
            add(R.id.myFragment, AnimationFrag1(), "blue")
            commit()
        }

    }

    fun call(v: View){
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + "8455878182")
        startActivity(dialIntent)
    }

    fun date(v: View){

        val calendarIntent = Intent(Intent.ACTION_EDIT).apply{
            val startTime = Calendar.getInstance(TimeZone.getTimeZone("America/New_York")).apply {
                set(2021, 11, 31, 23, 0,0)
            }
            val endTime = Calendar.getInstance(TimeZone.getTimeZone("America/New_York")).apply {
                set(2022, 0, 1, 1, 0,0)
            }

            type = "vnd.android.cursor.item/event"
            putExtra("beginTime", startTime.timeInMillis)
            putExtra("endTime", endTime.timeInMillis)
            putExtra("title", "New Years Eve Covid Super Spreader!")
            putExtra("eventLocation", "Adam's house")
        }
        startActivity(calendarIntent )
    }

    fun share(v: View){
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT,"Hey, I just wanted you to know Adam really is the best")
        }
        startActivity(Intent.createChooser(shareIntent,"Share To:"))
    }

    fun unknown(v: View){
        val file = getFileFromAssets(this, "mypdf.pdf")
        if(file.exists()) Toast.makeText(this, "File transferred fine...", Toast.LENGTH_LONG).show()
        try {
            val unknownIntent = Intent().apply {
                setDataAndType(Uri.fromFile(file), "application/pdf")
            }
            startActivity(unknownIntent)
        }
        catch(e: ActivityNotFoundException){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pdf&c=apps")))
        }
    }

    fun changeFrag(v: View){
        val myFragment = supportFragmentManager.findFragmentByTag("white")
        if (myFragment != null && myFragment.isVisible) {
            supportFragmentManager.beginTransaction().apply{
                setCustomAnimations(R.anim.card_flip_left_in, R.anim.card_flip_left_out)
                replace(R.id.myFragment, AnimationFrag1(), "blue")
                commit()
            }
        }
        else{
            supportFragmentManager.beginTransaction().apply{
                setCustomAnimations(R.anim.card_flip_left_in, R.anim.card_flip_left_out)
                replace(R.id.myFragment, AnimationFrag2(), "white")
                commit()
            }
        }
    }

    @Throws(IOException::class)
    fun getFileFromAssets(context: Context, fileName : String): File = File(context.cacheDir, fileName).also {
        if (!it.exists()) {
            it.outputStream().use { cache ->
                context.assets.open(fileName).use { inputStream ->
                    inputStream.copyTo(cache)
                }
            }
        }
    }
}