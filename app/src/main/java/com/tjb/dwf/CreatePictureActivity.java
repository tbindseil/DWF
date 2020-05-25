package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class CreatePictureActivity extends AppCompatActivity {
    private static final String TAG = "CREATE_PICTURE_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_picture);
    }

    // TODO send title and hopefully username!
    public void onClickSubmit(View v) {
        TextView textView = findViewById(R.id.pictureTitle);

        String url ="https://www.draw-n-stuff.com/api/blog";
        Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                textView.setText("Response is: "+ response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        };

        // Add the request to the RequestQueue.
        JSONObject parameters = new JSONObject();
        Request<JSONObject> request = new JsonObjectRequest(Request.Method.POST, url, parameters, responseListener, errorListener);
        RequestQueueSingleton.getInstance().add(request, TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestQueueSingleton.getInstance().cancelAll(TAG);
    }
}
