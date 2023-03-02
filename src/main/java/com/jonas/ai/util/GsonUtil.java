package com.jonas.ai.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.Collection;

public class GsonUtil {

    private static final Gson gson = new Gson();

    public static <T> String toJson(T t) {
        return gson.toJson(t);
    }

    public static <T> String toJson(T t, Type type) {
        return gson.toJson(t, type);
    }

    public static <T> T toBean(String json, Class<T> clz) {
        return gson.fromJson(json, clz);
    }

    public static <T> T toBean(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> JsonArray toJsonArray(Collection<T> collection, Type type) {
        return gson.toJsonTree(collection, type).getAsJsonArray();
    }

    public static JsonArray toJsonArray(String json) {
        return JsonParser.parseString(json).getAsJsonArray();
    }

    public static <T> JsonObject toJsonObject(T obj) {
        return gson.toJsonTree(obj).getAsJsonObject();
    }

    public static JsonObject toJsonObject(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }
}
