package com.zltel.broadcast.um.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationInformation;
import com.zltel.broadcast.um.service.OrganizationInformationService;

import io.swagger.annotations.ApiOperation;

/**
 * 组织信息控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.6.5
 */
@RequestMapping(value="/org/ifmt")
@RestController
public class OrganizationInformationController extends BaseController {
	@Autowired
	private OrganizationInformationService organizationInformationService;
	
	
	/**
	 * 变更组织结构
	 * @param organizationInformation 条件
	 * @return
	 */
	@RequestMapping(value="/changeOrgStructureStart", method=RequestMethod.POST)
	@ApiOperation(value = "变更组织结构")
	public R changeOrgStructureStart(OrganizationInformation organizationInformation) {
		try {
			if(organizationInformationService.changeOrgStructureStart(organizationInformation) == 1) {
				return R.ok().setMsg("变更组织结构成功");
			} else {
				return R.error().setMsg("变更组织结构失败");
			}
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("变更组织结构失败");
		}
	}
	
	/**
	 * 查询到组织信息
	 * @param organizationInformation 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfosSelect", method=RequestMethod.POST)
	@ApiOperation(value = "查询到组织信息")
	public R queryOrgInfosSelect(OrganizationInformation organizationInformation) {
		try {
			List<OrganizationInformation> orgInfoSelects = organizationInformationService.queryOrgInfosSelect(organizationInformation);
			if(orgInfoSelects != null && orgInfoSelects.size() > 0) {
				return R.ok().setData(orgInfoSelects);
			} else {
				return R.ok().setMsg("没有查询到组织信息");
			}
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询到组织信息失败");
		}
	}
	
	/**
	 * 查询到组织信息（关联组织类型表的查询）
	 * @param organizationInformation 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfosSelects", method=RequestMethod.POST)
	@ApiOperation(value = "查询到组织信息")
	public R queryOrgInfosSelects(@RequestParam Map<String, Object> condition) {
		try {
			List<Map<String, Object>> orgInfoSelects = organizationInformationService.queryOrgInfosSelects(condition);
			if(orgInfoSelects != null && orgInfoSelects.size() > 0) {
				return R.ok().setData(orgInfoSelects);
			} else {
				return R.ok().setMsg("没有查询到组织信息");
			}
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询到组织信息失败");
		}
	}
	
	/**
	 * 入党时加入党组织组织信息
	 * @param organizationInformation 条件
	 * @return
	 */
	@RequestMapping(value="/joinOrgQueryOrgInfosSelect", method=RequestMethod.POST)
	@ApiOperation(value = "查询到组织信息")
	public R joinOrgQueryOrgInfosSelect(@RequestParam Map<String, Object> orgInfoConditions) {
		try {
			return organizationInformationService.joinOrgQueryOrgInfosSelect(orgInfoConditions);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询到组织信息失败");
		}
	}
	
	/**
	 * 查询组织信息生成树
	 * @param organizationInformation 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfosToTree", method=RequestMethod.POST)
	@LogPoint("查询组织信息生成树")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询组织信息生成树")
	public R queryOrgInfosToTree(OrganizationInformation organizationInformation) {
		try {
			return organizationInformationService.queryOrgInfosToTree(organizationInformation);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询组织信息生成树失败");
		}
	}
	
	/**
	 * 查询组织信息生成树
	 * @param orgInfoConditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfosToTrees", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询组织信息生成树")
	public R queryOrgInfosToTrees(@RequestParam Map<String, Object> orgInfoConditions) {
		try {
			return organizationInformationService.queryOrgInfosToTree(orgInfoConditions);
		} catch (Exception e) {
			return R.error().setMsg("查询组织信息生成树失败");
		}
	}
	
	/**
	 * 查询积分结构树
	 * @param orgInfoConditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgIntegralConstituteToTree", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询积分结构树")
	public R queryOrgIntegralConstituteToTree(@RequestParam Map<String, Object> orgInfoConditions) {
		try {
			return organizationInformationService.queryOrgIntegralConstituteToTree(orgInfoConditions);
		} catch (Exception e) {
			return R.error().setMsg("查询积分结构树失败");
		}
	}
	
	/**
	 * 查询组织信息
	 * @param organizationInfo 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfos", method=RequestMethod.POST)
	@LogPoint("查询组织信息")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询组织信息")
	public R queryOrgInfos(OrganizationInformation organizationInformation) {
		try {
			return organizationInformationService.queryOrgInfos(organizationInformation);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询组织信息失败");
		}
	}
	
	/**
	 * 查询组织信息
	 * @param organizationInfo 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfosForMap", method=RequestMethod.POST)
	@LogPoint("查询组织信息")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询组织信息")
	public R queryOrgInfosForMap(@RequestParam Map<String, Object> organizationInformation, int pageNum, int pageSize) {
		try {
			return organizationInformationService.queryOrgInfosForMap(organizationInformation, pageNum, pageSize);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询组织信息失败");
		}
	}
	
	/**
	 * 查询组织信息
	 * @param organizationInfo 条件
	 * @return
	 */
	@RequestMapping(value="/queryThisOrgChildren", method=RequestMethod.POST)
	@LogPoint("查询组织信息")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询组织信息")
	public R queryThisOrgChildren(@RequestParam Map<String, Object> organizationInformation, int pageNum, int pageSize) {
		try {
			return organizationInformationService.queryThisOrgChildren(organizationInformation, pageNum, pageSize);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询组织信息失败");
		}
	}
	
	/**
	 * 查询省份信息
	 * @param organizationInfo 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfosCommitteeProvince", method=RequestMethod.POST)
	@LogPoint("查询省份信息")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询省份信息")
	public R queryOrgInfosCommitteeProvince(OrganizationInformation organizationInformation) {
		try {
			return organizationInformationService.queryOrgInfosCommitteeProvince(organizationInformation);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询省份信息失败");
		}
	}
	
	/**
	 * 查询城市信息
	 * @param organizationInfo 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfosCommitteeCity", method=RequestMethod.POST)
	@LogPoint("查询城市信息")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询城市信息")
	public R queryOrgInfosCommitteeCity(OrganizationInformation organizationInformation) {
		try {
			return organizationInformationService.queryOrgInfosCommitteeCity(organizationInformation);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询城市信息失败");
		}
	}
	
	/**
	 * 查询地区信息
	 * @param organizationInfo 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgInfosCommitteeArea", method=RequestMethod.POST)
	@LogPoint("查询地区信息")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询地区信息")
	public R queryOrgInfosCommitteeArea(OrganizationInformation organizationInformation) {
		try {
			return organizationInformationService.queryOrgInfosCommitteeArea(organizationInformation);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询地区信息失败");
		}
	}
	
	/**
	 * 添加组织信息
	 * @param organizationInfo 要添加的组织信息
	 * @return
	 */
	@RequestMapping(value="/insertOrgInfo", method=RequestMethod.POST)
	@LogPoint("添加组织信息")
	@RequiresPermissions(value = {"org:info:insert"})
	@ApiOperation(value = "添加组织信息")
	public R insertOrgInfo(OrganizationInformation organizationInformation) {
		try {
			return organizationInformationService.insertOrgInfo(organizationInformation);	//开始添加组织信息
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("组织信息添加失败。");
		}
	}
	
	/**
	 * 修改组织信息
	 * @param organizationInfo 要修改的组织信息
	 * @return
	 */
	@RequestMapping(value="/updateOrgInfo", method=RequestMethod.POST)
	@LogPoint("修改组织信息")
	@RequiresPermissions(value = {"org:info:update"})
	@ApiOperation(value = "修改组织信息")
	public R updateOrgInfo(OrganizationInformation organizationInformation) {
		try {
			return organizationInformationService.updateOrgInfo(organizationInformation);	//开始修改系统用户信息
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("组织信息修改出错。");
		}
	}
	
	/**
	 * 批量删除组织信息
	 * @param organizationInfo 要删除的组织信息
	 * @return
	 */
	@RequestMapping(value="/deleteOrgInfo", method=RequestMethod.POST)
	@LogPoint("批量删除组织信息")
	@RequiresPermissions(value = {"org:info:delete"})
	@ApiOperation(value = "批量删除组织信息")
	public R deleteOrgInfo(OrganizationInformation organizationInformation) {
		try {
			return organizationInformationService.deleteOrgInfo(organizationInformation);	//开始删除组织信息
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("组织信息删除失败。");
		}
	}
	
	/**
	 * 得到用户数量
	 * @param organizationInfo 
	 * @return
	 */
	@RequestMapping(value="/queryOrgRelationsNewForUserId", method=RequestMethod.POST)
	@LogPoint("得到用户数量")
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "得到用户数量")
	public R queryOrgRelationsNewForUserId(@RequestParam Map<String, Object> organizationInformation) {
		try {
			return organizationInformationService.queryOrgRelationsNewForUserId(organizationInformation);	//开始删除组织信息
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("没有查询到用户数量。");
		}
	}
	
	
	
	/**
	 * 服务于按照时间间隔一次展示组织信息的层级，组织信息组织结构展示
	 * @param orgInfoConditions 条件
	 * @return
	 */
	@RequestMapping(value="/bossSayOneByOneShowOrgInfoLevel", method=RequestMethod.POST)
	@RequiresPermissions(value = {"org:info:query"})
	@ApiOperation(value = "查询组织信息生成树")
	public R bossSayOneByOneShowOrgInfoLevel(@RequestParam Map<String, Object> orgInfoConditions) {
		try {
			return organizationInformationService.bossSayOneByOneShowOrgInfoLevel(orgInfoConditions);
		} catch (Exception e) {
			return R.error().setMsg("查询组织信息生成树失败");
		}
	}
}
