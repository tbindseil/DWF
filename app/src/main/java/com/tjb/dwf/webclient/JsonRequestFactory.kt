package com.tjb.dwf.webclient

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import javax.inject.Inject

// TODO can/should I unit test this?
class JsonRequestFactory @Inject constructor() {
    fun createRequest(method: Int,
                      url: String,
                      params: MutableMap<String?, String?>,
                      activityResponseListener: Response.Listener<JSONObject>?,
                      controllerResponseListener: Response.Listener<JSONObject>?,
                      errorListener: Response.ErrorListener,
                      tag: String): JsonObjectRequest {

        val parameters = JSONObject(params as Map<*, *>)

        val responseListener = Response.Listener { response: JSONObject ->
            controllerResponseListener?.onResponse(response)
            activityResponseListener?.onResponse(response)
        }

        val request = JsonObjectRequest(method, url, parameters, responseListener, errorListener)
        request.tag = tag
        return request
    }
}