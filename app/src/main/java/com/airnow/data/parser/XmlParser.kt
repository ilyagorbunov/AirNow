package com.airnow.data.parser

import com.airnow.data.model.Post
import com.airnow.data.model.Source
import org.xmlpull.v1.XmlPullParser

abstract class XmlParser {

    abstract fun parsePosts(parser: XmlPullParser, source: Source): List<Post>

    fun parseLink(parser: XmlPullParser, attributeName: String = "href"): String {
        val link = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, attributeName)
        parser.nextTag()
        return link
    }
}