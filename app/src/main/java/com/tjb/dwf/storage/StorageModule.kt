package com.tjb.dwf.storage

import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface StorageModule {
    @Singleton
    @Binds
    fun bindsStorage(sharedPreferencesStorage: SharedPreferencesStorage): Storage
}