package com.example.fourteenthpractice.data

import android.content.Context
import com.example.fourteenthpractice.model.User
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStreamReader


internal object JSONHelper {

    private const val FILE_NAME = "data.json"


    fun exportToJSON(context: Context, dataList: List<User?>?): Boolean {
        val gson = Gson()
        val dataItems = DataItems()
        dataItems.users = dataList
        val jsonString = gson.toJson(dataItems)
        try {
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { fileOutputStream ->
                fileOutputStream.write(jsonString.toByteArray())
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun importFromJSON(context: Context): List<User?>? {
        try {
            context.openFileInput(FILE_NAME).use { fileInputStream ->
                InputStreamReader(fileInputStream).use { streamReader ->
                    val gson = Gson()
                    val dataItems = gson.fromJson(streamReader, DataItems::class.java)
                    return dataItems.users
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return null
    }

    private class DataItems {
        var users: List<User?>? = null
    }
}