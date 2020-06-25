package com.tjb.dwf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public abstract class AuthenticatedActivity extends AppCompatActivity {
    protected UserPojo mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("debug", "AuthenticatedActivity.onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // maybe these two are moved to user controller?

        // check login status from previous activity
        Serializable result = getIntent().getSerializableExtra(UserPojo.SERIALIZE_TAG);
        mUser = result instanceof UserPojo ? (UserPojo) result : null;
        if (mUser != null) {
            return;
        }

        // check for token in storage
        SharedPreferences sharedPref = getSharedPreferences("USER", Context.MODE_PRIVATE);
        String userJson = sharedPref.getString("USER", null);
        Log.e("AuthenticatedActivity", "userJson is " + userJson);
        mUser = GsonSingleton.getInstance().fromJson(userJson, UserPojo.class);

        if (mUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
