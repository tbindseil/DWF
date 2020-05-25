package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LOGIN_TAG:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View v) {
        String username = ((TextView) findViewById(R.id.usernameText)).getText().toString();
        String password = ((TextView) findViewById(R.id.passwordText)).getText().toString();

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        JSONObject parameters = new JSONObject(params);

        String url = "https://draw-n-stuff.com/users/authenticate";

        Context context = this;
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "success", Toast.LENGTH_LONG);
            }
        };

        // BUG - sign up with pre existing user
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG);
            }
        };

        Request<JSONObject> request = new JsonObjectRequest(Request.Method.POST, url, parameters, responseListener, errorListener);
        RequestQueueSingleton.getInstance().add(request, TAG);
    }

    public void onClickSignUp(View v) {
        String username = ((TextView) findViewById(R.id.usernameText)).getText().toString();
        String password = ((TextView) findViewById(R.id.passwordText)).getText().toString();

        String firstName = "firstName";
        String lastName = "lastName";

        Map<String, String> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("username", username);
        params.put("password", password);
        JSONObject parameters = new JSONObject(params);

        String url = "https://draw-n-stuff.com/users/register";

        Context context = this;
        Response.Listener responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "success", Toast.LENGTH_LONG);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG);
            }
        };

        Request<JSONObject> request = new JsonObjectRequest(Request.Method.POST, url, parameters, responseListener, errorListener);
        RequestQueueSingleton.getInstance().add(request, TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestQueueSingleton.getInstance().cancelAll(TAG);
    }
}
