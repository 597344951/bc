package com.zltel.broadcast.wechat_pay;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.github.pagehelper.util.StringUtil;

/**
 * 支付工具类
 * 
 * @author 张毅
 * @since jdk 1.8.0_191_b12 date: 2018.10.31
 */
public class Pay_Util {
	/**
	 * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。 防止数据被第三方篡改
	 * 
	 * @return boolean
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean isTenpaySign(String characterEncoding, SortedMap<String, String> packageParams,
			String API_KEY) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<String, String>> it = packageParams.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && StringUtil.isNotEmpty(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + API_KEY);

		// 算出摘要
		String mysign = MD5Encode(sb.toString(), characterEncoding).toLowerCase();
		String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();

		return tenpaySign.equals(mysign);
	}

	/**
	 * @Description：sign签名
	 * @param characterEncoding
	 *            编码格式
	 * @param parameters
	 *            请求参数
	 * @return
	 * 
	 * 
	 * 		假设传送参数 appid： wxd930ea5d5a258f4f mch_id： 10000100 device_info： 1000
	 *         body： test nonce_str： ibuaiVcKdpRxkhJA
	 * 
	 * 
	 *         第一步：对参数按照key=value的格式，并按照参数名ASCII字典序排序如下：
	 *         stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
	 * 
	 *         第二步：拼接API密钥：
	 *         stringSignTemp=stringA+"&key=192006250b4c09247ec02edce69f6a2d"
	 *         //注：key为商户平台设置的密钥key
	 *         sign=MD5(stringSignTemp).toUpperCase()="9A0A8659F005D6984697E2CA0A9CF3B7"
	 *         //注：MD5签名方式
	 *         sign=hash_hmac("sha256",stringSignTemp,key).toUpperCase()="6A9AE1657590FD6257D693A078E1C3E4BB6BA4DC30B23E0EE2496E54170DACD6"
	 *         //注：HMAC-SHA256签名方式
	 * 
	 *         最终得到最终发送的数据： <xml> <appid>wxd930ea5d5a258f4f</appid>
	 *         <mch_id>10000100</mch_id> <device_info>1000</device_info>
	 *         <body>test</body> <nonce_str>ibuaiVcKdpRxkhJA</nonce_str>
	 *         <sign>9A0A8659F005D6984697E2CA0A9CF3B7</sign> </xml>
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String createSign(String characterEncoding, SortedMap<String, String> packageParams, String API_KEY)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<String, String>> it = packageParams.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if (StringUtil.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
				sb.append(key + "=" + value + "&");
			}
		}
		sb.append("key=" + API_KEY);
		return MD5Encode(sb.toString(), characterEncoding).toUpperCase();
	}

	private static String randomStr = "abcdefghijklmnopqrstuvwxyz1234567890";

	/**
	 * 为微信支付生成一个32位的随机数
	 * 
	 * @return
	 */
	public static String getRamdomStr(int length) {
		StringBuffer sb = new StringBuffer();
		int len = randomStr.length();
		for (int i = 0; i < length; i++) {
			sb.append(randomStr.charAt((int) Math.round(Math.random() * (len - 1))));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 生成随机订单号
	 * 
	 * @return
	 */
	public static String getRamdomOrderNo() {
		StringBuffer sb = new StringBuffer();
		return sb.append("ZL-").append(getRamdomStr(6)).append(getNowDate()).append(getRamdomStr(6)).toString()
				.toUpperCase();
	}

	/**
	 * 得到当前时间值，为订单号服务
	 * 
	 * @return
	 */
	private static String getNowDate() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	/**
	 * 得到指定时间偏移的时间
	 * @param date	指定时间
	 * @param deviationYear	偏移年
	 * @param deviationMonth	偏移日
	 * @param deviationDay	偏移天
	 * @param deviationHour	偏移小时
	 * @param deviationMin	偏移分钟
	 * @param deviationSec	偏移秒
	 * @param deviationMilli	偏移毫秒
	 * @return
	 */
	public static Date deviationDate(Date date, int deviationYear, int deviationMonth, int deviationDay, int deviationHour
			, int deviationMin, int deviationSec, int deviationMilli) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		calendar.add(Calendar.YEAR, deviationYear);
		calendar.add(Calendar.MONTH, deviationMonth);
		calendar.add(Calendar.DAY_OF_MONTH, deviationDay);
		calendar.add(Calendar.HOUR, deviationHour);
		calendar.add(Calendar.MINUTE, deviationMin);
		calendar.add(Calendar.SECOND, deviationSec);
		calendar.add(Calendar.MILLISECOND, deviationMilli);
		
		return calendar.getTime();
	}
	
	/**
	 * 将时间格式化成微信需要的时间格式
	 * @param date	要格式化的时间
	 * @return
	 */
	public static String formatWechatDate(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date).toString();
	}

	/**
	 * 获取本机IP地址
	 * 
	 * @return
	 */
	public static String localIp() {
		String ip = null;
		Enumeration<NetworkInterface> allNetInterfaces = null;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
				for (InterfaceAddress add : InterfaceAddress) {
					InetAddress Ip = add.getAddress();
					if (Ip != null && Ip instanceof Inet4Address) {
						ip = Ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {

		}
		return ip;
	}

	/**
	 * 生成MD5
	 * 
	 * @param str
	 *            待处理数据
	 * @param charsetEncoding
	 *            //字符编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String MD5Encode(String str, String charsetEncoding)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		java.security.MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] array = md.digest(str.getBytes(StringUtil.isEmpty(charsetEncoding) ? "UTF-8" : charsetEncoding));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 生成 HMACSHA256
	 * 
	 * @param data
	 *            待处理数据
	 * @param key
	 *            密钥
	 * @return 加密结果
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws Exception
	 */
	public static String HMACSHA256(String str, String key, String charsetEncoding)
			throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(
				key.getBytes(StringUtil.isEmpty(charsetEncoding) ? "UTF-8" : charsetEncoding), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte[] array = sha256_HMAC
				.doFinal(str.getBytes(StringUtil.isEmpty(charsetEncoding) ? "UTF-8" : charsetEncoding));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}
}
