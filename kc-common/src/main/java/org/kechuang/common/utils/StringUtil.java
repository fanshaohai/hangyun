package org.kechuang.common.utils;

import java.util.List;

/**
 * 字符串处理工具类
 *
 * @author LiLei
 */
public class StringUtil {

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toFirstOneLowerCase(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toFirstOneUpperCase(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 判断是否为空
     *
     * @param s
     * @return
     */
    public static Boolean isNullOrEmpty(String s) {
        if (s == null || s.length() <= 0 || "null".equalsIgnoreCase(s)) {
            return true;
        }
        return false;
    }

    /**
     * @param s
     * @return
     */
    public static Boolean isnotNullorEmpty(String s) {
        if (s != null && s.length() > 0 && (!"null".equalsIgnoreCase(s))) {
            return true;
        }
        return false;
    }

    /**
     * 比较字符串相等
     *
     * @param s1
     * @param s2
     * @return
     */
    public static Boolean equals(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.equals(s2);
    }

    public static String substring(String source, int beginIndex, int endIndex) {
        String str = "";
        if (source != null) {
            str = source.substring(beginIndex, endIndex);
        }
        return str;
    }

    /**
     * 将字符串转换成用于In查询的字符串
     *
     * @param str  字符串
     * @param flag 是否是必须字段
     * @return
     */
    public static String getStringForIn(String str, Boolean flag) {
        String inStr;
        if (flag) {//必须存在
            inStr = "''";
        }
        else {
            inStr = "";
        }
        //是否为空
        if (StringUtil.isNullOrEmpty(str)) {
            return inStr;
        }
        //转换为单引号分隔符
        String[] ids = str.split(",");
        StringBuilder strBuffer = new StringBuilder();
        for (String id : ids) {
            strBuffer.append("'" + id + "',");
        }
        if (strBuffer.length() != 0) {
            inStr = strBuffer.substring(0, strBuffer.length() - 1);
        }
        return inStr;
    }

    public static String listToString(final List<String> strs) {
        final StringBuilder buf = new StringBuilder();
        for (String str : strs) {
            buf.append("'").append(str).append("',");
        }
        String strT = "''";
        if (buf.length() != 0) {
            strT = buf.substring(0, buf.length() - 1);
        }
        return strT;
    }

    /**
     * @param flag
     * @param trueVal true时的值
     * @param trueVal false时的值
     * @return
     */
    public static String getStringVal(boolean flag, String trueVal, String falseVal) {
        if (!flag) {
            return falseVal;
        }
        return trueVal;
    }

    public static int getIntVal(boolean flag, int trueVal, int falseVal) {
        if (!flag) {
            return falseVal;
        }
        return trueVal;
    }

    public static double getDoubleVal(boolean flag, double trueVal, double falseVal) {
        if (!flag) {
            return falseVal;
        }
        return trueVal;
    }

//    public static void main(String[] args) {
//        System.out.println(isnotNullorEmpty("null"));
//        System.out.println(isNullOrEmpty("null"));
//
//        System.out.println(isnotNullorEmpty("null2"));
//        System.out.println(isNullOrEmpty("null2"));
//
//        System.out.println(isnotNullorEmpty(""));
//        System.out.println(isNullOrEmpty(""));
//
//        System.out.println(isnotNullorEmpty(null));
//        System.out.println(isNullOrEmpty(null));
//
//
//    }

}
