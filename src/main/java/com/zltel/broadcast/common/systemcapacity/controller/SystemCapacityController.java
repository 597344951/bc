package com.zltel.broadcast.common.systemcapacity.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.systemcapacity.bean.Capability;
import com.zltel.broadcast.resource.service.ResourceMaterialService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

/**
 * 系统容量
 * 
 * @author wangch
 *
 */
@RestController
@RequestMapping("/system-capacity")
public class SystemCapacityController extends BaseController {

    public static final long DEFAULT_STORE_SIZE = 10000000000L;

    @Resource
    private ResourceMaterialService resourceMaterialService;

    /**
     * 组织素材存储容量
     * 
     * @return
     */
    @ApiOperation(value = "查询当前用户所属组织素材使用容量")
    @GetMapping("/resource-store")
    public R orgResStoreCapacity() {
        SysUser user = getSysUser();
        return this.orgResStoreCapacity(user.getOrgId());
    }

    @ApiOperation(value = "查询指定组织组织素材使用容量")
    @GetMapping("/resource-store/{orgid}")
    public R orgResStoreCapacity(@PathVariable("orgid") Integer orgid) {
        Long size = this.resourceMaterialService.orgUsedStoreSize(orgid);
        Capability c = new Capability();
        c.setUsed(size == null ? 0 : size);
        c.setTotal(DEFAULT_STORE_SIZE);
        return R.ok().setData(c);
    }

}
