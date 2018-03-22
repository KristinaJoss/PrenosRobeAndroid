package com.example.toshiba.prenosrobe.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Time;

/**
 * Created by Tina on 15.3.2018.
 */

public class TimeSerializer implements JsonSerializer<Time> {
    @Override
    public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getHours() + ":" + src.getMinutes());
    }
}
