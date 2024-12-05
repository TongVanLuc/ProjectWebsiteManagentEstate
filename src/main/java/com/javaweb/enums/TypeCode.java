package com.javaweb.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeCode {
    TANG_TRET("Tầng Trệt"),
    NGUYEN_CAN("Nguyên Căn"),
    NOI_THAT("Nội Thất");

    private final String name;

    TypeCode(String name) {
        this.name = name;
    }

    public static Map<String, String> type() {
        Map<String, String> typeCodes = new HashMap<String, String>();
        for (TypeCode item : TypeCode.values()) {
            typeCodes.put(item.toString(), item.name);
        }
        return typeCodes;
    }

}
