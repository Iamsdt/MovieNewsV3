package com.iamsdt.movienewsv3.data

import android.content.Context
import android.os.AsyncTask
import com.iamsdt.movienewsv3.utils.JsonResponseUtils
import com.iamsdt.movienewsv3.utils.NetworkUtils

/**
 * Created by Shudipto on 6/25/2017.
 */

class FetchMovies(context:Context) : AsyncTask<Void, Void, Void>() {

    private var mContext:Context? = null

    init {
        mContext = context
    }


    override fun doInBackground(vararg params: Void?): Void? {

        val jsonData = NetworkUtils()

        val string:String? = jsonData.getResponseFromHttpUrl(mContext!!)

        val jsonResponse = JsonResponseUtils()

        if (string != null) jsonResponse.jsonDataToValues(mContext!!,string)

        return null
    }

}