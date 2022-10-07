package org.example.forumServer.utils;

public class CommonUtil {
    public static boolean strIsNull(String str) {
        if (null == str || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }
}
