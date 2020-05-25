package com.tjb.dwf;

import java.io.Serializable;

public class UserPojo implements Serializable {
    public static final String SERIALIZE_TAG = "USER";

    public String firstName;
    public String lastName;

    // TODO databinding
    public UserPojo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
