package com.blogspot.shudiptotrafder.movienewsv3.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.blogspot.shudiptotrafder.movienewsv3.BuildConfig

/**
 * Created by Shudipto on 6/11/2017.
 */

class DBHelper (context: Context) :
        SQLiteOpenHelper(context,"MOVIES.db", null, 1) {


    val entry = DataContract()

    override fun onCreate(db: SQLiteDatabase?) {

        val sqlCommand = "CREATE TABLE " + entry.TABLE_NAME+ " ( "+
        entry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
        entry.COLUMN_TITLE + " TEXT, "+
        entry.COLUMN_ORIGINAL_TITLE + " TEXT, "+
        entry.COLUMN_LANGUAGE + " TEXT, "+
        entry.COLUMN_RELEASE_DATE + " TEXT, "+
        entry.COLUMN_OVERVIEW + " TEXT, "+
        entry.COLUMN_POSTER_PATH + " TEXT, "+
        entry.COLUMN_POPULARITY + " TEXT, "+
        entry.COLUMN_VOTE_AVERAGE + " TEXT, "+
        entry.COLUMN_VOTE_COUNT + " TEXT, "+
        " UNIQUE ( " + entry.COLUMN_TITLE + " ) ON CONFLICT REPLACE)"

        sle(sqlCommand)

        db!!.execSQL(sqlCommand)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + entry.TABLE_NAME)
        onCreate(db)
    }

    /**
     * This methods show log error message with throwable
     * @param message String show on log
     */
    private fun sle(message: String) {

        val TAG = "DBHelper"

        if (BuildConfig.DEBUG) {
            Log.e(TAG, message)
        }
    }

}
