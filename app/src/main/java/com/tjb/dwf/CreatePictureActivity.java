package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.ExecutableQuery;

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
            //ExecutableQuery<ToDoItem> query = toDoTable.where();
            //query = query.field("text");
            //query = query.eq("TJTAG");

            //ListenableFuture<MobileServiceList<ToDoItem>> soon2be = query.execute();
            //List<ToDoItem> results = soon2be.get(10, TimeUnit.SECONDS);

            List<ToDoItem> results = toDoTable
                    .where()
                    .field("test")
                    .eq("TJTAG")
                    .execute()
                    .get();
        } catch (Exception e) {
            Toast.makeText(this,"exception", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this,"no exception?", Toast.LENGTH_SHORT).show();
    }
}
