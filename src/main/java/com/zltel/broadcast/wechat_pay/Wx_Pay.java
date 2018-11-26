package com.zltel.broadcast.wechat_pay;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.dao.BaseUserInfoMapper;
import com.zltel.broadcast.um.dao.OrganizationRelationMapper;
import com.zltel.broadcast.wechat_pay.order.bean.WechatOrder;
import com.zltel.broadcast.wechat_pay.order.dao.WechatOrderMapper;

import io.netty.util.internal.StringUtil;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author 张毅
 * @since jdk 1.8.0_191_b12 
 * date: 2018.10.31
 */
@RequestMapping(value="/wx/pay")
@RestController
public class Wx_Pay {
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xFFFFFFFF;
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final Logger logout = LoggerFactory.getLogger(Wx_Pay.class);
	
	@Resource
    private WechatOrderMapper wechatOrderMapper;
	@Resource
	private BaseUserInfoMapper baseUserInfoMapper;
	@Resource
	OrganizationRelationMapper organizationRelationMapper;

	/**
	 * 获取微信支付的二维码地址，使用post提交
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getCodeUrl", method=RequestMethod.POST)
	@LogPoint("获取微信支付的二维码地址")
	@ApiOperation(value = "获取微信支付的二维码地址")
	public R getCodeUrl(Pay_Params ps) throws Exception {		
        Map<String, String> resultMap = new HashMap<>();
        if (AdminRoleUtil.isPlantAdmin() || AdminRoleUtil.isOrgAdmin()) {	//如果是平台管理员或组织管理员
        	resultMap.put("return_code", "FAIL");
        	resultMap.put("return_msg", "请使用个人账户登录");
        	return R.ok().setData(resultMap);
        }
        
        //订单时间范围
        ps.setTime_start(new Date());
		ps.setTime_expire(Pay_Util.deviationDate(ps.getTime_start(), 0, 0, 0, 0, Pay_Config.EFFECTIVETIME, 0, 0));	//订单有效时间
		//用户id
		SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
		BaseUserInfo bui = new BaseUserInfo();
		bui.setIdCard(sysUser.getUsername());
		List<BaseUserInfo> buis =  baseUserInfoMapper.queryBaseUserInfos(bui);
		if (buis == null || buis.size() != 1) {		//查询当前用户信息不正确或查询不到
			resultMap.put("return_code", "FAIL");
        	resultMap.put("return_msg", "订单生成失败");
        	return R.ok().setData(resultMap);
		}
		Integer user_id = buis.get(0).getBaseUserId();
		
		WechatOrder conditonWo = new WechatOrder();
		conditonWo.setUserId(user_id);
		conditonWo.setTimeStart(ps.getTime_start());
		conditonWo.setTradeStatus("待支付");
		conditonWo.setTotalFee(ps.getTotal_fee());
		conditonWo.setProductId(ps.getProduct_id());
		
		List<WechatOrder> wos = wechatOrderMapper.queryWechatOrder(conditonWo);		//查询是否有未支付的订单，避免重复生成订单
		if (wos != null && wos.size() == 1) {	//存在未支付的订单，继续完成此订单
			WechatOrder wo = wos.get(0);
			resultMap.put("return_code", "SUCCESS");
			resultMap.put("time_expire", String.valueOf(wo.getTimeExpire().getTime()));
        	resultMap.put("code_url", wo.getCodeUrl());		//微信二维码
			return R.ok().setData(resultMap);
		}
		
		/**
		 * 参数封装，准备生成新的订单
		 */
		SortedMap<String, String> packageParams = new TreeMap<>();	//封装号然后解析成xml
		packageParams.put("appid", Pay_Config.APPID);	// 微信服务号的appid
		packageParams.put("mch_id", Pay_Config.MCHID);	// 微信支付商户号
		packageParams.put("device_info", Pay_Config.DEVICEINFO);	//设备号
		packageParams.put("nonce_str", Pay_Util.getRamdomStr(32));	//32位随机字符串
		packageParams.put("sign_type", Pay_Config.SIGNTYPE);	//签名类型，统一使用MD5
		packageParams.put("body", ps.getBody());	//商品描述
		packageParams.put("out_trade_no", ps.getOut_trade_no());	//商户订单号【备注：每次发起请求都需要随机的字符串，否则失败。】
		packageParams.put("fee_type", Pay_Config.FEETYPE);	//使用币种
		packageParams.put("total_fee", String.valueOf(ps.getTotal_fee()));	//支付金额 ，使用分为单位
		packageParams.put("spbill_create_ip", Pay_Util.localIp());// 客户端主机ip
		packageParams.put("notify_url", Pay_Config.NOTIFY_URL);		//回调地址【注意，这里必须要使用外网的地址】
		packageParams.put("trade_type", Pay_Config.TRADETYPE_NATIVE);		// 类型【网页扫码支付】
		packageParams.put("time_start", Pay_Util.formatWechatDate(ps.getTime_start()));	//订单开始时间
		packageParams.put("time_expire", Pay_Util.formatWechatDate(ps.getTime_expire()));	//订单失效时间

		String sign = Pay_Util.createSign(Wx_Pay.DEFAULT_ENCODING, packageParams, Pay_Config.APIKEY); // 获取签名
		packageParams.put("sign", sign);

		String requestXML = Xml_Util.getRequestXml(packageParams);// 将请求参数转换成xml格式的String类型
		String resXml = Http_Util.post_request(Pay_Config.UFDODER_URL, requestXML); // 解析请求之后的xml参数并且转换成String类型
		Map<String, String> map = Xml_Util.doXMLParse(resXml);	//将xml格式的数据转换为map方便使用
		
		if ("SUCCESS".equalsIgnoreCase(map.get("return_code"))) {	//订单生成成功
			//生成记录记录此订单
			WechatOrder wo = new WechatOrder();
			
			Map<String, Object> conditions = new HashMap<>();
			conditions.put("orgRltUserId", user_id);
			List<Map<String, Object>> orgs = organizationRelationMapper.queryOrgRelationsNew(conditions);
			if (orgs != null && orgs.size() == 1) {		//如果有组织，就写入组织信息
				wo.setOrgName(String.valueOf(orgs.get(0).get("orgInfoName")));
			}
			
			wo.setUserId(user_id);	//设置用户唯一标识
			wo.setMchId(map.get("mch_id")); //商户号
			wo.setDeviceInfo(map.get("device_info")); //设备号
			wo.setNonceStr(map.get("nonce_str")); //随机字符串
			wo.setSign(map.get("sign")); //签名
			wo.setSignType(Pay_Config.SIGNTYPE); //签名类型
			wo.setBody(ps.getBody()); //商品描述
			wo.setDetail(ps.getDetail()); //商品详情
			wo.setAttach(ps.getAttach()); //附加数据
			wo.setOutTradeNo(ps.getOut_trade_no()); //商户订单号
			wo.setFeeType(Pay_Config.FEETYPE); //货币种类
			wo.setTotalFee(ps.getTotal_fee()); //商品金额
			wo.setSpbillCreateIp(packageParams.get("spbill_create_ip")); //终端ip
			wo.setTimeStart(ps.getTime_start()); //订单开始时间
			wo.setTimeExpire(ps.getTime_expire()); //订单失效时间
			wo.setTradeStatus("待支付"); //交易状态
			wo.setNotifyUrl(Pay_Config.NOTIFY_URL); //交易通知地址
			wo.setTradeType(Pay_Config.TRADETYPE_NATIVE); //交易类型
			wo.setProductId(ps.getProduct_id()); //商品id
			wo.setCodeUrl(map.get("code_url"));		//保存微信二维码链接，以便后续使用
			
			wechatOrderMapper.insertSelective(wo);	//本地保存记录
			
			resultMap.put("return_code", "SUCCESS");
			resultMap.put("time_expire", String.valueOf(ps.getTime_expire().getTime()));
        	resultMap.put("code_url", map.get("code_url"));
			return R.ok().setData(resultMap);
		} else {
			resultMap.put("return_code", "FAIL");
        	resultMap.put("return_msg", "订单生成失败");
        	return R.ok().setData(resultMap);
		}
	}

	/**
	 * 将路径生成二维码图片
	 * 
	 * @param content	//code码生成二维码
	 * @param response
	 */
	@RequestMapping(value="/encodeQrcode", method=RequestMethod.GET)
	@LogPoint("将路径生成二维码图片")
	@ApiOperation(value = "将路径生成二维码图片")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void encodeQrcode(String content, HttpServletResponse response) {
		if (StringUtils.isBlank(content))
			return;
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map hints = new HashMap();
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 250, 250, hints);
			BufferedImage image = toBufferedImage(bitMatrix);
			// 输出二维码图片流
			try {
				ImageIO.write(image, "png", response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (WriterException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 类型转换
	 * 
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) == true ? Wx_Pay.BLACK : Wx_Pay.WHITE);
			}
		}
		return image;
	}

	// 特殊字符处理
	public static String UrlEncode(String src) throws UnsupportedEncodingException {
		return URLEncoder.encode(src, Wx_Pay.DEFAULT_ENCODING).replace("+", "%20");
	}
	
	@RequestMapping(value="/testForward", method=RequestMethod.GET)
	public static void testForward(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("code", "success");
		request.getRequestDispatcher("/view/pm/wx_pay_result.jsp").forward(request, response);		
	}

	/**
	 * pc端微信支付成功之后的回调方法
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/wechat_notify_url_pc", method=RequestMethod.GET)
	@ApiOperation(value = "此为微信支付成功的回调方法，请勿访问")
	public static void wechat_notify_url_pc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 读取参数
		StringBuffer sb = new StringBuffer();
		String s;
		
		InputStream inputStream = null;
		BufferedReader in = null;
		try {
			inputStream = request.getInputStream();
			in = new BufferedReader(new InputStreamReader(inputStream, Wx_Pay.DEFAULT_ENCODING));
			while ((s = in.readLine()) != null) {
				sb.append(s);
			} 
		} finally {
			if (in != null) in.close();
			if (inputStream != null) inputStream.close();
		}
		
		String successResXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		String errorResXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
				+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		HttpServletRequest my_request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		logout.debug("返回xml信息：" + sb.toString());
		if(StringUtil.isNullOrEmpty(sb.toString())) {	//没有返回信息，认为交易失败
			BufferedOutputStream out = null;
			try {
				out = new BufferedOutputStream(response.getOutputStream());
				out.write(errorResXml.getBytes());
				out.flush();
			} finally {
				if (out != null) out.close();
			}
			
			my_request.setAttribute("code", "交易出现异常");
			my_request.getRequestDispatcher("/view/pm/wx_pay_result.jsp").forward(request, response);
		} else {	//否则解析内容
			// 过滤空 设置 TreeMap
			Map<String, String> m = Xml_Util.doXMLParse(sb.toString());		// 解析xml成map
			Iterator<String> it = m.keySet().iterator();
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			while (it.hasNext()) {
				String parameterKey = it.next();
				String parameterValue = m.get(parameterKey);
				packageParams.put(parameterKey, null != parameterValue ? parameterValue.trim() : "");
			}
			
			
			if (Pay_Util.isTenpaySign(Wx_Pay.DEFAULT_ENCODING, packageParams, Pay_Config.APIKEY)) {	// 判断签名是否正确
				BufferedOutputStream out = null;
				try {
					out = new BufferedOutputStream(response.getOutputStream());
					out.write("SUCCESS".equals((String) packageParams.get("result_code")) ? successResXml.getBytes()
							: errorResXml.getBytes());
					out.flush();
				} finally {
					if (out != null) out.close();
				}
				
				my_request.setAttribute("code", (String)packageParams.get("result_code"));
				my_request.getRequestDispatcher("/view/pm/wx_pay_result.jsp").forward(request, response);	
			} else {
				
			}
		}
	}
}
