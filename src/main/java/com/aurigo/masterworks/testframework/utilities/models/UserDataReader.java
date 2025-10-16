package com.aurigo.masterworks.testframework.utilities.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import static com.aurigo.masterworks.testframework.BaseFramework.userDir;

public class UserDataReader {

    protected static final String USER_DATA_READER = userDir +  "//src//test//resources//User.json";

    public static User getUser()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(new File(USER_DATA_READER), User.class);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read user data from JSON file", e);
        }
    }
}