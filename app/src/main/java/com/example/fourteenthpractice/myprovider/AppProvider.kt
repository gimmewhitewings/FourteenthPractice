package com.example.fourteenthpractice.myprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.example.fourteenthpractice.myprovider.FriendsContract.buildFriendUri
import com.example.fourteenthpractice.myprovider.FriendsContract.getFriendId


class AppProvider : ContentProvider() {
    private var mOpenHelper: AppDatabase? = null
    override fun onCreate(): Boolean {
        mOpenHelper = AppDatabase.getInstance(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val match = sUriMatcher.match(uri)
        val queryBuilder = SQLiteQueryBuilder()
        when (match) {
            FRIENDS -> queryBuilder.tables = FriendsContract.TABLE_NAME
            FRIENDS_ID -> {
                queryBuilder.tables = FriendsContract.TABLE_NAME
                val taskId = getFriendId(uri)
                queryBuilder.appendWhere(FriendsContract.Columns._ID + " = " + taskId)
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        val db = mOpenHelper!!.readableDatabase
        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun getType(uri: Uri): String {
        return when (sUriMatcher.match(uri)) {
            FRIENDS -> FriendsContract.CONTENT_TYPE
            FRIENDS_ID -> FriendsContract.CONTENT_ITEM_TYPE
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val match = sUriMatcher.match(uri)
        val db: SQLiteDatabase
        val returnUri: Uri
        val recordId: Long
        if (match == FRIENDS) {
            db = mOpenHelper!!.writableDatabase
            recordId = db.insert(FriendsContract.TABLE_NAME, null, values)
            returnUri = if (recordId > 0) {
                buildFriendUri(recordId)
            } else {
                throw SQLException("Failed to insert: $uri")
            }
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
        return returnUri
    }

    override fun delete(
        uri: Uri, selection: String?, selectionArgs: Array<String>?
    ): Int {
        val match = sUriMatcher.match(uri)
        val db = mOpenHelper!!.writableDatabase
        var selectionCriteria = selection
        require(!(match != FRIENDS && match != FRIENDS_ID)) { "Unknown URI: $uri" }
        if (match == FRIENDS_ID) {
            val taskId = getFriendId(uri)
            selectionCriteria = FriendsContract.Columns._ID + " = " + taskId
            if (!selection.isNullOrEmpty()) {
                selectionCriteria += " AND ($selection)"
            }
        }
        return db.delete(FriendsContract.TABLE_NAME, selectionCriteria, selectionArgs)
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?
    ): Int {
        val match = sUriMatcher.match(uri)
        val db = mOpenHelper!!.writableDatabase
        var selectionCriteria = selection
        require(!(match != FRIENDS && match != FRIENDS_ID)) { "Unknown URI: $uri" }
        if (match == FRIENDS_ID) {
            val taskId = getFriendId(uri)
            selectionCriteria = FriendsContract.Columns._ID + " = " + taskId
            if (!selection.isNullOrEmpty()) {
                selectionCriteria += " AND ($selection)"
            }
        }
        return db.update(FriendsContract.TABLE_NAME, values, selectionCriteria, selectionArgs)
    }

    companion object {
        private val sUriMatcher = buildUriMatcher()
        const val FRIENDS = 100
        const val FRIENDS_ID = 101
        private fun buildUriMatcher(): UriMatcher {
            val matcher = UriMatcher(UriMatcher.NO_MATCH)
            // content://com.example.friendsprovider/FRIENDS
            matcher.addURI(
                FriendsContract.CONTENT_AUTHORITY,
                FriendsContract.TABLE_NAME,
                FRIENDS
            )
            // content://com.example.friendsprovider/FRIENDS/8
            matcher.addURI(
                FriendsContract.CONTENT_AUTHORITY,
                FriendsContract.TABLE_NAME + "/#",
                FRIENDS_ID
            )
            return matcher
        }
    }
}