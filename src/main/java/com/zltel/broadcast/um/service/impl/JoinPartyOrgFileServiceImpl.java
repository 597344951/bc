package com.zltel.broadcast.um.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.JoinPartyOrgFile;
import com.zltel.broadcast.um.dao.JoinPartyOrgFileMapper;
import com.zltel.broadcast.um.service.JoinPartyOrgFileService;

@Service
public class JoinPartyOrgFileServiceImpl extends BaseDaoImpl<JoinPartyOrgFile> implements JoinPartyOrgFileService {
	@Resource
    private JoinPartyOrgFileMapper joinPartyOrgFileMapper;
	@Override
    public BaseDao<JoinPartyOrgFile> getInstince() {
        return this.joinPartyOrgFileMapper;
    }
}
