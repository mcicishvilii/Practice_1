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

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.sampl)
        mediaPlayer.setOnCompletionListener {
            stopSelf()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                if (isPaused) {
                    mediaPlayer.seekTo(currentPosition)
                    mediaPlayer.start()
                    isPaused = false
                } else {
                    mediaPlayer.start()
                }
            }
            ACTION_PAUSE -> {
                mediaPlayer.pause()
                isPaused = true
                currentPosition = mediaPlayer.currentPosition
            }
            ACTION_STOP -> {
                mediaPlayer.stop()
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    companion object {
        const val ACTION_PLAY = "PLAY"
        const val ACTION_PAUSE = "PAUSE"
        const val ACTION_STOP = "STOP"
    }
}


