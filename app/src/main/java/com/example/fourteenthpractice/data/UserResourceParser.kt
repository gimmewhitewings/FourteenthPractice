package com.example.fourteenthpractice.data

import com.example.fourteenthpractice.model.User
import org.xmlpull.v1.XmlPullParser


class UserResourceParser {
    val users: ArrayList<User?> = ArrayList()

    fun parse(xpp: XmlPullParser): Boolean {
        var status = true
        var currentUser: User? = null
        var inEntry = false
        var textValue: String? = ""
        try {
            var eventType = xpp.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = xpp.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if ("user".equals(tagName, ignoreCase = true)) {
                        inEntry = true
                        currentUser = User()
                    }

                    XmlPullParser.TEXT -> textValue = xpp.text
                    XmlPullParser.END_TAG -> if (inEntry) {
                        if ("user".equals(tagName, ignoreCase = true)) {
                            users.add(currentUser)
                            inEntry = false
                        } else if ("name".equals(tagName, ignoreCase = true)) {
                            currentUser!!.name = textValue!!
                        } else if ("age".equals(tagName, ignoreCase = true)) {
                            currentUser!!.age = textValue!!.toInt()
                        }
                    }

                    else -> {}
                }
                eventType = xpp.next()
            }
        } catch (e: Exception) {
            status = false
            e.printStackTrace()
        }
        return status
    }
}