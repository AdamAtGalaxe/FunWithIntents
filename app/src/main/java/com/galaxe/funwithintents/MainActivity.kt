package com.galaxe.funwithintents

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.File
import java.io.IOException
import java.util.*



class MainActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var myFrag : Fragment
    lateinit var front : FrameLayout
    lateinit var back : FrameLayout
    lateinit var front_anim : AnimatorSet
    lateinit var back_anim : AnimatorSet
    var isFront = true

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        front = findViewById(R.id.frontFragment)
        back = findViewById(R.id.backFragment)
        var scale = applicationContext.resources.displayMetrics.density
        supportFragmentManager.beginTransaction().apply{
            //setCustomAnimations(R.anim.card_flip_left_in, R.anim.card_flip_left_out)
            replace(R.id.frontFragment, AnimationFrag1(), "blue")
            replace(R.id.backFragment, AnimationFrag2(), "white")
            addToBackStack(null)
            commit()
        }

        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale

        front_anim = AnimatorInflater.loadAnimator(applicationContext, R.anim.card_flip_left_in) as AnimatorSet
        back_anim = AnimatorInflater.loadAnimator(applicationContext, R.anim.card_flip_left_out) as AnimatorSet

        //progressBar = findViewById(R.id.progressBar)
//        supportFragmentManager.beginTransaction().apply{
//            setCustomAnimations(R.anim.card_flip_left_in, R.anim.card_flip_left_out)
//            replace(R.id.flFragment, blueFrag, "blue")
//            addToBackStack(null)
//            commit()
//        }




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
    fun changeFrag(v: View){

        if(isFront)
        {
            front_anim.setTarget(front);
            back_anim.setTarget(back);
            front_anim.start()
            back_anim.start()
            isFront = false

        }
        else
        {
            front_anim.setTarget(back)
            back_anim.setTarget(front)
            back_anim.start()
            front_anim.start()
            isFront =true

        }

//            var myFragment = supportFragmentManager.findFragmentByTag("blue")
//        if (myFragment != null && myFragment.isVisible()) {
//            supportFragmentManager.beginTransaction().apply{
//                setCustomAnimations(R.anim.card_flip_left_in, R.anim.card_flip_left_out)
//                replace(R.id.flFragment, whiteFrag, "white")
//                addToBackStack(null)
//                commit()
//            }
//
//        }
//        else{
//            supportFragmentManager.beginTransaction().apply{
//                setCustomAnimations(R.anim.card_flip_left_in, R.anim.card_flip_left_out)
//                replace(R.id.flFragment, blueFrag, "blue")
//                addToBackStack(null)
//                commit()
//            }
//        }
    }
}