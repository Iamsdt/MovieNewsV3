package com.iamsdt.movienewsv3.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.iamsdt.movienewsv3.BuildConfig
import com.iamsdt.movienewsv3.utils.Utilities
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Created by Shudipto on 6/25/2017.
 */

class NetworkUtils{

    private fun accurateUrl(context: Context): String {

        val utility = Utilities()

        val baseUrl = "http://api.themoviedb.org/3/movie/${utility.getMoviesShortType(context)}"
        val apiKey = "api_key"

        val buildUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(apiKey, BuildConfig.MOVIE_API_KEY).build()

        val uri = buildUri.toString()

        sle(uri)

        return uri
    }

    @Throws(IOException::class)

    fun getResponseFromHttpUrl(context: Context): String? {

        val url = URL(accurateUrl(context))

        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

        urlConnection.requestMethod = "GET"
        urlConnection.readTimeout = 10000
        urlConnection.readTimeout = 15000

        if (urlConnection.responseCode == 200) {

            try {
                val stream = urlConnection.inputStream

                val scanner = Scanner(stream)

                scanner.useDelimiter("\\A")

                print(readLine())

                if (scanner.hasNext()) {
                    return scanner.next()
                } else {
                    return null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                slet(e)

            } finally {
                urlConnection.disconnect()
            }
        }

        return null
    }

    /**
     * This methods show log error message with throwable
     * @param message String show on log
     */
    private fun sle(message: String) {

        val TAG = "NetworkUtils"

        if (BuildConfig.DEBUG) {
            Log.e(TAG, message)
        }
    }

    /**
     * This methods show log error message with throwable

     * @param t       throwable that's show on log
     */

    private fun slet(t: Throwable) {

        val TAG = "NetworkUtils"

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "getResponseFromHttpUrl", t)
        }
    }
}

