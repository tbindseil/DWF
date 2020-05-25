package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        //mQueue.addRequest();
    }

    public void onClickSignUp(View v) {
        String username = ((TextView) findViewById(R.id.usernameText)).getText().toString();
        String password = ((TextView) findViewById(R.id.passwordText)).getText().toString();

        String url = "https://draw-n-stuff.com/users/register";

        //mQueue.addRequest();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestQueueSingleton.getInstance().cancelAll(TAG);
    }
}
