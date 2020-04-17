package com.teligen.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static String defaultPattern = "yyyy-MM-dd HH:mm:ss.SSS";
    public static String datePattern = "yyyy-MM-dd";
    public static String hourPattern = "HH:mm";
    public static String yearPattern = "yyyy";
    public static String monthPattern = "MM";

    public static Long getCurrentDateMills() {
        return new Date().getTime();
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateTime = sdf.format(new Date());
        return dateTime;
    }

    public static String getCurrentDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(new Date());
        return dateTime;
    }

    public static String getCurrentDateDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = sdf.format(new Date());
        return dateTime;
    }

    public static String getCurrentDateStr(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateTime = sdf.format(new Date());
        return dateTime;
    }

    public static String getLatestDateStr(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        // 获取前月的第天
        Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        cal_1.add(Calendar.DAY_OF_MONTH, -1);
        String latestDay = format.format(cal_1.getTime());
        System.out.println("-----1------latestDay:" + latestDay);
        return latestDay;
    }

    public static String getCurrentMonthFirstDay(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        // 获取前月的第天
        Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置天天当前日期既为本月第一天
        String firstDay = format.format(cal_1.getTime());
        System.out.println("-----1------firstDay:" + firstDay);
        return firstDay;
    }

    public static String getCurrentMonthLastDay(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        // 获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH,
                ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        System.out.println("===============last:" + last);
        return last;
    }

    public static String getDateStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(date);
        return dateTime;
    }

    public static String getDateDayStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = sdf.format(date);
        return dateTime;
    }

    public static Date getDate(String formatStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(formatStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDate(String formatStr) {
        return getDate(formatStr, defaultPattern);
    }

    public static Date getMonthDate(String monthStr){
        return getDate(monthStr, monthPattern);

    }

    public static String getDateAndHourByMills(Long mills) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTime = sdf.format(new Date(mills));
        return dateTime;
    }

    public static String getDateStrByMills(Long mills) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(new Date(mills));
        return dateTime;
    }

    public static String formatDateByMills(Long mills) {
        String result = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfForYear = new SimpleDateFormat("yyyy");
        String todayStr = sdf.format(new Date());
        String yearStr = sdfForYear.format(new Date());

        Date todayDate = getDate(todayStr, datePattern);
        Date yearDate = getDate(yearStr, yearPattern);

        Date inDate = new Date(mills); // 传参进来时间
        if (inDate.getTime() > todayDate.getTime()) { // 当前时间大于当天日期时间
            SimpleDateFormat sdfForHour = new SimpleDateFormat("HH:mm");
            String hourStr = sdfForHour.format(inDate);
            int hour = Integer.valueOf((String) hourStr.subSequence(0, 2));

            if (hour < 12) {
                result = "上午" + hourStr;
            } else if (hour == 12) {
                result = "中午" + hourStr;
            } else {
                result = "下午" + hourStr;
            }
        } else if (inDate.getTime() > yearDate.getTime()) {
            SimpleDateFormat sdfForMonth = new SimpleDateFormat("MM月dd日 HH:mm");
            String monthStr = sdfForMonth.format(inDate);
            result = monthStr;
        } else {
            SimpleDateFormat sdfForYm = new SimpleDateFormat(
                    "yyyy年MM月dd日 HH:mm");
            String ymStr = sdfForYm.format(inDate);
            result = ymStr;
        }

        return result;
    }

    public static boolean isQuickNightTime() {
        boolean isValue = false;
        Date inDate = new Date();
        SimpleDateFormat sdfForHour = new SimpleDateFormat("HH:mm");
        String hourStr = sdfForHour.format(inDate);
        int hour = Integer.valueOf((String) hourStr.subSequence(0, 2));

        if (hour >= 22) {
            isValue = true;
        } else if (hour <= 8) {
            isValue = true;
        }
        return isValue;
    }

    public static String formatDayByMills(Long mills) {
        String result = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfForYear = new SimpleDateFormat("yyyy");
        String todayStr = sdf.format(new Date());
        String yearStr = sdfForYear.format(new Date());

        Date todayDate = getDate(todayStr, datePattern);
        Date yearDate = getDate(yearStr, yearPattern);
        Date inDate = new Date(mills); // 传参进来时间
        if (inDate.getTime() > yearDate.getTime()) {
            SimpleDateFormat sdfForMonth = new SimpleDateFormat("MM月dd日");
            String monthStr = sdfForMonth.format(inDate);
            result = monthStr;
        } else {
            SimpleDateFormat sdfForYm = new SimpleDateFormat("yyyy年MM月dd日");
            String ymStr = sdfForYm.format(inDate);
            result = ymStr;
        }

        return result;
    }

    public static String showDayOrHourByMills(Long mills) {
        String result = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfForYear = new SimpleDateFormat("yyyy");
        String todayStr = sdf.format(new Date());
        String yearStr = sdfForYear.format(new Date());

        Date todayDate = getDate(todayStr, datePattern);
        Date yearDate = getDate(yearStr, yearPattern);
        Date inDate = new Date(mills); // 传参进来时间
        if (inDate.getTime() > todayDate.getTime()) { // 当前时间大于当天日期时间
            SimpleDateFormat sdfForHour = new SimpleDateFormat("HH:mm");
            String hourStr = sdfForHour.format(inDate);
            int hour = Integer.valueOf((String) hourStr.subSequence(0, 2));
            result = hourStr;
        } else if (inDate.getTime() > yearDate.getTime()) {
            SimpleDateFormat sdfForMonth = new SimpleDateFormat("MM月dd日 ");
            String monthStr = sdfForMonth.format(inDate);
            result = monthStr;
        } else {
            SimpleDateFormat sdfForYm = new SimpleDateFormat("yy-MM-dd");
            String ymStr = sdfForYm.format(inDate);
            result = ymStr;
        }

        return result;
    }

    public static String getDateAndHoursByMills(Long mills) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
        String dateTime = sdf.format(new Date(mills));
        return dateTime;
    }

    public static String formatTime(long mills) {
        String result = "";
        int day = (int) (mills / 1000 / 60 / 60 / 24);
        int hour = (int) (mills / 1000 / 60 / 60 - day * 24);
        int min = (int) (mills / 1000 / 60 - day * 24 * 60 - hour * 60);
        result = day + "天" + hour + "小时" + min + "分";
        return result;
    }

    public static final String DEFAULT_DATETIME_FORTMAT = "yyyy-MM-dd HH:mm:ss";

    public static String now() {
        return new SimpleDateFormat(DEFAULT_DATETIME_FORTMAT)
                .format(new Date());
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat(DEFAULT_DATETIME_FORTMAT).format(date);
    }

    /***
     *
     * 得到天干地支
     *
     * @return
     */
    public static String[] getYera() {
        String str[] = new String[107];

        int index1 = 1942;
        // int num = year - 1900 + 36;
        for (int i = 0; i < str.length; i++) {
            str[i] = cyclicalm(index1 - 1900 + 36) + "年(" + index1 + ")";
            index1++;
        }
        return str;
    }

    // ====== 传入 月日的offset 传回干支, 0=甲子
    public static String cyclicalm(int num) {
        final String[] Gan = new String[] { "甲", "乙", "丙", "丁", "戊", "己", "庚",
                "辛", "壬", "癸" };
        final String[] Zhi = new String[] { "子", "丑", "寅", "卯", "辰", "巳", "午",
                "未", "申", "酉", "戌", "亥" };
        return (Gan[num % 10] + Zhi[num % 12]);
    }
}
