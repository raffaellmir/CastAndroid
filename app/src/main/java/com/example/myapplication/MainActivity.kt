package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.data.CastManager
import com.example.myapplication.data.CastManager.castSession
import com.example.myapplication.data.CastManager.sessionManager
import com.example.myapplication.ui.screen.MainScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.cast.framework.CastContext

class MainActivity : FragmentActivity() {

    val castManager = CastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        castManager.castContext = CastContext.getSharedInstance(this)


        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        castSession = sessionManager?.currentCastSession
    }
}