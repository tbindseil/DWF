package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tjb.dwf.main.MainActivity;

public class ErrorActivity extends AppCompatActivity {
    public static final String ERROR_SERIALIZATION_TAG = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        // check login status
        String error = getIntent().getStringExtra(ERROR_SERIALIZATION_TAG);
        if (error == null) {
            error = "null";
        }

        TextView errorText = findViewById(R.id.errorText);
        errorText.setText(error);
    }

    public void onClickMainActivityButton(View v) {
        // newActivityFactory.makeNewActivity TODO
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
