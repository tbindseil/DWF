package com.tjb.dwf.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tjb.dwf.ErrorActivity;
import com.tjb.dwf.GsonSingleton;
import com.tjb.dwf.R;
import com.tjb.dwf.RequestQueueSingleton;
import com.tjb.dwf.main.MainActivity;
import com.tjb.dwf.user.UserPojo;

import org.json.JSONObject;

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

        Response.Listener<JSONObject> responseListener = (JSONObject response) -> {
            onResponse(response);
        };

        Response.ErrorListener errorListener = (VolleyError error) -> {
            onErrorResponse(error);
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

        Response.Listener<JSONObject> responseListener = (JSONObject response) -> {
            onResponse(response);
        };

        Response.ErrorListener errorListener = (VolleyError error) -> {
            onErrorResponse(error);
        };

        Request<JSONObject> request = new JsonObjectRequest(Request.Method.POST, url, parameters, responseListener, errorListener);
        RequestQueueSingleton.getInstance().add(request, TAG);
    }

    private void onResponse(JSONObject response) {
        try {
            // save User
            UserPojo user = GsonSingleton.getInstance().fromJson(response.toString(), UserPojo.class);
            SharedPreferences sharedPref = getSharedPreferences("USER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            String userJson = GsonSingleton.getInstance().toJson(user);
            editor.putString("USER", userJson);
            editor.commit();

            // serialize user to main activity
            Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
            intent.putExtra(UserPojo.SERIALIZE_TAG, user);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("SignUpResponseHandler", "Exception generating pojo");
            finish();
        }
    }

    private void onErrorResponse(VolleyError error) {
        String errorStr = error.toString();
        Intent intent = new Intent(this.getApplicationContext(), ErrorActivity.class);
        intent.putExtra(ErrorActivity.ERROR_SERIALIZATION_TAG, errorStr);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestQueueSingleton.getInstance().cancelAll(TAG);
    }
}