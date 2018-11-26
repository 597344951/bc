package com.zltel.broadcast.wechat_pay;

import java.util.Date;

/**
 * 微信支付参数/提交的商品信息
 * @author 张毅
 * @since jdk 1.8.0_191_b12
 * date: 2018.10.31
 */
public class Pay_Params {
	private Long total_fee;	//订单金额     特别注意，金额以分为单位	（必须）
	private String body;	//商品名称  这里使用智慧党建系统党费缴纳  默认使用  "智慧党建-在线缴纳党费"	（必须）
	private String detail;	//商品详细描述	（非必须）
	private String out_trade_no = Pay_Util.getRamdomOrderNo();	//商户订单号，必须随机  	（必须）
	private String attach = "智慧党建-在线缴纳党费";	//附加参数  	（非必须）
	private String memberid;	//会员ID  
	private String product_id;	//商品id，trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
	private Date time_start;	//交易起始时间，请求微信接口时，要转换成yyyymmddhhmmss，以北京时间为准
	private Date time_expire;	//交易结束时间
	
	public Date getTime_start() {
		return time_start;
	}
	public void setTime_start(Date time_start) {
		this.time_start = time_start;
	}
	public Date getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(Date time_expire) {
		this.time_expire = time_expire;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Long getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Long total_fee) {
		this.total_fee = total_fee;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
}
