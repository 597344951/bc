package com.zltel.broadcast.um.util;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author 张毅
 * @since jdk 1.8.0_172
 * Date：2018.6.4
 */
public class RegexUtil {
	private RegexUtil() {}
	
	private static final String REGEX_IDCARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|("
			+ "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
	
	private static final String REGEX_DATE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?(("
			+ "((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))["
			+ "\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9]"
			+ ")))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[0"
			+ "2]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?(("
			+ "0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	
	private static final String REGEX_MOBILEPHONE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
	
	private static final String REGEX_QQ = "^[1-9][0-9]{4,10}";
	
	private static final String REGEX_WECHAT = "[-_a-zA-Z0-9]{5,19}";
	
	private static final String REGEX_EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
	
	private static final String REGEX_NUM = "^\\d+$";
	
	private static final String REGEX_CHINANAME = "^[\\u4e00-\\u9fa5.·]+$";
	
	/**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }
    
    /**
     * 验证身份证号码
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard(CharSequence input) {
        return isMatch(REGEX_IDCARD, input);
    }
    
    /**
     * 验证日期
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isDate(CharSequence input) {
        return isMatch(REGEX_DATE, input);
    }
    
    /**
     * 验证移动电话号码
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobilePhone(CharSequence input) {
        return isMatch(REGEX_MOBILEPHONE, input);
    }
    
    /**
     * 验证QQ
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isQq(CharSequence input) {
        return isMatch(REGEX_QQ, input);
    }
    
    /**
     * 验证微信
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isWechat(CharSequence input) {
        return isMatch(REGEX_WECHAT, input);
    }
    
    /**
     * 验证邮箱
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(CharSequence input) {
        return isMatch(REGEX_EMAIL, input);
    }
    
    /**
     * 验证正整数
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isNum(CharSequence input) {
        return isMatch(REGEX_NUM, input);
    }
    
    /**
     * 验证中国姓名
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isChinaName(CharSequence input) {
        return isMatch(REGEX_CHINANAME, input);
    }
}
