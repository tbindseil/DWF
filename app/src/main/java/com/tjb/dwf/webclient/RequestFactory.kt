package com.tjb.dwf.webclient

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.tjb.dwf.user.UserPojo
import org.json.JSONObject
import javax.inject.Inject

class RequestFactory @Inject constructor(private val jsonRequestFactory: JsonRequestFactory) {
    // login callback?

    fun makeLogoutRequest(userToken: String): JsonObjectRequest {

        val url = "https://draw-n-stuff.com/auth/logout"

        val params = HashMap<String?, String?>()
        val activityResponseListener = Response.Listener { response: JSONObject -> }
        val controllerResponseListener = Response.Listener { response: JSONObject -> }
        val errorListener = Response.ErrorListener { }

        return jsonRequestFactory.createAuthorizedRequest(
                userToken,
                Request.Method.POST,
                url,
                params,
                activityResponseListener,
                controllerResponseListener,
                errorListener,
                "TAG"
        )


    }
}
