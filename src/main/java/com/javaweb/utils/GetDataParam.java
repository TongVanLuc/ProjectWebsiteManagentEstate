package com.javaweb.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDataParam {
    public static Map<String, Object> getParams(Object obj) {
        Map<String, Object> Params = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(obj) != null && !field.get(obj).equals("") && !field.getName().equals("typeCode")) {
                    Params.put(field.getName().toString(), field.get(obj));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return Params;
    }

    public static List<String> getTypeCode(Object obj){
        List<String> typeCode = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(obj) != null && !field.get(obj).equals("") && field.getName().equals("typeCode")) {
                    typeCode = (List<String>) field.get(obj);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return typeCode;
    }
}
