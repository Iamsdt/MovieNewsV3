package com.iamsdt.movienewsv3.database

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

/**
 * Created by Shudipto on 6/25/2017.
 */


class DbProvider : ContentProvider() {

    private var mHelper: DBHelper? = null

    private val Entry = DataContract()

    //use to get all data from this path
    private val MOVIES: Int = 100
    //use to get single data from a single row
    private val MOVIES_WITH_ID: Int = 101

    //first we check our uri
    private val sUriMatcher: UriMatcher = matcher()

    //uri matcher that's check uri and match type
    private fun matcher(): UriMatcher {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH
        // to the constructor
        val matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        /*
          *All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */

        matcher.addURI(Entry.AUTHORITY, Entry.PATH, MOVIES)
        matcher.addURI(Entry.AUTHORITY, Entry.PATH + "/#", MOVIES_WITH_ID)

        return matcher

    }


    override fun onCreate(): Boolean {
        mHelper = DBHelper(context)
        return true
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {

        val database: SQLiteDatabase = mHelper!!.writableDatabase

        val match: Int = sUriMatcher.match(uri)
        //uri
        val returnUri: Uri

        when (match) {

            MOVIES -> {
                val inserted: Long = database.insert(Entry.TABLE_NAME, null, values)

                if (inserted > 0) {
                    //success
                    returnUri = ContentUris.withAppendedId(Entry.CONTENT_URI, inserted)
                } else {
                    throw RuntimeException("FAILED TO INSERTED DATA: " + uri)
                }

            }
            else -> throw UnsupportedOperationException("Unknown Uri: " + uri)

        }

        //notify data has changed
        context.contentResolver.notifyChange(uri, null)

        return returnUri
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {

        val database: SQLiteDatabase = mHelper!!.writableDatabase

        val match: Int = sUriMatcher.match(uri)
        //uri
        val returnCursor: Cursor

        when (match) {

            MOVIES ->
                returnCursor = database.query(
                        //table
                        Entry.TABLE_NAME,
                        //selected column
                        projection,
                        //section
                        selection,
                        //selection arg
                        selectionArgs,
                        //group by
                        null,
                        //having
                        null,
                        sortOrder)

            // Add a case to query for a single row of data by ID
            // Use selections and selectionArgs to filter for that ID
            MOVIES_WITH_ID -> {
                // Get the id from the URI
                val id = uri!!.pathSegments[1]
                // Selection is the _ID column = ?, and the Selection args = the row ID from the URI
                val mSelection = "_id = ?"
                val mSelectionArg = Array<String>(1) { id }

                // Construct a query as you would normally, passing in the selection/args
                returnCursor = database.query(Entry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArg,
                        null,
                        null,
                        sortOrder)

            }

            else ->
                throw UnsupportedOperationException("Unknown Uri: " + uri)
        }



        returnCursor.setNotificationUri(context.contentResolver, uri)

        return returnCursor
    }


    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {

        val database: SQLiteDatabase = mHelper!!.writableDatabase

        val match: Int = sUriMatcher.match(uri)

        val tasksUpdated: Int

        when (match) {

            MOVIES_WITH_ID -> {
                //update a single task by getting the id
                val id = uri!!.pathSegments[1]

                //using selections
                val mSelection = "_id = ?"
                val mSelectionArg = Array<String>(1) { id }

                // Construct a query as you would normally, passing in the selection/args
                tasksUpdated = database.update(Entry.TABLE_NAME,
                        values,
                        mSelection,
                        mSelectionArg)

            }
            else ->
                throw UnsupportedOperationException("Unknown Uri: " + uri)
        }



        if (tasksUpdated != 0) {
            //set notifications if a task was updated
            context.contentResolver.notifyChange(uri, null)
        }
        return tasksUpdated
    }


    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {

        val database: SQLiteDatabase = mHelper!!.writableDatabase

        val match: Int = sUriMatcher.match(uri)

        val tasksDelete: Int

        when (match) {

            MOVIES_WITH_ID -> {
                //update a single task by getting the id
                val id = uri!!.pathSegments[1]

                //using selections
                val mSelection = "_id = ?"
                val mSelectionArg = Array<String>(1) { id }

                // Construct a query as you would normally, passing in the selection/args
                tasksDelete = database.delete(Entry.TABLE_NAME,
                        mSelection,
                        mSelectionArg)

            }
            else ->
                throw UnsupportedOperationException("Unknown Uri: " + uri)
        }


        if (tasksDelete != 0) {
            //set notifications if a task was updated
            context.contentResolver.notifyChange(uri, null)
        }

        return tasksDelete

    }

    /* getType() handles requests for the MIME type of data
    We are working with two types of data:
    1) a directory and 2) a single row of data.
    This method will not be used in our app, but gives a way to standardize the data formats
    that your provider accesses, and this can be useful for data organization.
    For now, this method will not be used but will be provided for completeness.
    */

    override fun getType(uri: Uri?): String {

        val match = sUriMatcher.match(uri)

        when (match) {

            MOVIES ->
                // directory
                return "vnd.android.cursor.dir" + "/" + Entry.AUTHORITY + "/" + Entry.PATH

            MOVIES_WITH_ID ->
                // single item type
                return "vnd.android.cursor.item" + "/" + Entry.AUTHORITY + "/" + Entry.PATH

            else ->
                throw  UnsupportedOperationException("Unknown uri: " + uri)
        }
    }

}
