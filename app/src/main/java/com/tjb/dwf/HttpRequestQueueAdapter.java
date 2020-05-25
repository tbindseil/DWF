package com.tjb.dwf;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestQueueAdapter {
    private RequestQueue mQueue;
    private final String mTag;

    /**
     * creates a queue with default configurations
     * @return a default queue
     */
    public static HttpRequestQueueAdapter CreateQueueAdapter(Context context, String tag) {
        // Instantiate the RequestQueue.
        return new HttpRequestQueueAdapter(Volley.newRequestQueue(context), tag);
    }

    private HttpRequestQueueAdapter(RequestQueue requestQueue, String tag) {
        mQueue = requestQueue;
        mTag = tag;
    }

    public void addRequest(String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        addRequest(url, responseListener, errorListener, params);
    }

    public void addRequest(String url,
                           Response.Listener<JSONObject> responseListener,
                           Response.ErrorListener errorListener,
                           Map<String, String> params) {
        // Request a string response from the provided URL.
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, responseListener, errorListener);
        stringRequest.setTag(mTag);

        // Add the request to the RequestQueue.
        mQueue.add(stringRequest);
    }

    public void cancelAll() {
        mQueue.cancelAll(mTag);
    }
}
