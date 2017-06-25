package com.iamsdt.movienewsv3.utils

import android.content.Context
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import com.iamsdt.movienewsv3.R

/**
 * Created by Shudipto on 6/25/2017.
 */

class Utilities{

    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val info = manager.activeNetworkInfo

        return info != null && info.isConnectedOrConnecting
    }

    internal fun getMoviesShortType(context: Context): String {

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)


        return preferences.getString(context.getString(R.string.pref_sort_movie_key),
                context.getString(R.string.pref_sort_movie_popular_value))
    }

    fun getTextSize(context: Context): Int {

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        val s = preferences.getString(context.getString(R.string.textSizeKey),
                context.getString(R.string.sTextModerateValue))

        return Integer.parseInt(s)

    }


    fun getNightModeEnabled(context: Context): Boolean {

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        return preferences.getBoolean(context.getString(R.string.switchKey), false)
    }
}