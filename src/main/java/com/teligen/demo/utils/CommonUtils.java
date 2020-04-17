package com.teligen.demo.utils;

import com.alibaba.fastjson.JSONObject;
import org.codehaus.xfire.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CommonUtils {

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static final long BUSINESS_EXPIRE_TIME = 1000 * 10L;

    public static void main(String[] args) {

        System.out.println(UUID.randomUUID().toString().replace("-", ""));

		/*XckySceneInfo xckySceneInfo = new XckySceneInfo();

		xckySceneInfo.setSceneInfo(null);
		xckySceneInfo.setSceneAnalysisSuggestion(null);
		xckySceneInfo.setCaseInfo(null);
		xckySceneInfo.setArrestInfo(null);
		xckySceneInfo.setPictureInfo(new TbPictureInfo());
		xckySceneInfo.setInvestigationId("");//勘验主键
		xckySceneInfo.setInvestigationType("456");//勘验类型
		try {
			List<String> str=isObjectFieldEmpty(xckySceneInfo);
			logger.info(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}*/

    }


    public static boolean checkValidJson(String content) {
        if (content == null || content.equals("")) {
            return false;
        }
        try {
            JSONObject.parseObject(content);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    //特殊字眼过滤
    public static String getSafeString(String content) {
        if ("".equals(CommonUtils.safeToString(content))) {
            return "";
        }
        String safeContent = content.replaceAll("反恐", "FK").replaceAll("一级临控", "YJLK").replaceAll("二级临控", "EJLK").replaceAll("三级临控", "SJLK");
        return safeContent;
    }

    //姓名过滤
    public static String getSafeName(String name) {
        if ("".equals(safeToString(name))) return "";
        String safeName = name;
        if (name.length() == 2) {
            safeName = name.substring(0, 1) + "*";
        } else if (name.length() == 3) {
            safeName = name.substring(0, 1) + "*" + name.substring(2);
        } else if (name.length() == 4) {
            safeName = name.substring(0, 2) + "*" + name.substring(3);
        } else if (name.length() > 4) {
            safeName = name.substring(0, 2) + "**" + name.substring(4);
        }
        return safeName;
    }

    //身份证过滤
    public static String getSafeSfzh(String sfzh) {
        if ("".equals(safeToString(sfzh))) return "";
        String safeSfzh = sfzh;
        if (sfzh.length() > 6) {
            safeSfzh = sfzh.substring(0, sfzh.length() - 4) + "****";
        }
        return safeSfzh;
    }

    public static void updateShell() throws Exception {
        logger.info("updateShell begin...");
        try {
            String command = "hds -n simmgr user update";
            Process ps = Runtime.getRuntime().exec(command);
            InputStreamReader ir = new InputStreamReader(ps.getInputStream());
            BufferedReader input = new BufferedReader(ir);
            String line;
            while ((line = input.readLine()) != null) {
                logger.info(line);
            }
        } catch (Exception ex) {
            logger.info("updateShell error:" + ex.getMessage());
            logger.error(ex.getMessage());
            throw ex;
        }
        logger.info("updateShell end...");
    }

    /**
     * 判断日期字符串是否为当天日期
     **/
    public static boolean checkDateStrInCurDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = sdf.parse(dateStr);
            return newDate.getDate() == new Date().getDate();
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 字符串日期获取日期各成分
     ***/
    public static String strToDateParas(String dateStr, String whichPara) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = new Date();
        try {
            newDate = sdf.parse(dateStr);
        } catch (Exception ex) {
        }
        Calendar c = Calendar.getInstance();
        c.setTime(newDate);
        String result = "";
        if (whichPara.equals("yyyy")) {
            result = c.get(Calendar.YEAR) + "";
        } else if (whichPara.equals("MM")) {
            result = (c.get(Calendar.MONTH) + 1) + "";
        } else if (whichPara.equals("dd")) {
            result = c.get(Calendar.DAY_OF_MONTH) + "";
        } else if (whichPara.equals("HH")) {
            result = c.get(Calendar.HOUR_OF_DAY) + "";
        } else if (whichPara.equals("mi")) {
            result = c.get(Calendar.MINUTE) + "";
        } else if (whichPara.equals("ss")) {
            result = c.get(Calendar.SECOND) + "";
        }
        return result;
    }

    public static String encryptByMd5(String plaintext) {
        String ciphertext = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(plaintext.getBytes("UTF-8"));
            byte[] bArr = md.digest();
            StringBuilder sbd = new StringBuilder();
            int tmp;
            for (int i = 0; i < bArr.length; i++) {
                tmp = bArr[i];
                if (tmp < 0) {
                    tmp += 256;
                }
                if (tmp < 16) {
                    sbd.append('0');
                }
                sbd.append(Integer.toHexString(tmp));
            }
            ciphertext = sbd.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ciphertext.toUpperCase();
    }

    /**
     * 字符串时间标准化转换
     * yyyy-MM-dd HH:mm
     **/
    public static String strToYYYYMMDD(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        try {
            Date date = sdf.parse(dateStr);
            str = sdf.format(date);
        } catch (Exception ex) {
            str = sdf.format(new Date());
        }
        return str;
    }

    /**
     * 字符串时间标准化转换
     * yyyy-MM-dd HH:mm
     **/
    public static String strToYYYYMMDD_HHMM(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = "";
        try {
            Date date = sdf.parse(dateStr);
            str = sdf.format(date);
        } catch (Exception ex) {
            str = sdf.format(new Date());
        }
        return str;
    }

    /**
     * 字符串时间标准化转换
     * yyyy-MM-dd HH:mm:ss
     **/
    public static String strToYYYYMMDD_HHMMSS(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = "";
        try {
            Date date = sdf.parse(dateStr);
            str = sdf.format(date);
        } catch (Exception ex) {
            str = sdf.format(new Date());
        }
        return str;
    }

    /**
     * 字符串时间标准化转换
     * yyyy-MM-dd HH:mm:ss
     **/
    public static Date strToDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (Exception ex) {
            return new Date();
        }
    }

    /**
     * 字符串时间标准化转换
     * yyyy-MM-dd HH:mm:ss
     **/
    public static String dateToYYYYMMDD_HHMMSS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = "";
        try {
            str = sdf.format(date);
        } catch (Exception ex) {
            str = sdf.format(new Date());
        }
        return str;
    }

    /**
     * 字符串时间标准化转换
     * yyyy-MM-dd HH:mm:ss
     **/
    public static String strToFormatDateStr(String dateStr, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (formatStr.trim().equals("")) {
            formatStr = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatSdf = new SimpleDateFormat(formatStr);
        String str = "";
        try {
            Date date = sdf.parse(dateStr);
            str = formatSdf.format(date);
        } catch (Exception ex) {
            str = formatSdf.format(new Date());
        }
        return str;
    }


    /**
     * 字符串时间标准化转换
     * yyyy-MM-dd HH:mm:ss
     *
     * @dateStr 目标时间串
     * @formatStr 时间格式
     * @interval 时间差
     **/
    public static String strToFormatDateStr(String dateStr, String formatStr, int interval) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (CommonUtils.safeToString(formatStr).trim().equals("")) {
            formatStr = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatSdf = new SimpleDateFormat(formatStr);
        String str = "";
        try {
            Date date = sdf.parse(dateStr);
            str = formatSdf.format(date);
        } catch (Exception ex) {
            str = formatSdf.format(getIntervalHour(new Date(), interval));
        }
        return str;
    }

    /**
     * 标准化字符串转换
     **/
    public static String safeToString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 标准化整型值转换
     **/
    public static int safeToInt(Object obj, int def) {
        int result = def;
        try {
            if (obj == null) return def;
            result = Integer.parseInt(obj.toString());
        } catch (Exception ex) {
            result = def;
        }
        return result;
    }

    /**
     * 标准化Double值转换
     **/
    public static double safeToDouble(Object obj, double def) {
        double result = def;
        try {
            if (obj == null) return def;
            result = Double.parseDouble(obj.toString());
        } catch (Exception ex) {
            result = def;
        }
        return result;
    }

    /**
     * 验证session有效性
     **/
    public static boolean checkSessionValid(HttpServletRequest request) {
        String userSession = "";
        if (request.getSession() != null) {
            userSession = CommonUtils.safeToString(request.getSession().getAttribute("user"));
        }
        if (userSession == null || "".equals(userSession.trim())) {
            return false;
        }
        return true;
    }

    /**
     * base64转换
     **/
    public static String getFormatPubKey(String pubkey) {
        try {
            StringBuilder result = new StringBuilder();
            byte[] pubkeybyte = Base64.decode(pubkey);
            for (int i = 0; i < pubkeybyte.length; i++) {
                result.append(String.valueOf(pubkeybyte[i]) + ",");
            }
            return result.toString();
        } catch (Exception ex) {
            return pubkey;
        }
    }

    /**
     * 标准化long值转换
     **/
    public static long safeToLong(Object obj, long def) {
        long result = def;
        try {
            if (obj == null || "".equals(obj.toString().trim())) {
                return result;
            }
            result = Long.parseLong(obj.toString());
        } catch (Exception ex) {
            result = def;
        }
        return result;
    }

    //日期转字符串
    public static String formatTimeToString(Date date, String format) {
        String result = "";
        try {
            if (format == null || "".equals(format.trim())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                result = sdf.format(date);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            }
        } catch (Exception ex) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = sdf.format(date);
        }
        return result;
    }

    /**
     * @param curDate
     * @param interval
     **/
    public static Date getIntervalHour(Date curDate, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.HOUR_OF_DAY, interval);
        return cal.getTime();
    }

    /**
     * @param curDate
     * @param interval
     **/
    public static Date getIntervalTime(Date curDate, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.DATE, interval);
        return cal.getTime();
    }

    /**
     * @param curDate
     * @param interval
     **/
    public static Date getIntervalYear(Date curDate, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.YEAR, interval);
        return cal.getTime();
    }

    /**
     * @param curDate
     * @param interval
     **/
    public static Date getIntervalMonth(Date curDate, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.MONTH, interval);
        return cal.getTime();
    }

    /**
     * 将用英文逗号拼接的字符串添加双引号："111,222,333" -> "'111','222','333'"
     */
    public static String formatQuoteString(String str) {
        if (str == null) return "";
        if (str.indexOf(",") > 0) {
            String[] tokens = str.split(",");
            StringBuilder result = new StringBuilder();
            for (String s : tokens) {
                if (!"".equals(safeToString(s))) {
                    result.append("'" + s + "',");
                }
            }
            return result.length() > 0 ? result.substring(0, result.length() - 1) : "";
        } else {
            return "'" + str + "'";
        }
    }

    /**
     * 校验手机号码是否有效
     ***/
    public static boolean isValidPhoneNum(String num) {
        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        if (num == null || num.trim().equals("")) {
            return false;
        }
        return num.matches(expression);
    }

    /**
     * 十六进制转二进制
     *
     * @return 二进制数
     * @hexStr 十六进制数
     **/
    public static String hexToBinary(String hexStr) {
        String binary = "";
        try {
            int decimal = Integer.parseInt(hexStr, 16);
            binary = Integer.toBinaryString(decimal);
        } catch (Exception ex) {
            binary = hexStr;
        }
        return binary;
    }

    /**
     * 十六进制逻辑与运算(暂时最大支持46位)
     *
     * @return 十六进制数
     * @hexStr1 十六进制数1
     * @hexStr2 十六进制数2
     **/
    public static String hexAnd(String hexStr1, String hexStr2) {
        StringBuilder sb = new StringBuilder(hexStr1.length() * 2);
        try {
            for (int i = 0; i < hexStr1.length() && i < 46; i++) {
                char s1 = hexStr1.charAt(i);
                char s2 = hexStr2.charAt(i);
                int int1 = Integer.parseInt(String.valueOf(s1), 16);
                int int2 = Integer.parseInt(String.valueOf(s2), 16);
                int intResult = int1 & int2;
                sb.append(Integer.toHexString(intResult));
            }
        } catch (Exception ex) {
            sb = new StringBuilder();
            sb.append(hexStr1);
        }
        if (hexStr1.length() > 46) {
            sb.append(hexStr1.substring(46));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 获取上周一的日期
     **/
    public static String getLastMonday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1 * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return monday;
    }

    public static boolean checkStrInTokens(String tokens, String str) {
        if (CommonUtils.safeToString(tokens).trim().equals("")
                || CommonUtils.safeToString(str).trim().equals("")) {
            return false;
        } else {
            ConcurrentHashMap map = new ConcurrentHashMap();
            String[] token = tokens.split(",");
            for (String t : token) {
                if (!CommonUtils.safeToString(t).trim().equals("")) {
                    map.put(CommonUtils.safeToString(t).trim(), CommonUtils.safeToString(t).trim());
                }
            }
            return map.containsKey(CommonUtils.safeToString(str).trim());
        }
    }

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }

    public static Map<?, ?> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (Exception e) {
                logger.error("objectToMap error : " + e.getMessage());
            }
            map.put(fieldName, value);
        }
        return map;
    }

    public static String getVString(String VString, String depttype) {
        if (VString == null) VString = "";
        String[] Vss = VString.split("\\|");

        String sReturn = "";
        for (int i = 0; i < Vss.length; i++) {
            if (!Vss[i].trim().equals("")) {
                String[] ss = Vss[i].split(":");
                if (ss.length >= 1) {
                    String[] ss2 = ss[0].split(",");
                    for (int j = 0; j < ss2.length; j++) {
                        if (ss2[j].trim().equals("0")) {
                            sReturn = "";
                            if (ss.length >= 2) sReturn = ss[1].trim();
                        }
                        if (ss2[j].trim().equals(depttype)) {
                            sReturn = "";
                            if (ss.length >= 2) sReturn = ss[1].trim();
                            return sReturn;
                        }
                    }
                }
            }
        }
        return sReturn;
    }

    //判断字符串是否为空
    public static boolean isNullorEmpty(String str) {

        if (str == null || str.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    //判断列表数据是否完整
    public static boolean isNotComplete(List list, int requiredNum) {

        if (list == null || list.size() < requiredNum) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * 判断一个实体类对象实例的所有成员变量是否为空
     *
     * @param obj 校验的类对象实例
     * @return List
     * @throws Exception
     */

    public static List<String> isObjectFieldEmpty(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();  //得到类对象
        Field[] fs = clazz.getDeclaredFields(); //得到属性集合
        List<String> list = new ArrayList<String>();
        for (Field field : fs) {            //遍历属性
            field.setAccessible(true); //设置属性是可以访问的（私有的也可以）
            if (field.get(obj) == null || field.get(obj) == "" || "null".equalsIgnoreCase(field.get(obj).toString())) {
                String name = (String) field.getName();
                list.add(name);
            }
        }
        return list;
    }
    //根据月份获取一个月的天数

    /**
     * 获取某个月份天数
     */
    public static int getDaysOfMonth(String monthStr) {
        Date monthDate = DateUtils.getMonthDate(monthStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthDate);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String changeNullToEmpty(String str) {
        return isNullorEmpty(str) ? "" : str;
    }

}
