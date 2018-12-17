package com.gwghk.agora.video.agoravideo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 摘要：字符串工具类
 * @author   Gavin.guo
 * @version  1.0
 * @Date	 2016年7月25日
 */
public class StringUtil extends StringUtils{
	private static final String EMPTY_STRING = "";
	private static final char[] RANDOM_CHARS = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ@._()".toCharArray();

	/**
	 * 空的String数组
	 */
	public static final String[] EMPTY_ARRAY = new String[0];

	/**
	 * 生成指定长度的随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String generateRandomString(int length) {
		return generateRandomString(RANDOM_CHARS, length);
	}

	/**
	 * 获取字符串数据
	 * 
	 * @param
	 * @return
	 */
	public static String getStringValue(String obj) {
		if (null == obj) {
			return OConstants.EMPTY_STRING;
		} else {
			return obj;
		}
	}

	/**
	 * 生成指定长度指定范围的随机字符串
	 * 
	 * @param randChars
	 * @param length
	 * @return
	 */
	public static String generateRandomString(char[] randChars, int length) {
		if (length < 1) {
			return null;
		}
		Random rand = new Random();
		int rLen = randChars.length;
		char[] newStr = new char[length];
		for (int i = 0; i < length; i++) {
			newStr[i] = randChars[rand.nextInt(rLen)];
		}
		return new String(newStr);
	}

	/**
	 * 生成全局唯一值
	 * 
	 * @return
	 */
	public static String generateGUID() {
		return replaceWithBlank(UUID.randomUUID().toString(), '-');
	}

	/**
	 * 将字符串中的特定字符替换为空（即去掉此字符）
	 * 
	 * @param
	 * @return
	 */
	public static String replaceWithBlank(String value, char oldChar) {
		if (null == value || value.length() == 0) {
			return value;
		}

		int len = value.length();
		int index = 0;
		char[] newValue = new char[len];
		for (int i = 0; i < len; i++) {
			char currentChar = value.charAt(i);
			if (currentChar != oldChar) {
				newValue[index++] = currentChar;
			}
		}

		return new String(newValue, 0, index);
	}

	/**
	 * 将字节数组转换为16进制形式
	 * 
	 * @param input
	 * @return
	 */
	public static String toHex(byte[] input) {
		if (input == null)
			return null;
		StringBuilder output = new StringBuilder(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			// 小于16的需要补充一位(共2位)
			if (current < 16) {
				output.append('0');
			}

			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 * 填充指定字符到字符串左边到指定长度
	 * 
	 * @param orignalValue
	 * @param padChar
	 *            需要填充的字符
	 * @param length
	 *            填充后的长度
	 * @return
	 */
	public static String padLeft(String orignalValue, char padChar, int length) {
		return pad(orignalValue, padChar, length, true);
	}

	/**
	 * 填充指定字符到字符串右边到指定长度
	 * 
	 * @param orignalValue
	 * @param padChar
	 *            需要填充的字符
	 * @param length
	 *            填充后的长度
	 * @return
	 */
	public static String padRight(String orignalValue, char padChar, int length) {
		return pad(orignalValue, padChar, length, false);
	}

	/**
	 * 填充指定字符到字符串的指定位置知道达到指定长度
	 * 
	 * @param orignalValue
	 * @param padChar
	 *            需要填充的字符
	 * @param length
	 *            填充后的长度
	 * @param isLeft
	 *            是否填充在左边
	 * @return
	 */
	public static String pad(String orignalValue, char padChar, int length, boolean isLeft) {
		// null作为空字符串
		if (null == orignalValue) {
			orignalValue = EMPTY_STRING;
		}

		// 判断长度是否比需要的长度短，只有在短的情况下才会进行处理
		int oldLen = orignalValue.length();
		if (oldLen >= length) {
			return orignalValue;
		} else {
			int padLen = length - oldLen;
			// 得到需要添加的字符
			char[] appendChars = new char[padLen];
			Arrays.fill(appendChars, padChar);

			char[] newChars = new char[length];
			char[] orignalChars = orignalValue.toCharArray();
			if (isLeft) {
				System.arraycopy(appendChars, 0, newChars, 0, padLen);
				System.arraycopy(orignalChars, 0, newChars, padLen, oldLen);
			} else {
				System.arraycopy(orignalChars, 0, newChars, 0, oldLen);
				System.arraycopy(appendChars, 0, newChars, oldLen, padLen);
			}

			return new String(newChars);
		}
	}

	/**
	 * 判读是否不为空
	 */
	public static boolean isNotEmpty(String value) {
		return !isNullOrEmpty(value);
	}

	/**
	 * 验证字符串是否为空或null
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(String value) {
		return isNullOrEmpty(value, true);
	}

	/**
	 * 验证字符串是否为空或null
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(String value, boolean trim) {
		int len;
		if (null == value || (len = value.length()) == 0) {
			return true;
		}

		if (!trim) {
			return false;
		}

		for (int i = 0; i < len; i++) {
			if (value.charAt(i) != ' ') {
				return false;
			}
		}

		return true;
	}

	public static String subString(String text, int start, int length) {
		if (null == text || text.length() == 0) {
			return EMPTY_STRING;
		}

		int endPos = text.length();
		// 开始位置大于总长度，则返回空
		if (start >= endPos) {
			return EMPTY_STRING;
		}

		//
		if (start + length > endPos) {
			length = endPos - start;
		}

		return text.substring(start, start + length);
	}

	/**
	 * 按照指定长度进行截取
	 * 
	 * @param
	 * @return
	 */
	public static String getStringInLength(String value, int length) {
		if (StringUtil.isNullOrEmpty(value) || value.length() <= length) {
			return value;
		} else {
			return value.substring(0, length);
		}
	}

	public static long getIPNumber(String clientIP) {
		String[] ipSecs = split(clientIP, '.');
		if (null == ipSecs || ipSecs.length < 4) {
			return 0;
		}

		return (Long.parseLong(ipSecs[0]) << 24) + (Long.parseLong(ipSecs[1]) << 16) + (Long.parseLong(ipSecs[2]) << 8)
				+ Long.parseLong(ipSecs[3]);
	}

	/**
	 * 将字符串中的指定旧值替换成指定新值
	 * 
	 * @param input
	 * @param oldValue
	 *            被替换的旧值
	 * @param newValue
	 *            替换成的新值
	 * @return
	 */
	public static String replaceLast(String input, String oldValue, String newValue) {
		int lIndex = input.lastIndexOf(oldValue);
		if (lIndex < 0) {
			return input;
		}

		StringBuilder sb = new StringBuilder(input.length() - oldValue.length() + newValue.length());
		sb.append(input.substring(0, lIndex));
		sb.append(newValue);
		sb.append(input.substring(lIndex + oldValue.length()));

		return sb.toString();
	}

	/**
	 * 将字符串中的指定旧值替换成指定新值
	 *
	 * @param input
	 *            原字符串
	 * @param oldValues
	 *            被替换的旧值列表
	 * @param newValues
	 *            替换成的新值列表
	 * @return
	 */
	public static String replaceChars(String input, String[] oldValues, String[] newValues) {

		// 参数为空则返回原字符串
		if (StringUtil.isNullOrEmpty(input) || oldValues == null || newValues == null) {
			return input;
		}

		int size = oldValues.length;
		if (newValues.length != size) {
			return input;
		}

		for (int i = 0; i < size; i++) {
			input = input.replaceAll(oldValues[i], newValues[i]);
		}

		return input;
	}

	/**
	 * 将字符串中的指定旧值替换成指定新值
	 *
	 * @param input
	 *            原字符串
	 * @param oldValue
	 *            被替换的旧值
	 * @param newValue
	 *            替换成的新值
	 * @return
	 */
	public static String replaceChar(String input, String oldValue, String newValue) {

		// 参数为空则返回原字符串
		if (StringUtil.isNullOrEmpty(input) || StringUtil.isNullOrEmpty(oldValue)
				|| StringUtil.isNullOrEmpty(newValue)) {
			return input;
		}

		return input.replaceAll(oldValue, newValue);
	}

	/**
	 * 直接针对String的split，不支持正则表达式
	 * 
	 * @param
	 * @return
	 */
	public static String[] split(String value, char splitChar) {
		if (null == value) {
			return null;
		}

		int len = value.length();
		if (len == 0) {
			return EMPTY_ARRAY;
		}

		int lastFromIndex = 0; // 最近一次查询的其实位置
		int index;
		ArrayList<String> result = new ArrayList<String>();
		while ((index = value.indexOf(splitChar, lastFromIndex)) != -1) {
			if (lastFromIndex != index) {
				result.add(value.substring(lastFromIndex, index));
			}

			lastFromIndex = index + 1;
		}

		if (lastFromIndex >= 0 && lastFromIndex != len) {
			result.add(value.substring(lastFromIndex));
		}

		return result.toArray(new String[result.size()]);
	}

	/**
	 * 直接针对String的split，不支持正则表达式
	 * 
	 * @param
	 * @return
	 */
	public static String[] split(String value, String splitString) {
		if (null == value) {
			return null;
		}

		int len = value.length();
		if (len == 0) {
			return EMPTY_ARRAY;
		}

		int splitStringLen = splitString.length();
		if (splitStringLen == 0) {
			return new String[] { value };
		}

		int lastFromIndex = 0; // 最近一次查询的其实位置
		int index;
		ArrayList<String> result = new ArrayList<String>();
		while ((index = value.indexOf(splitString, lastFromIndex)) != -1) {
			if (lastFromIndex != index) {
				result.add(value.substring(lastFromIndex, index));
			}

			lastFromIndex = index + splitStringLen;
		}

		if (lastFromIndex >= 0 && lastFromIndex != len) {
			result.add(value.substring(lastFromIndex));
		}

		return result.toArray(new String[result.size()]);
	}

	/**
	 * 获取截断后的字符串
	 * 
	 * @param original
	 *            原始字符串
	 * @param maxLen
	 *            截断后的最大长度
	 * @return
	 */
	public static final String getShortString(String original, int maxLen) {
		if (null == original) {
			return "";
		}

		if (original.length() > maxLen) {
			return original.substring(0, maxLen);
		}

		return original;
	}

	public static final int getCommonStrLength(String str1, String str2) {
		if (str1 == null || str2 == null) {
			return 0;
		}
		str1 = str1.toLowerCase();
		str2 = str2.toLowerCase();
		int len1 = str1.length();
		int len2 = str2.length();
		String min = null;
		String max = null;
		String target = null;
		min = len1 <= len2 ? str1 : str2;
		max = len1 > len2 ? str1 : str2;

		// 最外层：min子串的长度，从最大长度开始
		for (int i = min.length(); i >= 1; i--) {
			// 遍历长度为i的min子串，从0开始
			for (int j = 0; j <= min.length() - i; j++) {
				target = min.substring(j, j + i);
				// 遍历长度为i的max子串，判断是否与target子串相同，从0开始
				for (int k = 0; k <= max.length() - i; k++) {
					if (max.substring(k, k + i).equals(target)) {
						return i;
					}
				}
			}
		}
		return 0;
	}

	/**
	 * 方法名称:transMapToString 传入参数:map 返回值:String 形如 形如
	 * username:chenziwen;password:1234
	 */
	public static String transMapToString(Map<String, String> map, boolean isSort) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(map.keySet());
		if (isSort) {
			Collections.sort(keys);
		}
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key).toString();
			sb.append(key + ":" + value);
			sb.append(";");
		}
		String str = sb.toString();
		if (str.endsWith(";")) {
			str = str.substring(0, str.lastIndexOf(";"));
		}
		return str;
	}

	/**
	 * 方法名称:transStringToMap 传入参数:mapString 形如 username:chenziwen;password:1234
	 * 返回值:Map
	 */
	public static Map<String, String> transStringToMap(String string) {
		Map<String, String> map = new HashMap<String, String>();
		if (isNullOrEmpty(string)) {
			return map;
		}
		String[] params = string.split(";");
		for (int i = 0; i < params.length; i++) {
			String[] str = params[i].split(":");
			if (str.length == 2) {
				map.put(str[0], str[1]);
			}
		}
		return map;
	}
	
	/**
	 * 转成模糊匹配形式的字符串
	 * @param value
	 */
	public static String toFuzzyMatch(String value){
		return ".*?"+value+".*";
	}
	
	/**
	 * 功能：随即生成指定位数的含验证码字符串
	 * @param bit 指定生成验证码位数(默认6位)
	 * @return String  生成指定位数的字符串
	 */
	public static String random(int bit) {
		if(bit == 0){
			bit = 6;
		}
		// 因为o和0,l和1很难区分,所以,去掉大小写的o和l
		String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";	// 初始化种子
		return RandomStringUtils.random(bit, str);
	}
	
	/**
	 * 功能：随即生成指定位数的含验证码字符串
	 * @param bit 指定生成验证码位数(默认6位)
	 * @return String  生成指定位数的字符串
	 */
	public static String randomNum(int bit) {
		if(bit == 0){
			bit = 6;
		}
		// 因为o和0,l和1很难区分,所以,去掉大小写的o和l
		String str = "0123456789";	// 初始化种子
		return RandomStringUtils.random(bit, str);
	}
	
	/**
     * 功能：将字符串转换为utf-8字节数组
     */
    public static byte[] getUTF8Bytes(String str){
    	return getBytesByCharset(str,"utf-8");
    }
    
    /**
     * 功能：获取指定格式的字节数组
     */
    public static byte[] getBytesByCharset(String str,String charsetName){
    	try{
    		return str.getBytes(charsetName);
    	}catch(Exception e){
    		return null;
    	}
    }
    
    /**
     * 功能：获取字符串值(如果当前字符串没有值，取默认值)
     */
    public static String defaultIfEmpty(String curVal,String defaultVal){
    	if("".equals(curVal) || null == curVal){
    		return defaultVal;
    	}
    	return curVal;
    }
    
    /**
     * 功能：替换模板中的变量
     * @param template  模板
     * @param args  变量键值对
     * @throws 替换后的模板字符串
     */
    public static String replaceTemplateArg(String template, Map<String,Object> args){  
        String regex = "\\$\\{(.+?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);
        StringBuffer sb = new StringBuffer();  
        while(matcher.find()) {
            String name = matcher.group(1); //键名
            String value =  (args.get(name) == null ? "" : args.get(name).toString()); //键值
            if (value == null) {  
                value = "";
            } else {
                value = value.replaceAll("\\$", "\\\\\\$"); 
            }
            matcher.appendReplacement(sb, value);
        }
        matcher.appendTail(sb); 
        return sb.toString(); 
    }
    
    /**
     * 功能：替换模板中的变量
     * @param template  模板
     * @param args  变量键值对
     * @throws 替换后的模板字符串
     */
    public static String replaceTemplateArgS(String template, Map<String,String> args){  
        String regex = "\\$\\{(.+?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);
        StringBuffer sb = new StringBuffer();  
        while(matcher.find()) {
            String name = matcher.group(1); //键名
            String value =  (args.get(name) == null ? "" : args.get(name).toString()); //键值
            if (value == null) {  
                value = "";
            } else {
                value = value.replaceAll("\\$", "\\\\\\$"); 
            }
            matcher.appendReplacement(sb, value);
        }
        matcher.appendTail(sb); 
        return sb.toString(); 
    }
    
    /**
     * 功能：获取map未在模板中定义的参数
     * @param template  模板
     * @param args 变量键值对
     * @return map未在模板中定义的参数
     */
    public static String getTemplateUndefinedVar(String template, Map<String,Object> args){
    	 String regex = "\\$\\{(.+?)\\}";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(template);
         StringBuffer sb = new StringBuffer();  
         while(matcher.find()) {
             String name = matcher.group(1); //键名
             if(null == args.get(name)){
            	 sb.append(name).append(",");
             }
         }
         if(sb.length() > 0){
        	 return sb.toString().substring(0,sb.toString().length()-1);  
         }
         return "";
    }

	public static boolean isJavaClass(Class<?> clz) {
		return clz != null && clz.getClassLoader() == null;
	}

	/**
	 * 判断传入参数是否为空,空字符串""或"null"或"<null> 为了兼容ios的空获取到<null>字符串
	 *
	 * @param s
	 *            待判断参数
	 *
	 * @return true 空 false 非空
	 */
	public static boolean isEmptyString(Object s) {
		return (s == null) || (s.toString().trim().length() == 0) || "null".equalsIgnoreCase(s.toString().trim())
				|| "<null>".equalsIgnoreCase(s.toString().trim());
	}

	/**
	 * 判断传入参数不为空,空字符串""或"null"或"<null> 为了兼容ios的空获取到<null>字符串
	 *
	 * @param s
	 *            待判断参数
	 *
	 * @return true 空 false 非空
	 */
	public static boolean isNotEmptyString(Object s) {
		return !isEmptyString(s);
	}

	/**
	 * 对字符串两端去空
	 *
	 * @param v
	 *            待处理的字符串
	 *
	 * @return 处理后的字符串
	 */
	public static String filterNullValue(Object v) {
		return isEmptyString(v) ? "" : String.valueOf(v).trim();
	}
}
