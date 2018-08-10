package com.zltel.broadcast.resource.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.resource.bean.MaterialAlbum;
import com.zltel.broadcast.resource.bean.ResourceMaterial;
import com.zltel.broadcast.resource.dao.MaterialAlbumMapper;
import com.zltel.broadcast.resource.dao.ResourceMaterialMapper;
import com.zltel.broadcast.resource.service.ResourceMaterialService;
import com.zltel.broadcast.um.bean.OrganizationInfo;
import com.zltel.broadcast.um.bean.OrganizationInformation;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.dao.OrganizationInfoMapper;
import com.zltel.broadcast.um.dao.OrganizationInformationMapper;
import com.zltel.broadcast.um.dao.SysUserMapper;

@Service
public class ResourceMaterialServiceImpl extends BaseDaoImpl<ResourceMaterial> implements ResourceMaterialService {

    @Resource
    private ResourceMaterialMapper mapper;

    @Resource
    private MaterialAlbumMapper albumMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private OrganizationInformationMapper orgMapper;

    @Override
    public BaseDao<ResourceMaterial> getInstince() {
        return mapper;
    }

    @Override
    public List<ResourceMaterial> query(ResourceMaterial record, Pager pager) {
        return this.mapper.query(record, pager);
    }

    @Override
    public int delete(ResourceMaterial m) {
        return this.mapper.delete(m);
    }

    @Override
    @Transactional
    public void inserts(List<ResourceMaterial> records) {

        this.mapper.inserts(records);
    }

    @Override
    @Transactional
    public void verify(List<ResourceMaterial> rms, final boolean verify) {
        rms.stream().forEach(rm -> {
            this.loadOtherInfo(rm);
            rm.setVerify(verify);
            this.verify(rm);
        });
    }


    @Override
    @Transactional
    public void verify(ResourceMaterial rm) {
        this.loadOtherInfo(rm);
        ResourceMaterial nm = new ResourceMaterial();
        nm.setMaterialId(rm.getMaterialId());
        nm.setVerify(rm.getVerify());
        nm.setVerifyDate(new Date());
        this.updateByPrimaryKeySelective(nm);
    }

    @Override
    public void loadOtherInfo(ResourceMaterial rm) {
        if (rm == null) return;
        Integer albumId = rm.getAlbumId();
        if (albumId != null) {
            MaterialAlbum ma = this.albumMapper.selectByPrimaryKey(albumId);
            if (null != ma) rm.setAlbumName(ma.getName());
        }
        Integer uid = rm.getUserId();
        if (null != uid) {
            SysUser user = this.sysUserMapper.selectByPrimaryKey(uid);
            if (null != user) rm.setUserName(user.getUsername());
        }
        Integer orgId = rm.getOrgId();
        if (null != orgId) {
            OrganizationInformation ogi = orgMapper.selectByPrimaryKey(orgId);
            if (null != ogi) rm.setOrgName(ogi.getOrgInfoName());
        }

    }

    @Override
    public Long orgUsedStoreSize(Integer orgid) {
       return this.mapper.orgUsedStoreSize(orgid);
    }



}
