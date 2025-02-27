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

            mediaRouteButton
        },
        modifier = Modifier.size(48.dp)
    )
}

@Preview
@Composable
private fun P() {
    CastButton()
}