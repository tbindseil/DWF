package com.tjb.dwf;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

public class RequestQueueSingleton extends RequestQueue {
    private static RequestQueueSingleton instance;

    // Instantiate the cache
    // Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
    // Set up the network to use HttpURLConnection as the HTTP client.
    // Network network = new BasicNetwork(new HurlStack());
    private RequestQueueSingleton(Cache cache, Network network) {
        super(cache, network);
    }

    public static synchronized void init(Cache cache, Network network) {
        if (instance == null) {
            instance = new RequestQueueSingleton(cache, network);
            instance.start();
        } else {
            Log.e("RequestQueueSingleton", "inited again!");
        }
    }

    public static synchronized RequestQueueSingleton getInstance() {
        if (instance == null) {
            Log.e("RequestQueueSingleton", "not inited yet!");
        }
        return instance;
    }

    public void add(Request request, Object tag) {
        request.setTag(tag);
        add(request);
    }
}
