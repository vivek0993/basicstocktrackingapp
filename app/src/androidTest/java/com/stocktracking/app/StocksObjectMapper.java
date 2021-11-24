package com.stocktracking.app;

import com.google.gson.Gson;

public class StocksObjectMapper {

    public StocksObjectMapper() {
    }

    public static Object getObjectFromJson(String jsonString, Class resultObjectClass) {
        if (resultObjectClass == null) {
            return jsonString;
        } else {
            try {
                Gson gson = new Gson();
                return gson.fromJson(jsonString, resultObjectClass);
            } catch (Exception var3) {
                return null;
            }
        }
    }

    public static String toJson(Object object) {
        try {
            Gson gson = new Gson();
            return gson.toJson(object);
        } catch (Exception var2) {
            return "";
        }
    }
}
