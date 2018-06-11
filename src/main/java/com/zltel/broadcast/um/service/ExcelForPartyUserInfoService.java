package com.zltel.broadcast.um.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyUserInfo;

public interface ExcelForPartyUserInfoService {
	int deleteByPrimaryKey(Integer uid);

    int insert(Object record);

    int insertSelective(Object record);

    Object selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(Object record);

    int updateByPrimaryKey(Object record);
    
    /**
	 * 批量导入党员信息
	 * @param baseUser 条件
	 * @return
	 */
    public R importPartyUserInfosExcel(HttpServletResponse response, MultipartFile file) throws Exception;
    
    /**
	 * 导出党员信息
	 * @param response 条件
	 * @return
	 */
    public R exportPartyUserInfosExcel(HttpServletResponse response, PartyUserInfo partyUserInfo) throws Exception;
    
    /**
	 * 下载错误信息
	 * @param response 条件
	 * @return
	 */
    public R downloadValidataMsg(HttpServletResponse response) throws Exception;
    
    /**
	 * 下载党员导入excel格式示例
	 * @param baseUser 条件
	 * @return
	 */
    public void exportPartyUserInfosExcelExample(HttpServletResponse response) throws Exception;
}
