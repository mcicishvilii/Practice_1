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
    private var isPaused = false
    private var currentPosition = 0
    private val binder = MusicServiceBinder()
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
    inner class MusicServiceBinder : Binder(){
        fun getService() = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener {
            stopSelf()
        }
    }

    fun playMusic(ab: Int) {
        if (!isPaused) {
            mediaPlayer.reset()
            mediaPlayer = MediaPlayer.create(this, ab)
            mediaPlayer.start()
        } else {
            mediaPlayer.start()
            isPaused = false
        }
    }

    fun stopMusic() {
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            isPaused = false
            currentPosition = 0
        }
    }

    fun pauseMusic() {
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            isPaused = true
            currentPosition = mediaPlayer.currentPosition
        }
    }




    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}


