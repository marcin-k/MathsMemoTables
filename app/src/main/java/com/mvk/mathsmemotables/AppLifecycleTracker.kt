package com.mvk.mathsmemotables

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import kotlin.concurrent.thread
import kotlin.coroutines.experimental.coroutineContext

/**
 * Created by marcin on 22/03/2018.
 *
 * Used to track when the application goes to background
 * stops the music and resumes when application comes back
 * to foreground
 */

class AppLifecycleTracker : Application.ActivityLifecycleCallbacks  {
    override fun onActivityPaused(p0: Activity?) {
        Log.d("TAG1", "paused")
    }

    override fun onActivityResumed(p0: Activity?) {
        Log.d("TAG1", "resumed")
    }

    override fun onActivityDestroyed(p0: Activity?) {
        Log.d("TAG1", "destroyed")
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
        Log.d("TAG1", "Save instance")
    }

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
        Log.d("TAG1", "Created")
    }


    private var numStarted = 0
    private var wasMusicPlaying = true

    override fun onActivityStarted(activity: Activity?) {
        if (numStarted == 0) {
            // app went to foreground
            Log.d("TAG1", "Foreground")
            if (wasMusicPlaying){

                Controller.getInstance().setLetsMusicPlay(true, activity)
            }else{
                Controller.getInstance().setLetsMusicPlay(false, activity)
            }
        }
        numStarted++
    }

    override fun onActivityStopped(activity: Activity?) {
        numStarted--
        if (numStarted == 0) {
            // app went to background
            Log.d("TAG1", "Background")
            if (Controller.getInstance().isLetsMusicPlay){
                wasMusicPlaying=true
            }
            else{
                wasMusicPlaying=false
            }
            Controller.getInstance().setLetsMusicPlay(false, activity)
        }
    }

}