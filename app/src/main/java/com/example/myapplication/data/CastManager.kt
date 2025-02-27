package com.example.myapplication.data

import android.media.MediaMetadata
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.SessionManager

object CastManager {
    var castContext: CastContext? = null
    var castSession: CastSession? = null
    var sessionManager: SessionManager? = null
    var movieMetaData: MediaMetadata? = null
}