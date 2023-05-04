package com.example.practice_1.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.practice_1.R


class MusicService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    val TAG = "Musicservisi"
    override fun onBind(intent: Intent?): IBinder? = null

    init {
        Log.d(TAG,"startred")
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.rhcp)
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }
}