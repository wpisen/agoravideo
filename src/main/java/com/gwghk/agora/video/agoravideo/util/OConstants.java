package com.gwghk.agora.video.agoravideo.util;

/**
 * 摘要：常量类
 * @author   Gavin.guo
 * @version  1.0
 * @Date	 2016年7月25日
 */
public class OConstants {
	/**
	 * 默认编码，如果没有特殊要求，都使用UTF8作为默认编码
	 */
	public final static String DEFAULT_ENCODING = "UTF-8";

	/**
	 * 空字符串
	 */
	public final static String EMPTY_STRING = "";

	/**
	 * 图片格式0-JPG
	 */
	public final static int IMAGE_FORMAT_JPG = 0;
	/**
	 * 图片格式1-BMP
	 */
	public final static int IMAGE_FORMAT_BMP = 1;
	/**
	 * 图片格式2-PNG
	 */
	public final static int IMAGE_FORMAT_PNG = 2;
	/**
	 * 图片格式3-GIF
	 */
	public final static int IMAGE_FORMAT_GIF = 3;
	
	public static final long NANO_TO_MILLS = 1000000l;


	/**
	 * 项目名称，用于在redis中进行区分
	 */
	public static final String PRO_NAME = "EXCHANGE_BOS_GATEWAY_C";

	/**
	 * 菜单缓存 redis key
	 */
	public static final String MENU_REDIS_KEY = PRO_NAME+":MENU_KEY";
	/**
	 * 角色菜单缓存 redis key
	 */
	public static final String ROLE_MENU_REDIS_KEY = PRO_NAME+":ROLE_MENU_KEY";
	/**
	 * 缓存用户与角色 redis key
	 */
	public static final String USER_ROLE_REDIS_KEY = PRO_NAME+":USER_ROLE_KEY";
	/**
	 * 缓存角色与列 redis key
	 */
	public static final String ROLE_COLUMN_AUTH_REDIS_KEY = PRO_NAME+":ROLE_COLUMN_AUTH_KEY";
	/**
	 * 缓存角色 redis key
	 */
	public static final String ROLE_REDIS_KEY = PRO_NAME+":ROLE_KEY";
	/**
	 * 缓存用户 redis key
	 */
	public static final String USER_REDIS_KEY = PRO_NAME+":USER_KEY";
	/**
	 * 缓存字典 redis key
	 */
	public static final String DICT_REDIS_KEY = PRO_NAME+":DICT_KEY_";
	/**
	 * 缓存字典 redis key
	 */
	public static final String DICT_SUB_SET_REDIS_KEY = PRO_NAME+":DICT_SUB_SET_KEY_";
	

}
