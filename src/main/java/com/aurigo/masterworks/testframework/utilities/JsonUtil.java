package com.aurigo.masterworks.testframework.utilities;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtil {

    //converts a JSON string (jsonResponse) into a Java object of the specified type (classType) using Gson.
    public static <ClassType> ClassType deSerialize(Class<ClassType> type, String jsonResponse)
    {
        try{
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, (Type) type);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String serialize(Object classType){
        try{
            Gson gson = new Gson();
            return gson.toJson(classType);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T  deSerializeFromFile(Type type, String filePath){

        try{
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            return gson.fromJson(reader, type);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
