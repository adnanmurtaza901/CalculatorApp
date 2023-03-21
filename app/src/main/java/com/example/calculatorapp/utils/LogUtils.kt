package com.example.calculatorapp.utils

import android.util.Log
import com.example.calculatorapp.BuildConfig


fun Any.logD(message: Any?) {
    logD(message.toString())
}

fun Any.logD(message: String) {
    if (BuildConfig.DEBUG) {
        Log.d(name, message)
    }
}

fun Any.logNA(message: String) {
    Log.d(NATIVE_TAG, message)
}

fun Any.logIA(message: String) {
    Log.d(INTERSTITIAL_TAG, message)
}

fun Any.logAA(message: String) {
    Log.d(APP_OPEN_TAG, message)
}

fun Any.logE(message: String) {
    if (BuildConfig.DEBUG) {
        Log.e(name, message)
    }
}

fun Any.logE(e: Exception) {
    if (BuildConfig.DEBUG) {
        Log.e(name, e.message ?: "Error")
    }
}

private val Any.name: String get() = this::class.java.simpleName
private const val NATIVE_TAG = "native_ad_log"
private const val INTERSTITIAL_TAG = "native_ad_log"
private const val APP_OPEN_TAG = "app_open_ad_log"

