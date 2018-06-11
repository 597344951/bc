package com.zltel.broadcast.um.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyUser;

public interface ExcelService {
	int deleteByPrimaryKey(Integer uid);

    int insert(PartyUser record);

    int insertSelective(PartyUser record);

    PartyUser selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(PartyUser record);

    int updateByPrimaryKey(PartyUser record);
    
    /**
	 * 批量导入党员信息
	 * @param baseUser 条件
	 * @return
	 */
    public R importPartyUsersExcel(HttpServletResponse response, MultipartFile file) throws Exception;
    
    /**
	 * 导出党员信息
	 * @param response 条件
	 * @return
	 */
    public R exportPartyUsersExcel(HttpServletResponse response, PartyUser partyUser) throws Exception;
    
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
    public void exportPartyUsersExcelExample(HttpServletResponse response) throws Exception;
}
