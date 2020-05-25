package com.tjb.dwf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public abstract class AuthenticatedActivity extends AppCompatActivity {
    protected UserPojo mUser;


    @Override
    // TODO validate user onResume in super class
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_picture);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check login status
        Serializable result = getIntent().getSerializableExtra(UserPojo.SERIALIZE_TAG);
        mUser = result instanceof UserPojo ? (UserPojo) result : null;
        if (mUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
