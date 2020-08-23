package com.tjb.dwf.storage

interface Storage {
    fun putString(key: String, value: String?)
    fun getString(key: String, defaultValue: String?): String?
}