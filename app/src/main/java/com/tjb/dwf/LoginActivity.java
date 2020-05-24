package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LOGIN_TAG:";
    private HttpRequestQueueAdapter mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mQueue = HttpRequestQueueAdapter.CreateQueueAdapter(this, TAG);
    }

    public void onClickLogin(View v) {
        String username = ((TextView) findViewById(R.id.usernameText)).getText().toString();
        String password = ((TextView) findViewById(R.id.passwordText)).getText().toString();

        // todo send post request
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQueue.cancelAll();
    }
}
