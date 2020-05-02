package com.tjb.dwf;

import android.content.Context;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

public class AzureServiceAdapter {
    private final String mMobileBackendUrl = "https://52.250.108.250";
    private Context mContext;
    private MobileServiceClient mClient;
    private static AzureServiceAdapter mInstance = null;

    private AzureServiceAdapter(Context context) {
        mContext = context;
        try {
            // why not inject this dep in?
            mClient = new MobileServiceClient(mMobileBackendUrl, mContext);
        } catch (Exception e) {
            Log.e("CreatePictureActivity", "exception creating mobile client");
            Log.e("CreatePictureActivity", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void Initialize(Context context) {
        if (mInstance == null) {
            mInstance = new AzureServiceAdapter(context);
        } else {
            // might throw if main activity is destroyed and then we head back there
            throw new IllegalStateException("AzureServiceAdapter is already initialized");
        }
    }

    public static AzureServiceAdapter getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("AzureServiceAdapter is not initialized");
        }
        return mInstance;
    }

    // why public?
    public MobileServiceClient getClient() {
        return mClient;
    }

    // Place any public methods that operate on mClient here.

    public MobileServiceTable<ToDoItem> getToDoTable() {
        return mClient.getTable(ToDoItem.class);
    }
}
