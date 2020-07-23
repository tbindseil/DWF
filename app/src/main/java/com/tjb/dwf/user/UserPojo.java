package com.tjb.dwf.user;

import java.io.Serializable;

public class UserPojo implements Serializable {
    public static final String SERIALIZE_TAG = "USER";

    public String firstName;
    public String lastName;
    public String token;
    public int id;

    public UserPojo(String firstName, String lastName, String token, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.id = id;
    }
}
