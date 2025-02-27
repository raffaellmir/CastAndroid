package com.example.myapplication.ui.screen

import android.media.MediaMetadata
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.mediarouter.app.MediaRouteButton
import androidx.mediarouter.media.MediaRouteSelector
import androidx.mediarouter.media.MediaRouter
import com.example.myapplication.data.CastManager
import com.example.myapplication.data.CastManager.castContext
import com.example.myapplication.data.CastManager.castSession
import com.example.myapplication.data.CastManager.movieMetaData
import com.example.myapplication.data.CastManager.sessionManager
import com.google.android.gms.cast.CastMediaControlIntent
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.Session
import com.google.android.gms.cast.framework.SessionManagerListener
import com.google.android.gms.cast.framework.media.RemoteMediaClient

@Composable
fun CastButton() {
    val context = LocalContext.current
    AndroidView(
        factory = { ctx ->
            val mediaRouteButton = MediaRouteButton(ctx).apply {
                val mediaRouter = MediaRouter.getInstance(ctx)
                val category = CastMediaControlIntent
                    .categoryForCast(CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID)
                val selector = MediaRouteSelector.Builder()
                    .addControlCategory(category)
                    .build()
                mediaRouter.addCallback(selector, object : MediaRouter.Callback() {})
                setRouteSelector(selector)
            }
            CastButtonFactory.setUpMediaRouteButton(context, mediaRouteButton)

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

            mediaRouteButton
        },
        modifier = Modifier.size(48.dp)
    )
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

@Preview
@Composable
private fun P() {
    CastButton()
}