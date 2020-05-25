package com.tjb.dwf;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthorizedJsonArrayRequest extends JsonArrayRequest {
    private String token;

    public AuthorizedJsonArrayRequest(int method,
                                       String url,
                                       JSONArray jsonRequest,
                                       Response.Listener<JSONArray> listener,
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
