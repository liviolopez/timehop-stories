package com.liviolopez.timehopstories.ui._components

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.net.toUri
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.liviolopez.timehopstories.utils.extensions.setGone
import com.liviolopez.timehopstories.utils.extensions.setVisible
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

class AppPlayer(context: Context) {
    private val TAG = "AppPlayer"

    interface OnProgressListener {
        fun onUpdatePosition(currentPosition: Long)
    }

    var player: SimpleExoPlayer = SimpleExoPlayer.Builder(context).build()
    var dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory(Util.getUserAgent(context, context.packageName))

    var currentPlayerView: PlayerView? = null
    var loadingBar: View? = null
    var onProgressListener: OnProgressListener? = null
    private val semaphoreListener = Semaphore(1)

    init {
        player.repeatMode = Player.REPEAT_MODE_ONE

        player.addListener(object: Player.EventListener{
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                if(isPlaying) progressListener()
            }

            override fun onLoadingChanged(isLoading: Boolean) {
                super.onLoadingChanged(isLoading)

                if(isLoading && !player.isPlaying) {
                    loadingBar?.setVisible()
                } else {
                    loadingBar?.setGone()
                }
            }
        })
    }

    private fun play() {
        player.playWhenReady = true
    }

    fun pause() {
        player.playWhenReady = false
    }

    fun release(){
        player.stop()
        player.release()

        currentPlayerView?.player = null
    }

    fun resume(){
        if(currentPlayerView?.player !== null) {
            player.playWhenReady = true
        }
    }

    fun cleanView(){
        currentPlayerView = null
    }

    private fun getVideoSource(videoUrl: String) = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUrl.toUri())

    fun setTrackAndPlay(playerView: PlayerView, videoUrl: String, position: Long) {
        currentPlayerView = playerView

        if(player.isPlaying) player.stop()

        playerView.setOnTouchListener(videoViewTouchPlayBack)
        playerView.player = null
        playerView.player = player
        playerView.useController = false

        player.prepare(getVideoSource(videoUrl))
        player.seekTo(position)
        play()
    }

    @SuppressLint("ClickableViewAccessibility")
    private val videoViewTouchPlayBack = View.OnTouchListener { playerView, event ->
        playerView as PlayerView?

        when(event?.action){
            MotionEvent.ACTION_UP -> play()
            MotionEvent.ACTION_DOWN -> if(player.isPlaying) pause()
        }

        true
    }

    private fun progressListener() {
        CoroutineScope(Dispatchers.IO).launch {
            semaphoreListener.withPermit {
                var isPlaying = true

                while (isPlaying) {
                    delay(1000)

                    withContext(Dispatchers.Main) {
                        Log.v(TAG, "Current video on position: ${player.contentPosition}")

                        if(player.isPlaying && loadingBar != null) loadingBar?.setGone()

                        onProgressListener?.onUpdatePosition(player.contentPosition)
                        isPlaying = player.isPlaying
                    }
                }
            }
        }
    }
}