package com.zltel.broadcast.wechat_pay;

/**
 * 微信支付配置
 * @author 张毅
 * @since jdk 1.8.0_191_b12
 * date: 2018.10.31
 */
public class Pay_Config {
	/** 
	* 微信服务号APPID 
	*/  
	public static String APPID = "wx6daa0c5f01cbec9f";
	/** 
	* 微信支付的商户号 
	*/  
	public static String MCHID = "1517538711";
	/**
	 * 设备号，本微信支付用户网页扫码支付，所以使用web
	 */
	public static String DEVICEINFO = "WEB";
	/**
	 *订单有效时间（分钟），刷卡最低1分钟，其它最低5分钟，这里取最小值5分钟
	 */
	public static int EFFECTIVETIME = 5;
	/**
	 * 签名类型，同意使用HMAC-SHA256
	 */
	public static String SIGNTYPE = "MD5";
	/**
	 * 使用币种，默认使用CNY
	 */
	public static String FEETYPE = "CNY";
	/**
	 * 交易类型，本系统使用网页扫码支付
	 */
	public static String TRADETYPE_NATIVE = "NATIVE";
	/** 
	* 微信支付的API密钥，key为商户平台设置的密钥key，商户平台操作密码ZhuoLing0613...
	*/  
	public static String APIKEY = "7765439D2BBF407193C598F979AE4A9E";
	/** 
	* 微信支付成功之后的回调地址【注意：当前回调地址必须是公网能够访问的地址】
	*/  
	public static String NOTIFY_URL = "http://www.shannondt.com.cn:8090/wx/pay/wechat_notify_url_pc";
	/** 
	* 微信统一下单API地址 
	*/  
	public static String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
