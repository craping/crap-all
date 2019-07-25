package org.crap.jrain.core.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.crap.jrain.core.bean.DateFormat;

/** 
 * @project Crap
 * 
 * @author Crap
 * 
 * @Copyright 2013 - 2014 All rights reserved. 
 * 
 * @email 422655094@qq.com
 * 
 */
public class StringUtil {
	
	public static String[] regex= {
		//email
		"^([a-zA-Z0-9_\\-\\.\\+]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$",
		//mobile
		"^0?(13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$",
		//phone
		"^0(([1-2]\\d)|[4-9]\\d{2})[\\s\\.\\-]?[2-8]\\d{5,7}$",
	};
	/**
	 * 判断是否为 +整数
	 * @author Crap
	 * @param str
	 * @return boolean
	 */
	public static boolean isPositiveInteger(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0     
        // 4 代表长度为4     
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }
	
	
	public static String getCode(int passLength, int type)  
    {  
        StringBuffer buffer = null;  
        StringBuffer sb = new StringBuffer();  
        Random r = new Random();  
        r.setSeed(new Date().getTime());  
        switch (type)  
        {  
        case 0:  
            buffer = new StringBuffer("0123456789");  
            break;  
        case 1:  
            buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyz");  
            break;  
        case 2:  
            buffer = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");  
            break;  
        case 3:  
            buffer = new StringBuffer(  
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");  
            break;  
        case 4:  
            buffer = new StringBuffer(  
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");  
            sb.append(buffer.charAt(r.nextInt(buffer.length() - 10)));  
            passLength -= 1;  
            break;  
        case 5:  
            String s = UUID.randomUUID().toString();  
            sb.append(s.substring(0, 8) + s.substring(9, 13)  
                    + s.substring(14, 18) + s.substring(19, 23)  
                    + s.substring(24));  
        }  
  
        if (type != 5)  
        {  
            int range = buffer.length();  
            for (int i = 0; i < passLength; ++i)  
            {  
                sb.append(buffer.charAt(r.nextInt(range)));  
            }  
        }  
        return sb.toString();  
    }
	/**
	 * 判断是否为 ±整数
	 * @author Crap
	 * @param str
	 * @return boolean
	 */
	public static boolean isInteger(String str) {
		int index = str.indexOf("-");
		if(index > -1){
			if(index != 0)
				return false;
			return isPositiveInteger(str.substring(1));
		}
		return isPositiveInteger(str);
	}

	/**
	 * 判断是否为 +数
	 * @author Crap
	 * @param str
	 * @return boolean
	 */
	public static boolean isPositiveNumber(String str) {
		int index = str.indexOf(".");
		if (index < 0) {
			return isPositiveInteger(str);
		} else {
			String num1 = str.substring(0, index);
			String num2 = str.substring(index + 1);
			return isPositiveInteger(num1) && isPositiveInteger(num2);
		}
	}
	
	/**
	 * 判断是否为 ±数
	 * @author Crap
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
		int index = str.indexOf(".");
		if (index < 0) {
			return isInteger(str);
		} else {
			String num1 = str.substring(0, index);
			String num2 = str.substring(index + 1);
			return isInteger(num1) && isPositiveInteger(num2);
		}
	}

	/**
	 * 判断是否为日期
	 * @author Crap
	 * @param str
	 * @param dateFormat 日期格式
	 * @return boolean
	 */
	public static boolean isDate(String str, String dateFormat) {
		if (str != null) {
			/*SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			formatter.setLenient(false);
			try {
				formatter.format(formatter.parse(str));
			} catch (Exception e) {
				return false;
			}*/
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			formatter.setLenient(false);
			try {
				formatter.parse(str);
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为日期
	 * @author Crap
	 * @param str
	 * @param dateFormat 日期格式枚举值 {@link DateFormat}
	 * @return boolean
	 */
	public static boolean isDate(String str, DateFormat dateFormat) {
		return isDate(str, dateFormat.toString());
	}
	
	/**
	 * 判断字符串是否符合正则表达式
	 * @author Crap
	 * @param str 需要验证的字符串
	 * @param regex 正则表达式规则
	 * @return boolean
	 */
	/*public static boolean matcher(String str, String regex){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.matches();
	}*/

	public static boolean isEmail(String str) {
		String regex = "^([a-zA-Z0-9_\\-\\.\\+]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return str.matches(regex);
	}

	public static boolean isPhone(String str) {
		String[] regex = new String[] {
			"^0(([1-2]\\d)|[4-9]\\d{2})[\\s\\.\\-]?[2-8]\\d{5,7}$",
			"^400(\\d){7,7}$",
			"^400(\\d){7,7}(\\+[0-9]{1,4})?$" 
		};
		for (int i = 0; i < regex.length; i++) {
			if (str.matches(regex[i]))
				return true;
		}
		return false;
	}

	public static boolean isMobile(String str) {
		String regex = "^0?(13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$";
		return str.matches(regex);
	}

	public static boolean isUsername(String str) {
		String regex = "^[a-zA-Z0-9_]{3,20}$";
		return str.matches(regex);
	}

	public static boolean isPassword(String str) {
		String regex = "^[a-zA-Z0-9]{3,20}$";
		return str.matches(regex);
	}
	
	/**
	 * 分割字符串
	 * 如果参数 delimiter 不存在 str 中则返回 str
	 * 如果参数 delimiter 存在 str 中
	 * 		split(",1", ","); 返回 ["", "1"]
	 * 		split("1,", ","); 返回 ["1", ""]
	 * @author Crap
	 * @param str 需要分割的字符串
	 * @param delimiter 分隔符
	 * @return String[]
	 */
	public static String[] split(String str, String delimiter){  
	    if (str == null) {  
	        return null;
	    }  
	    int delimiterLength;  
	    int stringLength = str.length();  
	    if (delimiter == null || (delimiterLength = delimiter.length()) == 0){  
	        return new String[] {str};  
	    }  
	    //select (select name from a) name,123, a,b from b where aa in (select name from bb where name='aa') and aa = (select a from c)
	    // a two pass solution is used because a one pass solution would  
	    // require the possible resizing and copying of memory structures  
	    // In the worst case it would have to be resized n times with each  
	    // resize having a O(n) copy leading to an O(n^2) algorithm.  
	  
	    int count;  
	    int start;  
	    int end;  
	  
	    // Scan s and count the tokens.  
	    count = 0;  
	    start = 0;  
	    while((end = str.indexOf(delimiter, start)) != -1){  
	        count++;  
	        start = end + delimiterLength;  
	    }  
	    count++;  
	  
	    // allocate an array to return the tokens,  
	    // we now know how big it should be  
	    String[] result = new String[count];  
	  
	    // Scan s again, but this time pick out the tokens  
	    count = 0;  
	    start = 0;  
	    while((end = str.indexOf(delimiter, start)) != -1){  
	        result[count] = (str.substring(start, end));  
	        count++;  
	        start = end + delimiterLength;  
	    }  
	    end = stringLength;  
	    result[count] = str.substring(start, end);  
	  
	    return (result);  
	}
	
	/**
	 * 匹配字符出现次数
	 * 
	 * @author Crap
	 * @param str 字符串
	 * @param subStr 需要匹配的字符串
	 * @return int
	 */
	public static int countMatches(String str, String subStr) {
        if ((str == null) || (str.length() == 0) || (subStr == null) || (subStr.length() == 0)) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }

        return count;
    }
	
	/**
	 * 匈牙利转驼峰命名法
	 * @author Crap
	 * @param str 字符串
	 * @return String
	 */
	public static String toCamelCase(String str){
		String name = "";
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			
			name += c == '_'?Character.toUpperCase(chars[i==chars.length-1?i:++i]):c;

		}
		return name;
	}
	
	
	/**
	 * 驼峰转匈牙利命名法
	 * @author Crap
	 * @param str 字符串
	 * @return String
	 */
	public static String toHungarian(String str){
		String name = "";
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			name += Character.isUpperCase(c)?(i==0?c:"_"+c):c;
		}
		return name.toLowerCase();
	}
	/**
	 * 拼接数组输出
	 * a 为 null 返回 "null" 字符串
	 * @author Crap
	 * @param str 字符串
	 * @param subStr 需要匹配的字符串
	 * @return int
	 */
	public static String toString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax)
                return b.toString();
        }
    }
	
	/**
	 * 判断字符串是否为空
	 * @author xuhong
	 * @param str 字符串
	 * @return true/false
	 */
	public static boolean isBlank(String str){
		if(str == null || str.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static String uuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	  
	/**  
	* @Title: decodeParams  
	* @Description: url参数转map
	* @param @param s
	* @param @param from
	* @param @param charset
	* @param @param paramsLimit
	* @param @return    参数  
	* @return Map<String,String>    返回类型  
	* @throws  
	*/  
	    
	public static Map<String, Object> decodeParams(String s) {
        int len = s.length();
        if (len < 2) {
            return Collections.emptyMap();
        }
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        int nameStart = 0;
        int valueStart = -1;
        int i;
        loop:
        for (i = 0; i < len; i++) {
            switch (s.charAt(i)) {
            case '=':
                if (nameStart == i) {
                    nameStart = i + 1;
                } else if (valueStart < nameStart) {
                    valueStart = i + 1;
                }
                break;
            case '&':
            case ';':
                addParam(s, nameStart, valueStart, i, params);
                nameStart = i + 1;
                break;
            case '#':
                break loop;
            default:
                // continue
            }
        }
        addParam(s, nameStart, valueStart, i, params);
        return params;
    }
	
	private static boolean addParam(String s, int nameStart, int valueStart, int valueEnd, Map<String, Object> params) {
		if (nameStart >= valueEnd) {
			return false;
		}
		if (valueStart <= nameStart) {
			valueStart = valueEnd + 1;
		}
		String name = s.substring(nameStart, valueStart - 1);
		String value = s.substring(valueStart, valueEnd);
		params.put(name, value);
		return true;
	}
	
	public static List<String> analysisClass(Object E) {
		List<String> eList = new ArrayList<>();
		
		Field[] fields = E.getClass().getFields();
		for (Field field : fields) {
//			if(!field.getType().isPrimitive())
//				continue;
			try {
				eList.add(field.get(E).toString());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return eList;
	}
	
	public static List<String> analysisClassName(Object E) {
		List<String> eList = new ArrayList<>();
		
		Field[] fields = E.getClass().getFields();
		for (Field field : fields) {
//			if(!field.getType().isPrimitive())
//				continue;
			eList.add(field.getName());
		}
		
		return eList;
	}
	
	public static Map<String, String> analysisClassKY(Object E) {
		Map<String, String> eMap = new HashMap<>();
		
		Field[] fields = E.getClass().getFields();
		for (Field field : fields) {
//			if(!field.getType().isPrimitive())
//				continue;
			try {
				eMap.put(field.getName(), field.get(E).toString());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return eMap;
	}
	public static void main(String[] args) {
		/*long startTime, endTime;

		startTime = System.nanoTime();
		System.out.println("isInteger:" + isInteger("-001"));
		endTime = System.nanoTime();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		
		startTime = System.nanoTime();
		System.out.println("isNumber:" + isNumber("-30.1"));
		endTime = System.nanoTime();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		
		startTime = System.nanoTime();
		System.out.println("isPositiveNumber:" + isPositiveInteger("-0.01"));
		endTime = System.nanoTime();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");*/
		
		
		/*String ip ="192,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121,168,20,121";
		long st1 = System.nanoTime();  
		String[] ips =ip.split(",");  
		System.out.println("使用jdk的split切分字符串");  
		for (int i = 0; i < ips.length; i++) {  
		 System.out.print(ips[i]+"  ");  
		}  
		System.out.println("花费时间"+(System.nanoTime()-st1));  
		  
		  
		long st2 = System.nanoTime();  
		ips= StringUtils.split(ip,",");  
		System.out.println("使用common的split切分字符串");  
		for (int i = 0; i < ips.length; i++) {  
		 System.out.print(ips[i]+"  ");  
		}  
		System.out.println("花费时间"+(System.nanoTime()-st2));  
		  
		long st3 = System.nanoTime();  
		System.out.println("使用StringTokenizer的切分字符串");  
		StringTokenizer token=new StringTokenizer(ip,",");  
		while(token.hasMoreElements()){  
		 System.out.print(token.nextToken()+"  ");  
		}  
		System.out.println("花费时间"+(System.nanoTime()-st3));  
		  
		long st4 = System.nanoTime();  
		Pattern pattern = Pattern.compile(",");  
		ips =pattern.split(ip);  
		System.out.println("使用jdk的pattern切分字符串");  
		for (int i = 0; i < ips.length; i++) {  
		 System.out.print(ips[i]+"  ");  
		}  
		System.out.println("花费时间"+(System.nanoTime()-st4));  
		  
		long st5 = System.nanoTime();  
		System.out.println("使用jdk的indexOf切分字符串");  
		int k=0,count=0;  
		StringBuilder sb = new StringBuilder();  
		for (int i = 0; i < ip.length(); i++) {  
		 if(ip.substring(i, i+1).equals(",")){  
		  if(count==0){  
		   System.out.print(ip.substring(0, i)+"  ");  
		  }else if(count==1){  
		   System.out.print(ip.substring(k+1, i)+"  ");  
		  }else{  
		   System.out.print(ip.substring(k+1, i)+"  ");  
		   System.out.print(ip.substring(i+1, ip.length())+"  ");  
		  }  
		  k=i;count++;  
		 }  
		}  
		System.out.println("花费时间"+(System.nanoTime()-st5));
		
		long st6 = System.nanoTime();   
		ips =split(ip, ",");  
		System.out.println("自定义split切分字符串");  
		for (int i = 0; i < ips.length; i++) {  
		 System.out.print(ips[i]+"  ");  
		}
		System.out.println("花费时间"+(System.nanoTime()-st6));*/
		System.out.println("I-CH" +StringUtil.getCode(3, 4)+System.currentTimeMillis());
		/*System.out.println(countMatches(",,,,,,,,", ","));
		System.out.println(isDate("2014-2-28 23:45:1","yyyy-MM-dd HH:mm:ss"));
		System.out.println("1,".split(",").length);
		System.out.println(toCamelCase("tbl_product_type"));
		System.out.println(toHungarian("AccountRebates"));
		System.out.println(uuid());*/
		System.out.println(StringUtil.decodeParams("a"));
	}
}
