package com.zltel.broadcast.wechat_pay.order.service;

import com.zltel.broadcast.wechat_pay.order.bean.WechatOrder;

public interface WechatOrderService {
	int deleteByPrimaryKey(Integer id);

    int insert(WechatOrder record);

    int insertSelective(WechatOrder record);

    WechatOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatOrder record);

    int updateByPrimaryKey(WechatOrder record);
}
