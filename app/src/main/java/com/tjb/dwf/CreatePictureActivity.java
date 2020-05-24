package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CreatePictureActivity extends AppCompatActivity {
    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_picture);

        try {
            // why not inject this dep in?
            mClient = new MobileServiceClient("https://52.250.108.250:5001/Index", this);
        } catch (Exception e) {
            Log.e("CreatePictureActivity", "exception creating mobile client");
            Log.e("CreatePictureActivity", e.getMessage());
            e.printStackTrace();
        }
    }

    // TODO send title and hopefully username!
    public void onClickSubmit(View v) {
        MobileServiceTable<ToDoItem> toDoTable = mClient.getTable(ToDoItem.class);
        try {
            List<ToDoItem> results = toDoTable
                    .execute()
                    .get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            Toast.makeText(this,"exception", Toast.LENGTH_SHORT).show();
            Log.e("Exception", e.getMessage());
            e.printStackTrace();
        }
        Log.e("problem", "problem");
        Toast.makeText(this,"no exception?", Toast.LENGTH_SHORT).show();
    }
}
