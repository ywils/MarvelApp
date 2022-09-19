package com.android.marvelapp.data.network

import com.android.marvelapp.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest

object ApiUtils {

    fun getMd5String(timestamp: String): String {
        val input = "$timestamp${BuildConfig.PRIVATE_API_KEY}${BuildConfig.PUBLIC_API_KEY}"
        return BigInteger(
            1,
            MessageDigest.getInstance("MD5").digest(input.toByteArray())
        ).toString(16).padStart(32, '0')
    }
}