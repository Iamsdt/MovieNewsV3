package com.blogspot.shudiptotrafder.movienewsv3.database

import android.net.Uri

/**
 * Created by Shudipto on 6/11/2017.
 */

class DataContract {

    var AUTHORITY: String = "com.blogspot.shudiptotrafder.movienewsv3"

    var BASE_CONTENT_URI: Uri = Uri.parse("content://" + AUTHORITY)

    var PATH: String = "movies"

    //uri to access database
    val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon()
            .appendPath(PATH).build()

    //table name
    val TABLE_NAME = "movie"

    //column
    val COLUMN_ID = "_id"
    val COLUMN_TITLE = "title"
    val COLUMN_ORIGINAL_TITLE = "original_title"
    val COLUMN_POSTER_PATH = "poster_path"
    val COLUMN_LANGUAGE = "language"
    val COLUMN_POPULARITY = "popularity"
    val COLUMN_OVERVIEW = "overview"
    val COLUMN_RELEASE_DATE = "release_date"
    val COLUMN_VOTE_AVERAGE = "vote_avg"
    val COLUMN_VOTE_COUNT = "vote_count"

    fun buildUriWithId(id: Int): Uri {
        return CONTENT_URI.buildUpon().appendPath(id.toString()).build()

    }
}