package com.zltel.broadcast.um.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyMembershipDuesManage;

public interface PartyMembershipDuesManageService {
	int deleteByPrimaryKey(Integer id);

    int insert(PartyMembershipDuesManage record);

    int insertSelective(PartyMembershipDuesManage record);

    PartyMembershipDuesManage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PartyMembershipDuesManage record);

    int updateByPrimaryKey(PartyMembershipDuesManage record);
    
    /**
     * 查询党费缴纳记录
     * @param conditionMaps
     * @return
     */
    public R queryPartyMembershipDues(Map<String, Object> conditionMaps, int pageNum, int pageSize);
    
    /**
     * 查询党费缴纳记录里的党组织
     * @param conditionMaps
     * @return
     */
    public R queryOrgInfoOfPMDM(Map<String, Object> conditionMaps, int pageNum, int pageSize);
    
    /**
     * 查询党费缴纳记录里的党组织
     * @param conditionMaps
     * @return
     */
    public R queryOrgInfoOfPMDMNotPage(Map<String, Object> conditionMaps);
    
    /**
     * 查询此组织的缴费统计
     * @param conditionMaps
     * @return
     */
    public R queryPMDMChartForOrgInfo(Map<String, Object> conditionMaps);
    
    /**
	 * 下载党费缴纳记录导入excel格式示例
	 * @param baseUser 条件
	 * @return
	 */
    public void exportPMDMExcelExample(HttpServletResponse response);
    
    /**
	 * 批量导入党费缴纳记录
	 * @param file 文件
	 * @return
	 */
    public R importPMDMsExcel(HttpServletResponse response, MultipartFile file);
    
    /**
	 * 批量导出党费缴纳记录
	 * @param response 条件
	 * @return
	 */
    public void exportPMDMExcel(HttpServletResponse response, @RequestParam Map<String, Object> conditionMaps);
}
