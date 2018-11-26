package com.zltel.broadcast.terminal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.dao.SimpleDao;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.terminal.bean.MapInfo;
import com.zltel.broadcast.incision.sola.bean.Screen;
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
        List<MapInfo> m = new ArrayList<>();

        if (tbi != null && !tbi.isEmpty()) { // 是否查询到数据
            for (TerminalBasicInfo t : tbi) {
                if (null == t.getGis() || "".equals(t.getGis())) continue;
                List<String> c = new ArrayList<>();
                MapInfo mi = new MapInfo();
                String online = t.getOnline().equals("1") ? "在线" : "离线";
                String icon = t.getOnline().equals("1") ? "/assets/icons/1.png" : "/assets/icons/2.png";
                mi.setTitle(t.getName() + "<span style=\"font-size:11px;color:#F00;\">" + online + "</span>");
                mi.setIcon(icon);
                mi.setPosition(t.getGis().split(","));
                c.add("终端地址:" + t.getAddr());
                c.add("屏幕尺寸:" + t.getSize());
                c.add("屏幕分辨率:" + t.getRatio());
                c.add("屏幕方向:" + t.getRev());
                c.add("屏幕类型:" + t.getTyp());
                c.add("联系电话" + t.getTel());
                mi.setContent(c);
                m.add(mi);
            }
            return R.ok().setData(m).setMsg("查询地图信息成功");
        } else {
            return R.ok().setMsg("没有查询到地图信息");
        }
    }


    public int synchronizTerminalInfo() {
        List<Screen> terminals = solaProgramService.getScreenList(0, Pager.DEFAULT_PAGER);
        if (terminals == null || terminals.isEmpty()) {
            log.warn("没有终端信息需要同步.");
            return 0;
        }
        Map<String, Object> terminal = null;
        Map<String, Object> queryParam = new HashMap<>();
        List<String> codes = this.getTerminalCodes();
        for (Screen t : terminals) {
            queryParam.put("code", t.getCode());
            boolean isAdd = false;
            if (!codes.contains(t.getCode())) {
                isAdd = true;
            } else {
                // 不是新增 ,移除
                codes.remove(t.getCode());
            }
            terminal = new HashMap<>();
            terminal.put("name", t.getName());
            terminal.put("id", t.getPkId());
            terminal.put("code", t.getCode());
            terminal.put("type_id", t.getScreenType());
            terminal.put("res_time", t.getRegDateTime());
            terminal.put("online", t.getOnlineStatus());
            terminal.put("last_time", t.getLastOnlineTime());
            terminal.put("ip", t.getIp());
            terminal.put("mac", t.getMac());
            terminal.put("size", t.getScreenSize());
            terminal.put("ratio", t.getResolution());
            terminal.put("rev", t.getScreenDirection());
            terminal.put("ver", t.getVersion());
            terminal.put("typ", t.getScreenInteraction());
            terminal.put("cover_image", t.getCoverImage());

            if (isAdd) {
                // 更新时不需要更改的数据
                terminal.put("addr", t.getPosition());
            }
            terminal.put("last_syn_time", new Date());
            if (isAdd) {
                simpleDao.add(TERMINAL_BASIC_INFO, terminal);
            } else {
                simpleDao.update(TERMINAL_BASIC_INFO, terminal, queryParam);
            }
        }
        this.setTerminalOffline(codes);
        return terminals.size();
    }

    /** 设置不存在的终端 离线 **/
    private void setTerminalOffline(List<String> codes) {
        if (codes == null || codes.isEmpty()) return;
        log.warn("终端code为:{} 没有同步成功,标记为离线状态", JSON.toJSONString(codes));
        Map<String, Object> queryParam = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("online", 0);
        codes.forEach(code -> {
            queryParam.put("code", code);
            simpleDao.update(TERMINAL_BASIC_INFO, data, queryParam);
        });
    }

    /** 是否新增数据 **/
    private boolean isAdd(Map<String, Object> queryParam) {
        return simpleDao.get(TERMINAL_BASIC_INFO, queryParam) == null;
    }

    /** 获取终端id **/
    private List<String> getTerminalCodes() {
        List<Map<String, Object>> list = simpleDao.query(TERMINAL_BASIC_INFO, null);
        return list.stream().map(map -> (String) map.get("code")).collect(Collectors.toList());
    }


}
