package com.airnow.ui.adapter

interface UIModelClickListener<in T> {
    fun onClick(model: T)
}