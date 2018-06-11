package com.zltel.broadcast.terminal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;
import com.zltel.broadcast.terminal.bean.TerminalGatherBean;
import com.zltel.broadcast.terminal.bean.TerminalGroup;
import com.zltel.broadcast.terminal.bean.TerminalGroupsInfo;
import com.zltel.broadcast.terminal.dao.TerminalBasicInfoMapper;
import com.zltel.broadcast.terminal.dao.TerminalGroupMapper;
import com.zltel.broadcast.terminal.dao.TerminalGroupsInfoMapper;
import com.zltel.broadcast.terminal.service.TerminalGroupsService;

@Service
public class TerminalGroupsServiceImpl implements TerminalGroupsService {

    @Resource
    private TerminalGroupMapper tgm;
    @Resource
    private TerminalGroupsInfoMapper tgi;
    @Resource
    private TerminalBasicInfoMapper tbi;

    @Override
    public int deleteByPrimaryKey(Integer oid) {
        this.deleteGi(oid);
        return tgm.deleteByPrimaryKey(oid);
    }
    
    @Override
    public int insert(TerminalGroup record) {
        return 0;
    }

    @Override
    public int insertSelective(TerminalGroup record) {
        return tgm.insertSelective(record);
    }

    @Override
    public TerminalGroup selectByPrimaryKey(Integer oid) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(TerminalGatherBean record) {
        this.deleteGi(record.getTg().getOid());
        int count =0;
        TerminalGroup tg=record.getTg();
        
        List<Integer> oids=record.getOids();
        for (Integer integer : oids) {
            TerminalGroupsInfo tgib = new TerminalGroupsInfo();
            tgib.setGid(tg.getOid());
            tgib.setTid(integer);
            if(this.tgi.insertSelective(tgib)>0) {
                count++;
            }
        }
        if (count==oids.size()) {
            tg.setCount(String.valueOf(oids.size()));
            tg.setDate(new Date().toString());
            return tgm.updateByPrimaryKeySelective(tg);
        }else {
            return 0;
        }
        
    }

    @Override
    public int updateByPrimaryKey(TerminalGroup record) {
        return tgm.updateByPrimaryKey(record);
    }

    @Override
    public R queryBasicGroup(TerminalGroup record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        try {
            if (null == record) {
                record = new TerminalGroup();
            }
            List<TerminalGroup> tg = tgm.queryInfo(record);
            PageInfo<TerminalGroup> tgs = new PageInfo<>(tg);
            if (tgs.getList() != null && tgs.getList().size() > 0) { // 是否查询到数据

                return R.ok().setData(tgs).setMsg("查询终端分组信息成功");
            } else {
                return R.ok().setMsg("没有查询到查询终端分组信息");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();

    }



    @Override
    public R queryBasicInfo(TerminalGroup record) {
        try {
            List<TerminalBasicInfo> tbis = new ArrayList<>();
            if (null == record.getOid()) {
                tbis = tbi.queryAll();
                for (TerminalBasicInfo terminalBasicInfo : tbis) {
                    terminalBasicInfo.setName("终端名称: " + terminalBasicInfo.getName() + " 终端地址: "
                            + terminalBasicInfo.getAddr() + " 联系电话: " + terminalBasicInfo.getTel()+" 屏幕方向: " + terminalBasicInfo.getRev()+" 屏幕分辨率: " + terminalBasicInfo.getRatio());
                }
                if (tbis != null && tbis.size() > 0) {
                    TerminalGatherBean tgb=new TerminalGatherBean();
                    tgb.setTbis(tbis);
                    tgb.setTbiss(new ArrayList<>());
                    return R.ok().setData(tgb).setMsg("查询终端基础信息成功");
                } else {
                    return R.ok().setMsg("没有查询到查询终端基础信息");
                }
            } else {
                List<Integer> a = tgi.queryInfo(record.getOid());
                List<TerminalBasicInfo> tbiall = tbi.queryAll();
                for (TerminalBasicInfo terminalBasicInfo : tbiall) {
                    terminalBasicInfo.setName("终端名称: " + terminalBasicInfo.getName() + " 终端地址: "
                            + terminalBasicInfo.getAddr() + " 联系电话: " + terminalBasicInfo.getTel()+" 屏幕方向: " + terminalBasicInfo.getRev()+" 屏幕分辨率: " + terminalBasicInfo.getRatio());
                }
                if (a != null && a.size() > 0) {
                    TerminalGatherBean tgb=new TerminalGatherBean();
                    tgb.setTbis(tbiall);
                    tgb.setTbiss(a);
                    return R.ok().setData(tgb).setMsg("查询终端基础信息成功");
                } else {
                    TerminalGatherBean tgb=new TerminalGatherBean();
                   
                    tgb.setTbis(tbiall);
                    tgb.setTbiss(new ArrayList<>());
                    return R.ok().setData(tgb).setMsg("查询终端基础信息成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();

    }

    @Override
    public R insertTgb(TerminalGatherBean tgb) {
        try {
            List<Integer> oids = tgb.getOids();
            TerminalGroup tg = tgb.getTg();
            tg.setCount(String.valueOf(oids.size()));
            tg.setDate(new Date().toString());
            int count=0;
            if(this.tgm.insertSelective(tg)>0) {
               tg.setOid(tgm.selectOid(tg)); 
               for (Integer integer : oids) {
                   TerminalGroupsInfo tgib = new TerminalGroupsInfo();
                   tgib.setGid(tg.getOid());
                   tgib.setTid(integer);
                   if(this.tgi.insertSelective(tgib)>0) {
                       count++;
                   }
               }
               if (count==oids.size()) {
                   return R.ok();
               }else {
                   this.deleteGi(tg.getOid());
                   tgm.deleteByPrimaryKey(tg.getOid());
                   return R.error();
               }
            }else {
                return R.error();
            }
           

        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @Override
    public int deleteGi(Integer oid) {
        return tgi.deleteByPrimaryKey(oid);
    }



}
