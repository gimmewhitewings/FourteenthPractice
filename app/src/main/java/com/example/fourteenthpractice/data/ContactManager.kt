package com.example.fourteenthpractice.data

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.provider.ContactsContract.Data
import com.example.fourteenthpractice.model.Contact

class ContactManager(private val context: Context) {
    private val contacts = mutableListOf<Contact>()

    init {
        loadContacts()
    }

    fun getAllContacts(): List<Contact> {
        return contacts
    }

    fun addContact(newContact: Contact) {
        // Добавляем контакт в список контактов
        contacts.add(newContact)

        // Добавляем контакт в телефонную книгу
        val contentResolver = context.contentResolver
        val values = ContentValues().apply {
            put(ContactsContract.RawContacts.ACCOUNT_TYPE, newContact.name)
            put(ContactsContract.RawContacts.ACCOUNT_NAME, newContact.name)
        }
        val rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values)

        val contactId = rawContactUri?.let { ContentUris.parseId(it) }
        values.clear()
        values.apply {
            put(Data.RAW_CONTACT_ID, contactId)
            put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
            put(StructuredName.DISPLAY_NAME, newContact.name)
        }
        contentResolver.insert(Data.CONTENT_URI, values)
    }

    private fun loadContacts() {
        val contentResolver = context.contentResolver

        val projection = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME
        )

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        with(cursor) {
            while (this!!.moveToNext()) {
                val name = getString(getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                contacts.add(Contact(name))
            }
        }

        cursor?.close()
    }
}
