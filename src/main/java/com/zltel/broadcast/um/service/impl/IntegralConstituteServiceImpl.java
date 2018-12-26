package com.zltel.broadcast.um.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.util.AdminRoleUtil;
import com.zltel.broadcast.um.bean.IntegralChangeType;
import com.zltel.broadcast.um.bean.IntegralConstitute;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.dao.IntegralChangeTypeMapper;
import com.zltel.broadcast.um.dao.IntegralConstituteMapper;
import com.zltel.broadcast.um.dao.PartyIntegralRecordMapper;
import com.zltel.broadcast.um.service.IntegralConstituteService;
import com.zltel.broadcast.um.util.IntegralErrorException;
import com.zltel.broadcast.um.util.IntegralNullException;
import com.zltel.broadcast.um.util.JumpOutRecursionException;

@Service
public class IntegralConstituteServiceImpl extends BaseDaoImpl<IntegralConstitute>
        implements
            IntegralConstituteService {
    @Resource
    private IntegralConstituteMapper integralConstituteMapper;
    @Resource
    private IntegralChangeTypeMapper integralChangeTypeMapper;
    @Resource
    private PartyIntegralRecordMapper partyIntegralRecordMapper;

    @Override
    public BaseDao<IntegralConstitute> getInstince() {
        return this.integralConstituteMapper;
    }
    
    /**
     * 修改
     * @param ic
     * @return
     */
    public R updateIntegralConstitute(IntegralConstitute ic) {
    	if (ic != null) {
    		int count = integralConstituteMapper.updateByPrimaryKeySelective(ic);
            if (count == 1) {
                return R.ok();
            } else {
                return R.error().setMsg("修改失败");
            }
    	}
    	return R.error().setMsg("修改错误");
    }
    
    /**
     * 删除
     * @param ic
     * @return
     */
    public R deleteIntegralConstitute(IntegralConstitute ic) {
    	Map<String, Object> condition = new HashMap<>();
    	condition.put("parentIcId", ic.getIcId());
    	List<Map<String, Object>> result = integralConstituteMapper.queryOrgIntegralConstitute(condition);
    	
    	if (result != null && result.size() > 0) {
    		return R.error().setMsg("请先删除此节点下的子节点");
    	}
    	
    	List<Integer> icIds = new ArrayList<>();
    	icIds.add(ic.getIcId());
    	try {
			this.checkNodeHaveIntegral(icIds);
		} catch (JumpOutRecursionException e) {
			return R.error().setMsg(e.getMsg());
		}
    	int count = integralConstituteMapper.deleteByPrimaryKey(ic.getIcId());
    	if (count == 1) {
    		return R.ok().setMsg("删除成功");
    	}
    	return R.ok().setMsg("删除失败");
    }
    
    /**
     * 检查此节点下是否有积分
     * @return
     */
    private void checkNodeHaveIntegral(List<Integer> icIds) throws JumpOutRecursionException {
    	if (icIds != null && icIds.size() > 0) {
    		for (Integer icId : icIds) {
    			IntegralChangeType ict = new IntegralChangeType();
    	    	ict.setIcId(icId);
    	    	List<IntegralChangeType> icts = integralChangeTypeMapper.queryICT(ict);
    	    	if (icts != null && icts.size() > 0) {
    	    		for (IntegralChangeType resultIct : icts) {
    	    			Map<String, Object> condition = new HashMap<>();
        	    		condition.put("changeIntegralType", resultIct.getIctId());
        	    		List<Map<String, Object>> pirs = partyIntegralRecordMapper.queryPartyIntegralRecords(condition);
        	    		if (pirs != null && pirs.size() > 0) {
        	    			throw new JumpOutRecursionException("该节点或子节点有积分信息，不能删除");
        	    		}
					}
    	    		
    	    	}
    	    	
    	    	//查询此节点下的子节点
    	    	Map<String, Object> condition = new HashMap<>();
    	    	condition.put("parentIcId", icId);
    	    	List<Map<String, Object>> ics = integralConstituteMapper.queryOrgIntegralConstitute(condition);
    	    	if (ics != null && ics.size() > 0) {
    	    		List<Integer> _icIds = new ArrayList<>();
    	    		for (Map<String, Object> map : ics) {
						_icIds.add(Integer.parseInt(String.valueOf(map.get("icId"))));
					}
    	    		this.checkNodeHaveIntegral(_icIds);
    	    	}
			}
    	}
    }

    /**
     * 添加组织积分结构
     * 
     * @param conditions
     * @return
     */
    public R insertIntegralConstitute(IntegralConstitute ic) {
        if (ic.getOrgId() == null) return R.error().setMsg("添加发生错误");
        int count = integralConstituteMapper.insertSelective(ic);
        if (count == 1) {
            return R.ok().setMsg("添加成功");
        } else {
            return R.error().setMsg("添加发生错误");
        }
    }

    /**
     * 查询拥有积分结构的组织
     * 
     * @param conditions
     * @return
     */
    public R queryOrgInfoForIc(Map<String, Object> conditions, int pageNum, int pageSize) {
        Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        if (conditions == null) conditions = new HashMap<>();
        if (AdminRoleUtil.isPlantAdmin()) { // 如果是平台管理员
            // 不做任何处理
        } else if (AdminRoleUtil.isOrgAdmin()) { // 如果是组织管理员
            if (sysUser.getOrgId() == null) {
                return R.ok().setCode(100).setMsg("组织管理员请设置所属的组织，如果是党员请加入组织");
            }
            conditions.put("orgInfoId", sysUser.getOrgId());
        } else { // 个人用户，即党员，没有此功能的权限，前台此模块不显示
            return null;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> orgInfoForIcs = integralConstituteMapper.queryOrgInfoForIc(conditions);
        PageInfo<Map<String, Object>> orgInfoForIcsForPageInfo = new PageInfo<>(orgInfoForIcs);
        if (orgInfoForIcsForPageInfo != null && orgInfoForIcsForPageInfo.getList() != null
                && orgInfoForIcsForPageInfo.getList().size() > 0) {
            return R.ok().setData(orgInfoForIcsForPageInfo).setMsg("查询组织信息成功");
        } else {
            return R.ok().setMsg("没有查询到组织信息");
        }
    }

    /**
     * 查询拥有积分结构的组织
     * 
     * @param conditions
     * @return
     */
    public R queryOrgInfoForIcNotPage(Map<String, Object> conditions) {
        Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        if (conditions == null) conditions = new HashMap<>();
        if (AdminRoleUtil.isPlantAdmin()) { // 如果是平台管理员
            // 不做任何处理
        } else if (AdminRoleUtil.isOrgAdmin()) { // 如果是组织管理员
            if (sysUser.getOrgId() == null) {
                return R.ok().setCode(100).setMsg("组织管理员请设置所属的组织，如果是党员请加入组织");
            }
            conditions.put("orgInfoId", sysUser.getOrgId());
        } else { // 个人用户，即党员，没有此功能的权限，前台此模块不显示
            return null;
        }

        List<Map<String, Object>> orgInfoForIcs = integralConstituteMapper.queryOrgInfoForIc(conditions);
        if (orgInfoForIcs != null && orgInfoForIcs.size() > 0) {
            return R.ok().setData(orgInfoForIcs).setMsg("查询组织信息成功");
        } else {
            return R.ok().setMsg("没有查询到组织信息");
        }
    }

    /**
     * 查询该组织拥有的党员，仅为党员积分功能服务
     * 
     * @param conditions
     * @return
     */
    public R queryPartyUserInfoAndIcInfo(Map<String, Object> conditions, int pageNum, int pageSize) {
        Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getPrincipal();
        if (conditions == null) conditions = new HashMap<>();
        if (AdminRoleUtil.isPlantAdmin()) { // 如果是平台管理员
            // 不做任何处理
        } else if (AdminRoleUtil.isOrgAdmin()) { // 如果是组织管理员
            if (sysUser.getOrgId() == null) {
                return R.ok().setCode(100).setMsg("组织管理员请设置所属的组织，如果是党员请加入组织");
            }
            conditions.put("orgId", sysUser.getOrgId());
        } else { // 个人用户，即党员
            return null;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> partyUserInfoAndIcInfos =
                integralConstituteMapper.queryPartyUserInfoAndIcInfo(conditions);
        PageInfo<Map<String, Object>> partyUserInfoAndIcInfosPageInfo = new PageInfo<>(partyUserInfoAndIcInfos);
        if (partyUserInfoAndIcInfosPageInfo != null && partyUserInfoAndIcInfosPageInfo.getList() != null
                && partyUserInfoAndIcInfosPageInfo.getList().size() > 0) {
            return R.ok().setData(partyUserInfoAndIcInfosPageInfo).setMsg("查询组织信息成功");
        } else {
            return R.ok().setMsg("没有查询到组织信息");
        }
    }

    /**
     * 查询组织积分信息（为了查询总分） 点击左侧组织导航时，查询该组织积分信息，如果信息异常则报异常提示初始化积分信息
     * 
     * @param conditions
     * @return
     */
    public R queryOrgIntegralInfo(Map<String, Object> conditions) {
        if (conditions == null) conditions = new HashMap<>();
        List<Map<String, Object>> ics = integralConstituteMapper.queryOrgIntegralConstitute(conditions);
        Double integralCount = new Double(0.0);
        Map<String, Object> results = new HashMap<>();
        results.put("integralCount", integralCount);
        try {
            getIntegralInfoChildrens(ics, results, conditions);
            results.put("integralError", false);
        } catch (IntegralErrorException e) { // 如果发现积分有为空的，则要设置积分
            results.put("integralError", true);
        }

        return R.ok().setData(results);
    }

    /**
     * 查询组织积分信息（最后一级）
     * 
     * @param conditions
     * @return
     */
    public R queryOrgIntegralInfo_IcType(Map<String, Object> conditions) {
        List<Map<String, Object>> ics = integralConstituteMapper.queryOrgIntegralConstitute(conditions);
        Map<String, String> icsMap = new HashMap<>();
        List<Map<String, Object>> result = new ArrayList<>();
        if (ics != null && ics.size() > 0) {
            for (Map<String, Object> ic : ics) {
            	//把当过爸爸节点标注出来
                icsMap.put(String.valueOf(ic.get("parentIcId")), "这是当过爸爸节点的id");
            }
            for (Map<String, Object> ic : ics) {
            	//没有找到爸爸就是儿子
                if (icsMap.get(String.valueOf(ic.get("icId"))) == null) {
                    result.add(ic);
                }
            }
        }
        return R.ok().setMsg("查询成功").setData(result);
    }

    /**
     * 得到积分的子节点信息
     * 
     * @param ics
     * @param count
     * @throws IntegralNullException
     */
    private void getIntegralInfoChildrens(List<Map<String, Object>> ics, Map<String, Object> results,
            Map<String, Object> conditions) throws IntegralErrorException {
        if (ics != null && ics.size() > 0) {
            for (Map<String, Object> ic : ics) {
                Map<String, Object> condition = new HashMap<>();
                condition.put("orgId", conditions.get("orgId"));
                condition.put("parentIcId", ic.get("icId"));

                if (ic.get("integral") == null) throw new IntegralErrorException(); // 某一节点如果没有设置积分值，抛异常

                List<Map<String, Object>> icChildrens = integralConstituteMapper.queryOrgIntegralConstitute(condition);
                //非内置积分可以不用设置加/扣分
                if ((icChildrens == null || icChildrens.size() == 0) && "1".equals(String.valueOf(ic.get("isInnerIntegral")))) {
                    // 当为最低级时，查看当前积分节点是否有对应的加分减分方法
                    IntegralChangeType ict = new IntegralChangeType();
                    ict.setIcId(Integer.parseInt(String.valueOf(ic.get("icId"))));
                    ict.setOperation(0); // 是否有扣分处理方式
                    List<IntegralChangeType> icts = integralChangeTypeMapper.queryICT(ict);
                    if (icts == null || icts.size() == 0) throw new IntegralErrorException();
                    icts.clear();
                    ict.setOperation(1); // 是否有加分处理方式
                    icts = integralChangeTypeMapper.queryICT(ict);
                    if (icts == null || icts.size() == 0) throw new IntegralErrorException();

                    BigDecimal bd = new BigDecimal(ic.get("integral").toString());
                    results.put("integralCount",
                            Double.parseDouble(results.get("integralCount").toString()) + bd.doubleValue());
                }
                getIntegralInfoChildrens(icChildrens, results, condition);
            }
        }
    }

    /**
     * 查询组织积分信息（为修改积分服务）
     * 
     * @param conditions
     * @return
     */
    public R queryOrgIntegralConstituteInfo(Map<String, Object> conditions) {
        List<Map<String, Object>> ics = integralConstituteMapper.queryOrgIntegralConstitute(conditions);
        if (ics != null && ics.size() == 1) {
            Map<String, Object> ic = ics.get(0);
            IntegralChangeType ict = new IntegralChangeType();
            ict.setIcId(Integer.parseInt(String.valueOf(ic.get("icId"))));
            ict.setOperation(0); // 是否有扣分处理方式
            List<IntegralChangeType> icts = integralChangeTypeMapper.queryICT(ict);
            ic.put("reduce_operation", "扣分");
            ic.put("add_operation", "加分");
            if (icts != null && icts.size() == 1) {
                IntegralChangeType _ict = icts.get(0);
                ic.put("reduce_ictId", _ict.getIctId());
                ic.put("reduce_integral", _ict.getChangeProposalIntegral());
                ic.put("reduce_describes", _ict.getDescribes() == null ? "" : _ict.getDescribes());
            }
            if (icts != null) icts.clear();
            ict.setOperation(1); // 是否有加分处理方式
            icts = integralChangeTypeMapper.queryICT(ict);
            if (icts != null && icts.size() == 1) {
                IntegralChangeType _ict = icts.get(0);
                ic.put("add_ictId", _ict.getIctId());
                ic.put("add_integral", _ict.getChangeProposalIntegral());
                ic.put("add_describes", _ict.getDescribes() == null ? "" : _ict.getDescribes());
            }
            return R.ok().setData(ic);
        } else {
            return R.error().setMsg("出现错误");
        }
    }

    /**
     * 修改组织积分信息
     * 
     * @param conditions
     * @return
     */
    public R updateOrgIntegralConstituteInfo(Map<String, Object> conditions) {
        if (conditions == null) return R.error().setMsg("发生错误");
        IntegralConstitute ic = new IntegralConstitute();
        if (conditions.get("icId") == null) return R.error().setMsg("发生错误");
        ic.setIcId(Integer.parseInt(String.valueOf(conditions.get("icId"))));
        ic.setType(conditions.get("type") == null ? null : String.valueOf(conditions.get("type")));
        ic.setIntegral(new BigDecimal(String.valueOf(conditions.get("integral"))));
        ic.setDescribes(conditions.get("describes") == null ? null : String.valueOf(conditions.get("describes")));
        integralConstituteMapper.updateByPrimaryKeySelective(ic);

        if (conditions.get("add_integral") != null && conditions.get("add_integral") != "") {
            IntegralChangeType add_ict = new IntegralChangeType();
            add_ict.setIcId(ic.getIcId());
            add_ict.setType("加分");
            add_ict.setChangeProposalIntegral(new BigDecimal(String.valueOf(conditions.get("add_integral"))));
            add_ict.setDescribes(
                    conditions.get("add_describes") == null ? null : String.valueOf(conditions.get("add_describes")));
            add_ict.setOperation(1);
            if (conditions.get("add_ictId") != null && conditions.get("add_ictId") != "") {
                add_ict.setIctId(Integer.parseInt(String.valueOf(conditions.get("add_ictId"))));
                integralChangeTypeMapper.updateByPrimaryKeySelective(add_ict);
            } else {
                integralChangeTypeMapper.insertSelective(add_ict);
            }
        } else {
        	IntegralChangeType add_ict = new IntegralChangeType();
            add_ict.setIcId(ic.getIcId());
            add_ict.setType("加分");
            integralChangeTypeMapper.deleteChangeIntegralIsNull(add_ict);
        }

        if (conditions.get("reduce_integral") != null && conditions.get("reduce_integral") != "") {
            IntegralChangeType reduce_ict = new IntegralChangeType();
            reduce_ict.setIcId(ic.getIcId());
            reduce_ict.setType("扣分");
            reduce_ict.setChangeProposalIntegral(new BigDecimal(String.valueOf(conditions.get("reduce_integral"))));
            reduce_ict.setDescribes(conditions.get("reduce_describes") == null
                    ? null
                    : String.valueOf(conditions.get("reduce_describes")));
            reduce_ict.setOperation(0);
            if (conditions.get("reduce_ictId") != null && conditions.get("reduce_ictId") != "") {
                reduce_ict.setIctId(Integer.parseInt(String.valueOf(conditions.get("reduce_ictId"))));
                integralChangeTypeMapper.updateByPrimaryKeySelective(reduce_ict);
            } else {
                integralChangeTypeMapper.insertSelective(reduce_ict);
            }
        } else {
        	IntegralChangeType add_ict = new IntegralChangeType();
            add_ict.setIcId(ic.getIcId());
            add_ict.setType("扣分");
            integralChangeTypeMapper.deleteChangeIntegralIsNull(add_ict);
        }
        return R.ok().setMsg("修改成功");
    }

    /**
     * 填写积分验证
     * 
     * @param conditions
     * @return
     */
    public R integralValidator(Map<String, Object> conditions) {
        if (conditions == null) return R.error().setData("发生错误");
        Integer score = Integer.parseInt(String.valueOf(conditions.get("score")));
        Integer icId = Integer.parseInt(String.valueOf(conditions.get("icId")));
        conditions.clear();
        conditions.put("icId", icId);
        List<Map<String, Object>> ics = integralConstituteMapper.queryOrgIntegralConstitute(conditions);
        if (ics != null && ics.size() == 1) {
            Map<String, Object> ic = ics.get(0);
            Integer _icId = Integer.parseInt(String.valueOf(ic.get("parentIcId")));
            if (_icId == -1) {
                return R.ok();
            } else {
                conditions.put("icId", _icId);
                List<Map<String, Object>> ics_parents = integralConstituteMapper.queryOrgIntegralConstitute(conditions);
                if (ics_parents != null && ics_parents.size() == 1) {
                    Map<String, Object> ics_parent = ics_parents.get(0);
                    Double count = 0.0;
                    Double addCount = 0.0;
                    if (ics_parent.get("integral") != null && ics_parent.get("integral") != "") {
                        count = new BigDecimal(ics_parent.get("integral").toString()).doubleValue();
                        conditions.clear();
                        conditions.put("parentIcId", ics_parent.get("icId"));
                        List<Map<String, Object>> ics_child =
                                integralConstituteMapper.queryOrgIntegralConstitute(conditions);
                        if (ics_child != null && ics_child.size() > 0) {
                            for (Map<String, Object> map : ics_child) {
                                if (Integer.parseInt(String.valueOf(map.get("icId"))) != icId) {
                                    if (map.get("integral") != null && map.get("integral") != "") {
                                        addCount += new BigDecimal(map.get("integral").toString()).doubleValue();
                                    }
                                }
                            }
                        }
                        addCount += score;
                        if (addCount > count) {
                            return R.ok().setData("子项总分大于父项的分值");
                        } else {
                            return R.ok();
                        }
                    } else {
                        return R.ok().setData("请设置上级积分项的分值");
                    }
                } else {
                    return R.ok().setData("错误");
                }
            }
        } else {
            return R.ok().setData("错误");
        }

    }
}
