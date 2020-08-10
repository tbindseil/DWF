package com.tjb.dwf.settings

import java.io.Serializable

abstract class Setting constructor(
        private var value: Serializable,
        private val key: String,
        private val callback: SetCallback) {

    abstract fun get()
    abstract fun set(newValue: Serializable)

    // I think get will just look into shared preferences

    //open fun onNotInLocalStorage() {
        // do nothing
    //}
}

// not sure how to do this in kotlin
interface SetCallback {
    fun onSet()
}