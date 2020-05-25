package com.tjb.dwf;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthorizedJsonObjectRequest extends JsonObjectRequest {
    private String token;

    public AuthorizedJsonObjectRequest(int method,
                                       String url,
                                       JSONObject jsonRequest,
                                       Response.Listener<JSONObject> listener,
                                       Response.ErrorListener errorListener,
                                       String token) {
        super(method, url, jsonRequest, listener, errorListener);
        this.token = token;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        Log.e("getHeaders", "token is: " + token);
        return headers;
    }
}
