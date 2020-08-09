package com.tjb.dwf

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GsonModule {

    @Singleton
    @Provides
    fun providesGson(): Gson {
        // TODO does this get called EVERY time we need gson? or just once
        return Gson()
    }
}
