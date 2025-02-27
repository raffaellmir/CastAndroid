package com.example.myapplication.data

import android.content.Context
import com.google.android.gms.cast.framework.CastOptions
import com.google.android.gms.cast.framework.OptionsProvider

class CastOptionsProvider : OptionsProvider {
    override fun getCastOptions(context: Context): CastOptions {
        val appId = "CC1AD845"
        return CastOptions.Builder()
            .setReceiverApplicationId(appId)
            .build()
    }

    override fun getAdditionalSessionProviders(context: Context) = null
}
