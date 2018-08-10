package com.zltel.broadcast.resource.service;

import java.util.List;

import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.resource.bean.ResourceMaterial;

public interface ResourceMaterialService extends BaseDao<ResourceMaterial> {

    List<ResourceMaterial> query(ResourceMaterial record, Pager pager);

    int delete(ResourceMaterial m);

    void inserts(List<ResourceMaterial> records);

    /** 授权多个素材 **/
    void verify(List<ResourceMaterial> rms, boolean verify);

    /** 授权单个素材 **/
    void verify(ResourceMaterial nm);
    
    /**加载其他数据信息**/
    public void loadOtherInfo(ResourceMaterial rm);
    /**查询指定组织 素材存储**/
    Long orgUsedStoreSize(Integer orgid);
}
