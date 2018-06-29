package com.zltel.broadcast.terminal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.dao.SimpleDao;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.terminal.bean.MapInfo;
import com.zltel.broadcast.incision.sola.service.SolaProgramService;
import com.zltel.broadcast.terminal.bean.OnlineCountBean;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;
import com.zltel.broadcast.terminal.bean.TerminalEcharts;
import com.zltel.broadcast.terminal.dao.TerminalBasicInfoMapper;
import com.zltel.broadcast.terminal.service.TerminalBasicInfoService;

@Service
public class TerminalBasicInfoServiceImpl implements TerminalBasicInfoService {

    private static final Logger log = LoggerFactory.getLogger(TerminalBasicInfoServiceImpl.class);
    private static final String TERMINAL_BASIC_INFO = "terminal_basic_info";
    @Resource
    private TerminalBasicInfoMapper tbm;

    @Resource
    private SolaProgramService solaProgramService;
    @Resource
    private SimpleDao simpleDao;

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
        if (tbis.getList() != null && !tbis.getList().isEmpty()) { // 是否查询到数据

            return R.ok().setData(tbis).setMsg("查询终端基础信息成功");
        } else {
            return R.ok().setMsg("没有查询到查询终端基础信息");
        }
    }

    @Override
    public R echarts(String string) {
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
            List<TerminalEcharts> ta = null;
            List<TerminalEcharts> tb = null;
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
        if (!te.isEmpty()) {
            return R.ok().setData(te).setMsg("查询统计信息成功");
        } else {
            return R.ok().setMsg("没有查询到统计信息");
        }

    }

    @Override
    public Map<String, Integer> countOnlineTerminal() {
        Map<String, Integer> ret = new HashMap<>();
        List<OnlineCountBean> list = this.tbm.countOnlineTerminal();
        list.forEach(m -> ret.put(m.getType(), m.getCount()));
        return ret;
    }


    @Override
    public R queryMapInfo() {
        List<TerminalBasicInfo> tbi = tbm.queryAll(); 
        List<MapInfo> m=new ArrayList<>();
        if (tbi != null && tbi.size() > 0) { // 是否查询到数据
            for (TerminalBasicInfo t : tbi) {
                MapInfo mi=new MapInfo();
                mi.setTitle(t.getName()+"<span style=\"font-size:11px;color:#F00;\">"+t.getAddr()+"</span>");
                mi.setIcon("https://vdata.amap.com/icons/b18/1/2.png");
                mi.setPosition(t.getGis());
                mi.setContent1("ip地址:"+t.getIp());
                m.add(mi);
            }
            return R.ok().setData(m).setMsg("查询地图信息成功");
        } else {
            return R.ok().setMsg("没有查询到地图信息");
        }
    }


    public void synchronizTerminalInfo() {
        List<Map<String, Object>> terminals = solaProgramService.queryTerminal();
        if (terminals == null || terminals.isEmpty()) {
            log.warn("没有终端信息需要同步.");
            return;
        }
        Map<String, Object> terminal = null;
        Map<String, Object> queryParam = new HashMap<>();
        for (Map<String, Object> t : terminals) {
            queryParam.put("id", t.get("PkId"));
            boolean isAdd = isAdd(queryParam);
            terminal = new HashMap<>();
            terminal.put("name", t.get("Name"));
            terminal.put("id", t.get("PkId"));
            terminal.put("code", t.get("Code"));
            terminal.put("type_id", t.get("ScreenType"));
            terminal.put("res_time", t.get("RegDateTime"));
            terminal.put("online", t.get("OnlineStatus"));
            terminal.put("last_time", t.get("LastOnlineTime"));
            terminal.put("ip", t.get("IP"));
            terminal.put("mac", t.get("Mac"));
            terminal.put("size", t.get("ScreenSize"));
            terminal.put("ratio", t.get("Resolution"));
            terminal.put("rev", t.get("ScreenDirection"));
            terminal.put("ver", t.get("Version"));
            terminal.put("typ", t.get("ScreenInteraction"));
            terminal.put("cover_image", t.get("CoverImage"));

            if (isAdd) {
                // 更新时不需要更改的数据
                terminal.put("addr", t.get("Position"));
            }
            terminal.put("last_syn_time", new Date());
            if (isAdd) {
                simpleDao.add(TERMINAL_BASIC_INFO, terminal);
            } else {
                simpleDao.update(TERMINAL_BASIC_INFO, terminal, queryParam);
            }
        }

    }


    /** 是否新增数据 **/
    private boolean isAdd(Map<String, Object> queryParam) {
        return simpleDao.get(TERMINAL_BASIC_INFO, queryParam) == null;
    }


}
