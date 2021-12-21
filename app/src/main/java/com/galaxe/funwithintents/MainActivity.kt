package com.galaxe.funwithintents

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import java.io.File
import java.io.IOException
import java.util.*



class MainActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        progressBar = findViewById(R.id.progressBar)
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
        val shareIntent= Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey, I just wanted you to know Adam really is the best")

        startActivity(Intent.createChooser(shareIntent,"Share To:"))
    }
    fun unknown(v: View){
        val file = getFileFromAssets("mypdf.pdf")
        if(file.exists()) Toast.makeText(this, "Yes", Toast.LENGTH_LONG).show()



//////////////////////////////////////////////////////


//        try {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.fromFile(file)).apply {
//                //(Intent.CATEGORY_OPENABLE)
//                type = "application/pdf"
//            }
//
//            startActivity(intent)
//        }
//        catch(e: ActivityNotFoundException){
//            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pdf&c=apps")))
//        }
        try {
            val intent = Intent()
            intent.setDataAndType(Uri.fromFile(file), "application/pdf")
            //intent.setAction(Intent.ACTION_VIEW)
            //val chooserIntent = Intent.createChooser(intent, "Open Report")
            startActivity(intent)
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
//                //(Intent.CATEGORY_OPENABLE)
//                type = "application/pdf"
//            }

            startActivity(intent)
        }
        catch(e: ActivityNotFoundException){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pdf&c=apps")))
        }



    }
    @Throws(IOException::class)
    fun getFileFromAssets(fileName: String): File = File(context.cacheDir, fileName)
        .also {
            if (!it.exists()) {
                it.outputStream().use { cache ->
                    context.assets.open(fileName).use { inputStream ->
                        inputStream.copyTo(cache)
                    }
                }
            }
        }
}