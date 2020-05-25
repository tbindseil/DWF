package com.tjb.dwf;

import com.google.gson.Gson;

public class GsonSingleton {
    private static Gson gson;

    public static Gson getInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
