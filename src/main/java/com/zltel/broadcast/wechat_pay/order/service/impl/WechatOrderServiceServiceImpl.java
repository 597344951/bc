package com.zltel.broadcast.wechat_pay.order.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.wechat_pay.order.bean.WechatOrder;
import com.zltel.broadcast.wechat_pay.order.dao.WechatOrderMapper;
import com.zltel.broadcast.wechat_pay.order.service.WechatOrderService;

@Service
public class WechatOrderServiceServiceImpl extends BaseDaoImpl<WechatOrder> implements WechatOrderService {
	@Resource
    private WechatOrderMapper wechatOrderMapper;

    @Override
    public BaseDao<WechatOrder> getInstince() {
        return this.wechatOrderMapper;
    }
}
