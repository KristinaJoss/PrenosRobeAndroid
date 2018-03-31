package com.example.toshiba.prenosrobe.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Time;

/**
 * Created by cbojana on 12.3.2018.
 */

public class TimeDeserializer implements JsonDeserializer<Time>
{
    @Override
    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Time time = Time.valueOf(json.getAsString());
        return time;
    }
}
