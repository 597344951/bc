package com.zltel.broadcast.terminal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.terminal.bean.OnlineCountBean;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;
import com.zltel.broadcast.terminal.bean.TerminalEcharts;
import com.zltel.broadcast.terminal.dao.TerminalBasicInfoMapper;
import com.zltel.broadcast.terminal.service.TerminalBasicInfoService;

@Service
public class TerminalBasicInfoServiceImpl implements TerminalBasicInfoService {
    @Resource
    private TerminalBasicInfoMapper tbm;

    @Override
    public int deleteByPrimaryKey(Integer oid) {
        return tbm.deleteByPrimaryKey(oid);
    }

    @Override
    public int insert(TerminalBasicInfo record) {
        record.setResTime((new Date()).toString());
        record.setLastTime((new Date()).toString());
        return tbm.insert(record);
    }

    @Override
    public int insertSelective(TerminalBasicInfo record) {
        return tbm.insertSelective(record);
    }

    @Override
    public TerminalBasicInfo selectByPrimaryKey(Integer oid) {
        return tbm.selectByPrimaryKey(oid);
    }

    @Override
    public int updateByPrimaryKeySelective(TerminalBasicInfo record) {
        record.setLastTime(new Date().toString());
        return tbm.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TerminalBasicInfo record) {
        return tbm.updateByPrimaryKey(record);
    }

    @Override
    public R queryBasicInfo(TerminalBasicInfo record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (null == record) {
            record = new TerminalBasicInfo();
        }
        List<TerminalBasicInfo> tbi = tbm.queryBasicInfo(record); // 开始查询，没有条件则查询所有系统用户
        PageInfo<TerminalBasicInfo> tbis = new PageInfo<>(tbi);
        if (tbis.getList() != null && tbis.getList().size() > 0) { // 是否查询到数据

            return R.ok().setData(tbis).setMsg("查询终端基础信息成功");
        } else {
            return R.ok().setMsg("没有查询到查询终端基础信息");
        }
    }

    @Override
    public R echarts(String string) throws Exception {
        try {
            List<TerminalEcharts> te = new ArrayList<>();
            String str = string.trim();
            if ("ratio".equals(str)) {
                te = tbm.ratioEcharts();
            }
            if ("online".equals(str)) {
                te = tbm.onlineEcharts();
            }
            if ("loc".equals(str)) {
                te = tbm.locEcharts();
            }
            if ("warranty".equals(str)) {
                te = tbm.warrantyEcharts();
            }
            if ("rev".equals(str)) {
                te = tbm.revEcharts();
            }
            if ("ver".equals(str)) {
                te = tbm.verEcharts();
            }
            if ("typ".equals(str)) {
                te = tbm.typEcharts();
            }
            if ("total".equals(str)) {
                te = tbm.tCountEcharts();
                float total = Float.parseFloat(te.get(0).getValue());
                List<TerminalEcharts> ta = new ArrayList<>();
                List<TerminalEcharts> tb = new ArrayList<>();
                ta = tbm.ratioEcharts();
                tb = tbm.onlineEcharts();
                ta.addAll(tb);
                tb = tbm.locEcharts();
                ta.addAll(tb);
                tb = tbm.warrantyEcharts();
                ta.addAll(tb);
                tb = tbm.revEcharts();
                ta.addAll(tb);
                tb = tbm.verEcharts();
                ta.addAll(tb);
                tb = tbm.typEcharts();
                ta.addAll(tb);
                for (TerminalEcharts a : ta) {
                    float b = Float.parseFloat(a.getValue());
                    float c = b / total;
                    String rate = String.valueOf(c);
                    a.setRate(rate);
                }
                te = ta;
            }
            if (te.size() > 0) {
                return R.ok().setData(te).setMsg("查询统计信息成功");
            } else {
                return R.ok().setMsg("没有查询到统计信息");
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public Map<String, Integer> countOnlineTerminal() {
        Map<String, Integer> ret = new HashMap<>();
        List<OnlineCountBean> list = this.tbm.countOnlineTerminal();
        list.forEach(m -> ret.put(m.getType(), m.getCount()));
        return ret;
    }

}
