package com.zltel.broadcast.terminal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;
import com.zltel.broadcast.terminal.dao.TerminalBasicInfoMapper;
import com.zltel.broadcast.terminal.service.TerminalOrgConfigService;

@Service
public class TerminalOrgConfigServiceImpl implements TerminalOrgConfigService {

    public static final Logger log = LoggerFactory.getLogger(TerminalOrgConfigServiceImpl.class);

    @Resource
    private TerminalBasicInfoMapper infoMapper;

    @Override
    public List<TerminalBasicInfo> queryUnConfigTerminal(TerminalBasicInfo info, Pager pager) {
        Page<?> p = PageHelper.startPage(pager.getPageIndex(), pager.getLimit());
        List<TerminalBasicInfo> list = this.infoMapper.queryUnConfigTerminal(info);
        pager.setTotal(p.getTotal());
        return list;
    }

    @Override
    @Transactional
    public int orgConfig(TerminalBasicInfo info) {
        
        return this.infoMapper.orgConfig(info);
    }

    @Override
    @Transactional
    public List<Integer> orgConfigs(List<TerminalBasicInfo> infos) {
        List<Integer> rets = new ArrayList<>();
        infos.forEach(info -> {
            int r = this.orgConfig(info);
            rets.add(r);
        });
        return rets;
    }

    @Override
    public List<R> checkTerminals(List<TerminalBasicInfo> infos) {
        List<R> rets = new ArrayList<>();
        infos.forEach(t -> {
            R r = this.checkTerminal(t);
            rets.add(r);
        });
        return rets;
    }

    private R checkTerminal(TerminalBasicInfo info) {
        if (null == info || StringUtils.isAnyBlank(info.getCode()) || info.getOrgId() == null) {
            return R.error("请输入查找终端");
        }
        // 检查 终端是否存在
        TerminalBasicInfo tb = new TerminalBasicInfo();
        tb.setCode(info.getCode());
        List<TerminalBasicInfo> tbs = this.infoMapper.queryBasicInfo(tb);
        if (tbs.isEmpty()) {
            return R.error("未找到指定终端,请先执行同步后再重试").setCode(R.CODE_NOT_FOUND).set("source", info.getCode());
        }
        // 检测终端是否已被关联
        TerminalBasicInfo ftb = tbs.get(0);
        if (ftb.getOrgId() != null && ftb.getOrgId() != 0) {
            return R.error("终端已关联组织,请勿重复关联").setCode(R.CODE_FAIL).set("source", info.getCode());
        }


        return R.ok("检测通过").set("source", info.getCode());
    }


}
