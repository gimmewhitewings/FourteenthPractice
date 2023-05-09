package com.example.fourteenthpractice.ui

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.fourteenthpractice.databinding.FragmentMyProviderBinding
import com.example.fourteenthpractice.myprovider.FriendsContract
import java.security.InvalidParameterException


class MyProviderFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private val TAG: String = "MyProvider"
    private lateinit var binding: FragmentMyProviderBinding
    private val LOADER_ID = 225


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyProviderBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            getButton.setOnClickListener { getAll() }
            addButton.setOnClickListener { add() }
            updateButton.setOnClickListener { update() }
            deleteButton.setOnClickListener { delete() }
        }
    }

    private fun getAll() {
        val projection = arrayOf(
            FriendsContract.Columns._ID,
            FriendsContract.Columns.NAME,
            FriendsContract.Columns.EMAIL,
            FriendsContract.Columns.PHONE
        )
        val contentResolver: ContentResolver = requireContext().contentResolver
        val cursor = contentResolver.query(
            FriendsContract.CONTENT_URI,
            projection,
            null,
            null,
            FriendsContract.Columns.NAME
        )
        if (cursor != null) {
            Log.d(TAG, "count: " + cursor.count)
            // перебор элементов
            while (cursor.moveToNext()) {
                for (i in 0 until cursor.columnCount) {
                    Log.d(TAG, cursor.getColumnName(i) + " : " + cursor.getString(i))
                }
                Log.d(TAG, "=========================")
            }
            cursor.close()
        } else {
            Log.d(TAG, "Cursor is null")
        }
    }

    private fun add() {
        val contentResolver: ContentResolver = requireContext().contentResolver
        val values = ContentValues()
        values.put(FriendsContract.Columns.NAME, "Sam")
        values.put(FriendsContract.Columns.EMAIL, "sam@gmail.com")
        values.put(FriendsContract.Columns.PHONE, "+13676254985")
        val uri = contentResolver.insert(FriendsContract.CONTENT_URI, values)
        Log.d(TAG, "Friend added")
    }

    private fun update() {
        val contentResolver: ContentResolver = requireContext().contentResolver
        val values = ContentValues()
        values.put(FriendsContract.Columns.EMAIL, "sammy@gmail.com")
        values.put(FriendsContract.Columns.PHONE, "+55555555555")
        val selection = FriendsContract.Columns.NAME + " = 'Sam'"
        val count = contentResolver.update(FriendsContract.CONTENT_URI, values, selection, null)
        Log.d(TAG, "Friend updated")
    }

    private fun delete() {
        val contentResolver: ContentResolver = requireContext().contentResolver
        val selection = FriendsContract.Columns.NAME + " = ?"
        val args = arrayOf("Sam")
        val count = contentResolver.delete(FriendsContract.CONTENT_URI, selection, args)
        Log.d(TAG, "Friend deleted")
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(
            FriendsContract.Columns._ID,
            FriendsContract.Columns.NAME,
            FriendsContract.Columns.EMAIL,
            FriendsContract.Columns.PHONE
        )
        return if (id == LOADER_ID) CursorLoader(
            requireContext(), FriendsContract.CONTENT_URI,
            projection,
            null,
            null,
            FriendsContract.Columns.NAME
        ) else throw InvalidParameterException("Invalid loader id")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        Log.d(TAG, "onLoaderReset...")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null) {
            Log.d(TAG, "count: " + data.count)
            // перебор элементов
            while (data.moveToNext()) {
                for (i in 0 until data.columnCount) {
                    Log.d(TAG, data.getColumnName(i) + " : " + data.getString(i))
                }
                Log.d(TAG, "=========================")
            }
            data.close()
        } else {
            Log.d(TAG, "Cursor is null")
        }
    }
}