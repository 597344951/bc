package com.zltel.broadcast.um.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.bean.PartyUserInfo;

public interface PartyUserInfoService {
	int deleteByPrimaryKey(Integer uid);

    int insert(PartyUserInfo record);

    int insertSelective(PartyUserInfo record);

    PartyUserInfo selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(PartyUserInfo record);

    int updateByPrimaryKey(PartyUserInfo record);
    
    /**
     * 查询党员信息
     * @param partyUserMap
     * @return
     * @throws Exception
     */
    public R queryPartyUserInfos(Map<String, Object> partyUserMap, int pageNum, int pageSize) throws Exception;
    
    
    /**
	 * 得到党员证件照
	 * @param idPhotoPath 参数
	 * @return
	 */
    public void getPartyUserInfoIdPhoto(HttpServletResponse response, int partyId) throws Exception;
    
    /**
     * 暂时存储用户图片
     * @param request
     * @param file
     * @throws Exception
     */
    public R savePartyUserInfoIdPhoto(HttpServletRequest request, MultipartFile file) throws Exception;
    
    /**
     * 添加党员
     * @param request
     * @param partyUser
     * @throws Exception
     */
    public R insertPartyUserInfo(HttpServletRequest request, Map<String, Object> partyUser) throws Exception;
    
    /**
     * 修改党员证件照
     * @param request
     * @param file
     * @param partyUserId
     * @return
     * @throws Exception
     */
    public R updatePartyUserIdPhoto(HttpServletRequest request, MultipartFile file, Map<String, Object> partyUser) throws Exception;
    
    /**
     * 修改党员信息
     * @param request
     * @param partyUser
     * @throws Exception
     */
    public R updatePartyUserInfo(HttpServletRequest request, Map<String, Object> partyUser) throws Exception;
    
    /**
     * 删除用户
     * @param partyUserInfo
     * @return
     * @throws Exception
     */
    public R deletePartyUserInfo(BaseUserInfo baseUserInfo) throws Exception;
}
