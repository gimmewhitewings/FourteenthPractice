package com.example.fourteenthpractice.myprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class AppDatabase private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE " + FriendsContract.TABLE_NAME + "(" +
                FriendsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                FriendsContract.Columns.NAME + " TEXT NOT NULL, " +
                FriendsContract.Columns.EMAIL + " TEXT, " +
                FriendsContract.Columns.PHONE + " TEXT NOT NULL)"
        db.execSQL(sql)

        // добавление начальных данных
        db.execSQL(
            "INSERT INTO " + FriendsContract.TABLE_NAME + " (" + FriendsContract.Columns.NAME
                    + ", " + FriendsContract.Columns.PHONE + ") VALUES ('Tom', '+12345678990');"
        )
        db.execSQL(
            ("INSERT INTO " + FriendsContract.TABLE_NAME + " (" + FriendsContract.Columns.NAME
                    + ", " + FriendsContract.Columns.EMAIL + ", " + FriendsContract.Columns.PHONE +
                    " ) VALUES ('Bob', 'bob@gmail.com', '+13456789102');")
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        val DATABASE_NAME = "friends.db"
        val DATABASE_VERSION = 1
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                instance = AppDatabase(context)
            }
            return instance
        }
    }
}