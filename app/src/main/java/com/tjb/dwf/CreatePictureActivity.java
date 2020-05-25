package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class CreatePictureActivity extends AuthenticatedActivity {
    private static final String TAG = "CREATE_PICTURE_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_picture);
    }

    // TODO send title and hopefully username!
    public void onClickSubmit(View v) {
        TextView textView = findViewById(R.id.pictureTitle);

        String url ="https://www.draw-n-stuff.com/users";
        Response.Listener responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Display the first 500 characters of the response string.
                textView.setText("Response is: " + response.toString());
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("error! " + error.toString());
            }
        };

        // Add the request to the RequestQueue.
        Request<JSONArray> request = new AuthorizedJsonArrayRequest(Request.Method.GET,
                url,
                new JSONArray(),
                responseListener,
                errorListener,
                mUser.token);
        RequestQueueSingleton.getInstance().add(request, TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestQueueSingleton.getInstance().cancelAll(TAG);
    }
}
