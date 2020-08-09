package com.tjb.dwf.webclient

import android.content.Context
import com.android.volley.Cache
import com.android.volley.Network
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WebModule {

    @Provides
    fun providesCache(context: Context): Cache {
        return DiskBasedCache(context.cacheDir, 1024 * 1024) // 1MB cap
    }

    @Provides
    fun providesNetwork(): Network {
        return BasicNetwork(HurlStack())
    }

    @Singleton
    @Provides
    fun providesRequestQueue(cache: Cache, network: Network): RequestQueue {
        val ret = RequestQueue(cache, network)
        ret.start()
        return ret
    }
}