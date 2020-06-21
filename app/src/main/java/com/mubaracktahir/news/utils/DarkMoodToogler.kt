package com.mubaracktahir.news.utils

import android.content.Context
import android.content.SharedPreferences


/**
 * Created by Mubarak Tahir on 6/20/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */

class DarkMoodToogler(var _context: Context) {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor

    // shared pref mode
    var PRIVATE_MODE = 0
    fun setDarkMode(isFirstTime: Boolean) {
        editor.putBoolean(IS_NIGHT_MODE, isFirstTime)
        editor.commit()
    }

    val isNightMode
        get() = pref.getBoolean(IS_NIGHT_MODE, true)

    companion object {
        // Shared preferences file name
        private const val PREF_NAME = "news_dark_mode"
        private const val IS_NIGHT_MODE = "IsNightMode"
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}