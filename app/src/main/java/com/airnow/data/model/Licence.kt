package com.airnow.data.model

import com.airnow.ui.adapter.diff.Diffable

data class Licence(
    val name: String,
    val description: String,
    val url: String,
    val licenceUrl: String = ""
) : Diffable {

    override fun isSame(item: Any) = equals(item)

    override fun hasSameContentWith(item: Any) = equals(item)
}