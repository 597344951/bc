package com.zltel.broadcast.wechat_pay.order.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.wechat_pay.order.bean.WechatOrder;

public interface WechatOrderMapper extends BaseDao<WechatOrder> {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatOrder record);

    int insertSelective(WechatOrder record);

    WechatOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatOrder record);

    int updateByPrimaryKey(WechatOrder record);
    
    /**
     * 查询订单
     * @param wo
     * @return
     */
    public List<WechatOrder> queryWechatOrder(WechatOrder wo);
}