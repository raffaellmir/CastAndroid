package com.example.myapplication.ui.screen

import android.media.MediaMetadata
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.data.CastManager.castContext
import com.example.myapplication.data.CastManager.castSession
import com.example.myapplication.data.CastManager.movieMetaData
import com.example.myapplication.data.CastManager.sessionManager
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.framework.Session
import com.google.android.gms.cast.framework.SessionManagerListener
import com.google.android.gms.cast.framework.media.RemoteMediaClient

@Composable
internal fun MainScreen() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            CastButton()

            castContext?.let { sessionManager = it.sessionManager }
            val listener = object : SessionManagerListener<Session> {
                override fun onSessionEnded(p0: Session, p1: Int) { Log.i(javaClass.simpleName, "on Session starting!") }
                override fun onSessionEnding(p0: Session) { Log.i(javaClass.simpleName, "on Session ending! with: $p0") }
                override fun onSessionResumeFailed(p0: Session, p1: Int) { Log.i(javaClass.simpleName, "on Session resume failed!") }
                override fun onSessionResumed(p0: Session, p1: Boolean) { Log.i(javaClass.simpleName, "on Session resumed!") }
                override fun onSessionResuming(p0: Session, p1: String) { Log.i(javaClass.simpleName, "on Session resuming!") }
                override fun onSessionStartFailed(p0: Session, p1: Int) { Log.i(javaClass.simpleName, "on Session start failed! with: $p1") }
                override fun onSessionStarted(p0: Session, p1: String) {
                    Log.i(javaClass.simpleName, "on Session started! with: $p1")
                    castSession?.let { session ->
                        session.remoteMediaClient.let { media ->
                            val mediaMetadataBuilder = MediaMetadata.Builder()
                            mediaMetadataBuilder.putString(MediaMetadata.METADATA_KEY_TITLE,"Media data title")
                            movieMetaData = mediaMetadataBuilder.build()
                            if (media != null)
                                castMovie(media)
                        }
                    }

                }
                override fun onSessionStarting(p0: Session) { Log.i(javaClass.simpleName, "on Session starting!") }
                override fun onSessionSuspended(p0: Session, p1: Int) { Log.i(javaClass.simpleName, "on Session suspended!") }
            }
            sessionManager?.addSessionManagerListener(listener)
        }
    }
}

private fun castMovie(remoteMediaClient: RemoteMediaClient) {
    movieMetaData?.let {

        val mediaInfoData = MediaInfo.Builder("https://www.youtube.com/watch?v=dQw4w9WgXcQ&pp=ygUIcmlja3JvbGw%3D").apply {
            setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
        }.build()

        val request = MediaLoadRequestData.Builder().apply {
            setMediaInfo(mediaInfoData)
            setAutoplay(true)
        }.build()

        remoteMediaClient.load(request)
    }

}