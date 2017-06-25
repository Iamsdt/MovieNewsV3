package com.iamsdt.movienewsv3.utils

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.iamsdt.movienewsv3.BuildConfig
import com.iamsdt.movienewsv3.database.DataContract
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Shudipto on 6/25/2017.
 */

class JsonResponseUtils {
    
    private val LIST = "results"

    //all json object
    private val Poster_path = "poster_path"
    private val Overview = "overview"
    private val Release_date = "release_date"
    private val Original_title = "original_title"
    private val Original_language = "original_language"
    private val Title = "title"
    private val Popularity = "popularity"
    private val Vote_average = "vote_average"
    private val Vote_count = "vote_count"
    
    //all table entry
    private val Entry = DataContract()

    //this is for insert data for a single row wise
    fun jsonDataToValues(context: Context, jsonStr: String) {

        if (jsonStr.length < 0) {
            return
        }

        try {
            val jsonObject = JSONObject(jsonStr)

            //json array
            val jsonArray = jsonObject.getJSONArray(LIST)


            for (i in 0..jsonArray.length() - 1) {

                //taking object from position of i
                val result = jsonArray.getJSONObject(i)

                //poster path
                val path = result.getString(Poster_path)
                //overview
                val overview = result.getString(Overview)

                //release_date
                val release_date = result.getString(Release_date)

                //original_title
                val original_title = result.getString(Original_title)

                //original_language
                val original_language = result.getString(Original_language)

                //title
                val title = result.getString(Title)

                //popularity
                val popularity = result.getString(Popularity)

                //vote_average
                val vote_average = result.getString(Vote_average)

                //vote_average
                val vote_count = result.getString(Vote_count)

                val values = ContentValues()

                values.put(Entry.COLUMN_POSTER_PATH, path)
                values.put(Entry.COLUMN_TITLE, title)
                values.put(Entry.COLUMN_OVERVIEW, overview)
                values.put(Entry.COLUMN_RELEASE_DATE, release_date)
                values.put(Entry.COLUMN_ORIGINAL_TITLE, original_title)
                values.put(Entry.COLUMN_LANGUAGE, original_language)
                values.put(Entry.COLUMN_POPULARITY, popularity)
                values.put(Entry.COLUMN_VOTE_COUNT, vote_count)
                values.put(Entry.COLUMN_VOTE_AVERAGE, vote_average)

                val uri = context.contentResolver.insert(Entry.CONTENT_URI, values)

                if (uri != null) {
                    sle(uri.toString())
                }

            }
        } catch (e: JSONException) {
            e.printStackTrace()
            slet(e)
        }

    }
    /**
     * This methods show log error message with throwable
     * @param t throwable
     */
    private fun slet(t:Throwable) {

        val TAG = "DBHelper"

        if (BuildConfig.DEBUG) {
            Log.e(TAG,"JSONException ",t)
        }
    }

    //we are using bulk insert methods
    //for that use this methods
    fun getJsonDataToValues(jsonData: String): Array<ContentValues>? {

        try {

            //make new json object
            val jsonObject = JSONObject(jsonData)

            val jsonArray = jsonObject.getJSONArray(LIST)

            //that's return all value in array
            val moviesValue:Array<ContentValues>? = null

            for (i in 0..jsonArray.length() - 1) {

                //taking object from position of i
                val result = jsonArray.getJSONObject(i)

                //poster path
                val path = result.getString(Poster_path)
                //overview
                val overview = result.getString(Overview)

                //release_date
                val release_date = result.getString(Release_date)

                //original_title
                val original_title = result.getString(Original_title)

                //original_language
                val original_language = result.getString(Original_language)

                //title
                val title = result.getString(Title)

                //popularity
                val popularity = result.getString(Popularity)

                //vote_average
                val vote_average = result.getString(Vote_average)

                //vote_average
                val vote_count = result.getString(Vote_count)

                val values = ContentValues()
                values.put(Entry.COLUMN_POSTER_PATH, path)
                values.put(Entry.COLUMN_TITLE, title)
                values.put(Entry.COLUMN_OVERVIEW, overview)
                values.put(Entry.COLUMN_RELEASE_DATE, release_date)
                values.put(Entry.COLUMN_ORIGINAL_TITLE, original_title)
                values.put(Entry.COLUMN_LANGUAGE, original_language)
                values.put(Entry.COLUMN_POPULARITY, popularity)
                values.put(Entry.COLUMN_VOTE_COUNT, vote_count)
                values.put(Entry.COLUMN_VOTE_AVERAGE, vote_average)

                //save on array on content values
                moviesValue?.set(i, values)
            }

            sle(moviesValue!!.size.toString())

            return moviesValue

        } catch (e: JSONException) {
            e.printStackTrace()
            slet(e)
            return null
        }

    }

    /**
     * This methods show log error message with throwable
     * @param message String show on log
     */
    private fun sle(message: String) {

        val TAG = "Json Utils"

        if (BuildConfig.DEBUG) {
            Log.e(TAG, message)
        }
    }
}