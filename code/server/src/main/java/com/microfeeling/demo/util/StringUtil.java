package com.microfeeling.demo.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Add by jianhan on 2018/6/5
 */
public class StringUtil {

    public static boolean isEmpty(Object obj) {
        if (obj instanceof List) {
            return ((List)obj).isEmpty();
        } else if (obj instanceof Set) {
            return ((Set)obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map)obj).isEmpty();
        }
        return obj == null || obj.toString().isEmpty();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.toString().isEmpty();
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String trim(Object str) {
        return str == null ? "": str.toString().trim();
    }

    public static String trim(String str) {
        return str == null ? "": str.toString().trim();
    }
}
