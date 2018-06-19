package com.zltel.broadcast.um.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.bean.PartyUserInfo;
import com.zltel.broadcast.um.dao.BaseUserInfoMapper;
import com.zltel.broadcast.um.dao.PartyUserInfoMapper;
import com.zltel.broadcast.um.service.OrganizationRelationService;
import com.zltel.broadcast.um.service.PartyUserInfoService;
import com.zltel.broadcast.um.util.DateUtil;
import com.zltel.broadcast.um.util.FileUtil;

/**
 * 党员信息
 * @author imzhy
 * @since jdk 1.8.0_172
 * Date: 2018.05.29
 */
@Service
@ConfigurationProperties(prefix = "party.user")
public class PartyUserInfoServiceImpl extends BaseDaoImpl<PartyUserInfo> implements PartyUserInfoService {
	
	@Resource
	private PartyUserInfoMapper partyUserInfoMapper;
	
	@Resource
	private BaseUserInfoMapper baseUserInfoMapper;
	
	@Resource
	private OrganizationRelationService organizationRelationService;
	
	@Override
    public BaseDao<PartyUserInfo> getInstince() {
        return this.partyUserInfoMapper;
    }
	
	private String uploadIdPhotoTempPath = "";
	private String uploadIdPhotoPath = "";
	
	/**
	 * 查询党员信息
	 * @param partyUserMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public R queryPartyUserInfos(Map<String, Object> partyUserMap, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> partyUserInfos = partyUserInfoMapper.queryPartyUserInfos(partyUserMap);	//开始查询，没有条件则查询所有组织关系
		PageInfo<Map<String, Object>> partyUserInfosPageInfo = new PageInfo<>(partyUserInfos);
		if (partyUserInfosPageInfo != null && partyUserInfosPageInfo.getList() != null && partyUserInfosPageInfo.getList().size() > 0) {	//是否查询到数据
			for (Map<String, Object> partyUserInfo : partyUserInfos) {
				partyUserInfo.put("birthDate", 
						DateUtil.formatDate(DateUtil.YYYY_MM_DD, partyUserInfo.get("birthDate") == null ||
						partyUserInfo.get("birthDate") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, partyUserInfo.get("birthDate").toString())));
				partyUserInfo.put("enrolmentTime", 
						DateUtil.formatDate(DateUtil.YYYY_MM_DD, partyUserInfo.get("enrolmentTime") == null ||
						partyUserInfo.get("enrolmentTime") == "" ? null : (Date)partyUserInfo.get("enrolmentTime")));
				partyUserInfo.put("graduationTime", 
						DateUtil.formatDate(DateUtil.YYYY_MM_DD, partyUserInfo.get("graduationTime") == null ||
						partyUserInfo.get("graduationTime") == "" ? null : (Date)partyUserInfo.get("graduationTime")));
				partyUserInfo.put("joinDateFormal", 
						DateUtil.formatDate(DateUtil.YYYY_MM_DD, partyUserInfo.get("joinDateFormal") == null ||
						partyUserInfo.get("joinDateFormal") == "" ? null : (Date)partyUserInfo.get("joinDateFormal")));
				partyUserInfo.put("joinDateReserve", 
						DateUtil.formatDate(DateUtil.YYYY_MM_DD, partyUserInfo.get("joinDateReserve") == null ||
						partyUserInfo.get("joinDateReserve") == "" ? null : (Date)partyUserInfo.get("joinDateReserve")));
				partyUserInfo.put("joinWorkDate", 
						DateUtil.formatDate(DateUtil.YYYY_MM_DD, partyUserInfo.get("joinWorkDate") == null ||
						partyUserInfo.get("joinWorkDate") == "" ? null : (Date)partyUserInfo.get("joinWorkDate")));
				partyUserInfo.put("typeName", partyUserInfo.get("type") == null || partyUserInfo.get("type") == "" ? 
						null : (int)partyUserInfo.get("type") == 1 ? "正式党员" : "预备党员");
				partyUserInfo.put("statusName", partyUserInfo.get("type") == null || partyUserInfo.get("type") == "" ? 
						null : (int)partyUserInfo.get("status") == 1 ? "正常" : "停止党籍");
				partyUserInfo.put("partyStaffName", partyUserInfo.get("partyStaff") == null || partyUserInfo.get("partyStaff") == "" ? 
						null : (int)partyUserInfo.get("partyStaff") == 1 ? "是" : "否");
				partyUserInfo.put("partyRepresentativeName", partyUserInfo.get("partyRepresentative") == null || partyUserInfo.get("partyRepresentative") == "" ?
						null : (int)partyUserInfo.get("partyRepresentative") == 1 ? "是" : "否");
				partyUserInfo.put("volunteerName", partyUserInfo.get("volunteer") == null || partyUserInfo.get("volunteer") == "" ?
						null : (int)partyUserInfo.get("volunteer") == 1 ? "是" : "否");
				partyUserInfo.put("difficultUserName", partyUserInfo.get("difficultUser") == null || partyUserInfo.get("difficultUser") == "" ?
						null : (int)partyUserInfo.get("difficultUser") == 1 ? "是" : "否");
				partyUserInfo.put("advancedUserName", partyUserInfo.get("advancedUser") == null || partyUserInfo.get("advancedUser") == "" ?
						null : (int)partyUserInfo.get("advancedUser") == 1 ? "是" : "否");
				partyUserInfo.put("positiveUserName", partyUserInfo.get("positiveUser") == null || partyUserInfo.get("positiveUser") == "" ?
						null : (int)partyUserInfo.get("positiveUser") == 1 ? "是" : "否");
				partyUserInfo.put("developUserName", partyUserInfo.get("developUser") == null || partyUserInfo.get("developUser") == "" ?
						null : (int)partyUserInfo.get("developUser") == 1 ? "是" : "否");
				partyUserInfo.put("missingUserName", partyUserInfo.get("missingUser") == null || partyUserInfo.get("missingUser") == "" ?
						null : (int)partyUserInfo.get("missingUser") == 1 ? "是" : "否");
				Date birthDay = DateUtil.toDate(DateUtil.YYYY_MM_DD, partyUserInfo.get("birthDate") == null || partyUserInfo.get("birthDate") == "" ?
						null : partyUserInfo.get("birthDate").toString());
				partyUserInfo.put("age", getPartyUserAge(birthDay));
			}
			return R.ok().setData(partyUserInfosPageInfo).setMsg("查询党员信息成功");
		} else {
			return R.ok().setMsg("没有查询到党员信息");
		}
	}
	
	/**
	 * 得到党员证件照
	 * @param idPhotoPath 参数
	 * @return
	 */
    public void getPartyUserInfoIdPhoto(HttpServletResponse response, int partyId) throws Exception {
    	Map<String, Object> partyIdMap = new HashMap<String, Object>();
    	partyIdMap.put("id", partyId);
    	List<Map<String, Object>> partyUserInfos = partyUserInfoMapper.queryPartyUserInfos(partyIdMap);
    	if (partyUserInfos != null && !partyUserInfos.isEmpty()) {
    		response.setHeader("Content-Type","image/jped");
        	
    		byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            InputStream bis = null;
            if (StringUtil.isEmpty((String)partyUserInfos.get(0).get("idPhoto"))) {
            	bis = this.getClass().getResourceAsStream("/noFoundIdPhoto.jpg");
            } else {            	
            	bis = new FileInputStream(new File((String)partyUserInfos.get(0).get("idPhoto")));
            }
            try {
    		int i = bis.read(buff);
    		while (i != -1) {
    			os.write(buff, 0, i);
    			os.flush();
    			i = bis.read(buff);
    		}
            }finally {
                IOUtils.closeQuietly(bis);
                IOUtils.closeQuietly(os);
            }
    	}
    }
    
    /**
     * 根据党员生日得到当前年龄
     * @param birthDay
     * @return
     */
    public static Integer getPartyUserAge(Date birthDay) throws Exception {
    	if (birthDay == null) return null;
    	Calendar cal = Calendar.getInstance();  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        
        cal.setTime(birthDay);  
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return age;  
    }
    
    /**
     * 暂时存储用户图片
     * @param request
     * @param file
     * @throws Exception
     */
    public R savePartyUserInfoIdPhoto(HttpServletRequest request, MultipartFile file) throws Exception {
    	String partyUserIdPhotoTempFileName = UUID.randomUUID().toString() + "." + FileUtil.getFileSuffix(file.getOriginalFilename());	//临时文件文件名
    	FileUtil.writeFile(file.getInputStream(), uploadIdPhotoTempPath, partyUserIdPhotoTempFileName);
    	request.getSession().setAttribute("partyUserIdPhotoFileName", file.getOriginalFilename());
    	return R.ok().setData(partyUserIdPhotoTempFileName);	//返回文件名
    }
    
    /**
     * 添加党员
     * @param request
     * @param partyUser
     * @throws Exception
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertPartyUserInfo(HttpServletRequest request, Map<String, Object> partyUser) throws Exception {
    	BaseUserInfo baseUserInfo = new BaseUserInfo();
    	PartyUserInfo partyUserInfo = new PartyUserInfo();
    	//照片地址
    	baseUserInfo.setName((String)partyUser.get("name"));
    	baseUserInfo.setSex((String)partyUser.get("sex"));
    	baseUserInfo.setNativePlace((String)partyUser.get("nativePlace"));
    	baseUserInfo.setBirthDate(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("birthDate")));
    	baseUserInfo.setNation(StringUtil.isEmpty((String)partyUser.get("nation")) ? 
    			null : Integer.parseInt((String)partyUser.get("nation")));
    	baseUserInfo.setIdCard((String)partyUser.get("idCard"));
    	baseUserInfo.setMobilePhone((String)partyUser.get("mobilePhone"));
    	baseUserInfo.setEmail((String)partyUser.get("email"));
    	baseUserInfo.setQq((String)partyUser.get("qq"));
    	baseUserInfo.setWechat((String)partyUser.get("wechat"));
    	baseUserInfo.setEducation(StringUtil.isEmpty((String)partyUser.get("education")) ? 
    			null : Integer.parseInt((String)partyUser.get("education")));
    	baseUserInfo.setAcademicDegree(StringUtil.isEmpty((String)partyUser.get("academicDegree")) ? 
    			null : Integer.parseInt((String)partyUser.get("academicDegree")));
    	baseUserInfo.setEnrolmentTime(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("enrolmentTime")));
    	baseUserInfo.setGraduationTime(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("graduationTime")));
    	baseUserInfo.setGraduationSchool((String)partyUser.get("graduationSchool"));
    	baseUserInfo.setMajor((String)partyUser.get("major"));
    	baseUserInfo.setSpecialityLiterature((String)partyUser.get("specialityLiterature"));
    	baseUserInfo.setSpecialityMajor((String)partyUser.get("specialityMajor"));
    	baseUserInfo.setMarriageStatus((String)partyUser.get("marriageStatus"));
    	baseUserInfo.setChildrenStatus((String)partyUser.get("childrenStatus"));
		baseUserInfo.setHomeAddressProvince((String)partyUser.get("homeAddressProvince"));
		baseUserInfo.setHomeAddressCity((String)partyUser.get("homeAddressCity"));
		baseUserInfo.setHomeAddressArea((String)partyUser.get("homeAddressArea"));
    	baseUserInfo.setHomeAddressDetail((String)partyUser.get("homeAddressDetail"));
		baseUserInfo.setPresentAddressProvince((String)partyUser.get("presentAddressProvince"));
		baseUserInfo.setPresentAddressCity((String)partyUser.get("presentAddressCity"));
		baseUserInfo.setPresentAddressArea((String)partyUser.get("presentAddressArea"));
    	baseUserInfo.setPresentAddressDetail((String)partyUser.get("presentAddressDetail"));
    	baseUserInfo.setIsParty(1);
    	
    	partyUserInfo.setType(StringUtil.isEmpty((String)partyUser.get("type")) ? 
    			null : Integer.parseInt((String)partyUser.get("type")));
    	partyUserInfo.setStatus(StringUtil.isEmpty((String)partyUser.get("status")) ? 
    			null : Integer.parseInt((String)partyUser.get("status")));
    	partyUserInfo.setJoinDateFormal(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("joinDateFormal")));
    	partyUserInfo.setJoinDateReserve(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("joinDateReserve")));
    	partyUserInfo.setWorkUnit((String)partyUser.get("workUnit"));
    	partyUserInfo.setWorkNature(StringUtil.isEmpty((String)partyUser.get("workNature")) ? 
    			null : Integer.parseInt((String)partyUser.get("workNature")));
    	partyUserInfo.setJoinWorkDate(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("joinWorkDate")));
    	partyUserInfo.setAppointmentTimeLength(StringUtil.isEmpty((String)partyUser.get("appointmentTimeLength")) ? 
    			null : Integer.parseInt((String)partyUser.get("appointmentTimeLength")));
    	partyUserInfo.setJoinPartyBranchTypeId(StringUtil.isEmpty((String)partyUser.get("joinPartyBranchType")) ? 
    			null : Integer.parseInt((String)partyUser.get("joinPartyBranchType")));
    	partyUserInfo.setFirstLineSituation(StringUtil.isEmpty((String)partyUser.get("firstLineTypeName")) ? 
    			null : Integer.parseInt((String)partyUser.get("firstLineTypeName")));
    	partyUserInfo.setPartyStaff(StringUtil.isEmpty((String)partyUser.get("partyStaff")) ? 
    			null : Integer.parseInt((String)partyUser.get("partyStaff")));
    	partyUserInfo.setPartyRepresentative(StringUtil.isEmpty((String)partyUser.get("partyRepresentative")) ? 
    			null : Integer.parseInt((String)partyUser.get("partyRepresentative")));
    	partyUserInfo.setVolunteer(StringUtil.isEmpty((String)partyUser.get("volunteer")) ? 
    			null : Integer.parseInt((String)partyUser.get("volunteer")));
    	partyUserInfo.setDifficultUser(StringUtil.isEmpty((String)partyUser.get("difficultUser")) ? 
    			null : Integer.parseInt((String)partyUser.get("difficultUser")));
    	partyUserInfo.setAdvancedUser(StringUtil.isEmpty((String)partyUser.get("advancedUser")) ? 
    			null : Integer.parseInt((String)partyUser.get("advancedUser")));
    	partyUserInfo.setPositiveUser(StringUtil.isEmpty((String)partyUser.get("positiveUser")) ? 
    			null : Integer.parseInt((String)partyUser.get("positiveUser")));
    	partyUserInfo.setDevelopUser(StringUtil.isEmpty((String)partyUser.get("developUser")) ? 
    			null : Integer.parseInt((String)partyUser.get("developUser")));
    	partyUserInfo.setMissingUser(StringUtil.isEmpty((String)partyUser.get("missingUser")) ? 
    			null : Integer.parseInt((String)partyUser.get("missingUser")));
    	partyUserInfo.setIntroduce((String)partyUser.get("introduce"));
    	
    	String partyUserIdPhotoTempFileName = partyUser.get("partyUserIdPhotoTempFileName") == null ? null : partyUser.get("partyUserIdPhotoTempFileName").toString();	//临时照片文件名
    	if (StringUtil.isEmpty(partyUserIdPhotoTempFileName)) {	//没有用户证件照
    		throw new Exception();
    	}
    	
    	String idPhotoPathTemp = uploadIdPhotoTempPath + File.separator + partyUserIdPhotoTempFileName;	//临时照片全路径
    	String idPhotoPath = uploadIdPhotoPath + File.separator + baseUserInfo.getIdCard();	//上传照片文件夹路径
    	String idPhotoName = request.getSession().getAttribute("partyUserIdPhotoFileName").toString();	//上传照片文件名
    	baseUserInfo.setIdPhoto(idPhotoPath + File.separator + idPhotoName);	//上传照片全路径
    	int insertBaseUserInfoCount = baseUserInfoMapper.insertSelective(baseUserInfo);	//保存基础信息
    	List<BaseUserInfo> baseUserInfos = baseUserInfoMapper.queryBaseUserInfos(baseUserInfo);	//查询基础信息id
    	if (insertBaseUserInfoCount != 1 || baseUserInfos == null ? true : baseUserInfos.size() != 1 ? true : false) {	//插入失败，抛异常回滚
    		throw new Exception();
    	}
    	partyUserInfo.setPartyUserId(baseUserInfos.get(0).getBaseUserId());	//设置id
    	int insertPartyUserInfoCount = partyUserInfoMapper.insertSelective(partyUserInfo);	//保存党员信息
    	if (insertPartyUserInfoCount != 1) {	//插入失败，抛异常回滚
    		throw new Exception();
    	}
    	FileUtil.writeFile(new FileInputStream(new File(idPhotoPathTemp)), idPhotoPath, idPhotoName);	//保存证件照
    	return R.ok().setMsg("党员信息注册成功");
    }
    
    /**
     * 修改党员证件照
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updatePartyUserIdPhoto(HttpServletRequest request, MultipartFile file, Map<String, Object> partyUser) throws Exception {
    	List<Map<String, Object>> puiMaps = partyUserInfoMapper.queryPartyUserInfos(partyUser);
    	if (puiMaps != null && puiMaps.size() == 1) {
    		String idPhotoPath = uploadIdPhotoPath + File.separator + puiMaps.get(0).get("idCard");	//上传照片文件夹路径
    		String idPhotoName = file.getOriginalFilename();	//上传照片文件名
    		FileUtil.writeFile(file.getInputStream(), idPhotoPath, idPhotoName);	//保存证件照
    		BaseUserInfo bui = new BaseUserInfo();
    		bui.setBaseUserId(Integer.parseInt(puiMaps.get(0).get("id").toString()));
    		bui.setIdPhoto(idPhotoPath + File.separator + idPhotoName);
    		int updateBaseUserInfoCount = baseUserInfoMapper.updateByPrimaryKeySelective(bui);
    		if (updateBaseUserInfoCount != 1) {	//更新失败，抛异常回滚
        		throw new Exception();
        	}
    		return R.ok().setMsg("证件照更新成功");
    	} else {
    		throw new Exception();
    	}
    }
    
    /**
     * 修改党员信息
     * @param request
     * @param partyUser
     * @throws Exception
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R updatePartyUserInfo(HttpServletRequest request, Map<String, Object> partyUser) throws Exception {
    	BaseUserInfo baseUserInfo = new BaseUserInfo();
    	PartyUserInfo partyUserInfo = new PartyUserInfo();
    	//照片地址
    	baseUserInfo.setBaseUserId(StringUtil.isEmpty((String)partyUser.get("id")) ? 
    			null : Integer.parseInt((String)partyUser.get("id")));
    	baseUserInfo.setName((String)partyUser.get("name"));
    	baseUserInfo.setMobilePhone((String)partyUser.get("mobilePhone"));
    	baseUserInfo.setEmail((String)partyUser.get("email"));
    	baseUserInfo.setQq((String)partyUser.get("qq"));
    	baseUserInfo.setWechat((String)partyUser.get("wechat"));
    	baseUserInfo.setEducation(StringUtil.isEmpty((String)partyUser.get("education")) ? 
    			null : Integer.parseInt((String)partyUser.get("education")));
    	baseUserInfo.setAcademicDegree(StringUtil.isEmpty((String)partyUser.get("academicDegree")) ? 
    			null : Integer.parseInt((String)partyUser.get("academicDegree")));
    	baseUserInfo.setEnrolmentTime(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("enrolmentTime")));
    	baseUserInfo.setGraduationTime(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("graduationTime")));
    	baseUserInfo.setGraduationSchool((String)partyUser.get("graduationSchool"));
    	baseUserInfo.setMajor((String)partyUser.get("major"));
    	baseUserInfo.setSpecialityLiterature((String)partyUser.get("specialityLiterature"));
    	baseUserInfo.setSpecialityMajor((String)partyUser.get("specialityMajor"));
    	baseUserInfo.setMarriageStatus((String)partyUser.get("marriageStatus"));
    	baseUserInfo.setChildrenStatus((String)partyUser.get("childrenStatus"));
		baseUserInfo.setPresentAddressProvince((String)partyUser.get("presentAddressProvince"));
		baseUserInfo.setPresentAddressCity((String)partyUser.get("presentAddressCity"));
		baseUserInfo.setPresentAddressArea((String)partyUser.get("presentAddressArea"));
    	baseUserInfo.setPresentAddressDetail((String)partyUser.get("presentAddressDetail"));
    	
    	partyUserInfo.setPartyUserId(baseUserInfo.getBaseUserId());
    	partyUserInfo.setType(StringUtil.isEmpty((String)partyUser.get("type")) ? 
    			null : Integer.parseInt((String)partyUser.get("type")));
    	partyUserInfo.setStatus(StringUtil.isEmpty((String)partyUser.get("status")) ? 
    			null : Integer.parseInt((String)partyUser.get("status")));
    	partyUserInfo.setJoinDateFormal(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("joinDateFormal")));
    	partyUserInfo.setJoinDateReserve(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("joinDateReserve")));
    	partyUserInfo.setWorkUnit((String)partyUser.get("workUnit"));
    	partyUserInfo.setWorkNature(StringUtil.isEmpty((String)partyUser.get("workNature")) ? 
    			null : Integer.parseInt((String)partyUser.get("workNature")));
    	partyUserInfo.setJoinWorkDate(DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)partyUser.get("joinWorkDate")));
    	partyUserInfo.setAppointmentTimeLength(StringUtil.isEmpty((String)partyUser.get("appointmentTimeLength")) ? 
    			null : Integer.parseInt((String)partyUser.get("appointmentTimeLength")));
    	partyUserInfo.setJoinPartyBranchTypeId(StringUtil.isEmpty((String)partyUser.get("joinPartyBranchType")) ? 
    			null : Integer.parseInt((String)partyUser.get("joinPartyBranchType")));
    	partyUserInfo.setFirstLineSituation(StringUtil.isEmpty((String)partyUser.get("firstLineTypeName")) ? 
    			null : Integer.parseInt((String)partyUser.get("firstLineTypeName")));
    	partyUserInfo.setPartyStaff(StringUtil.isEmpty((String)partyUser.get("partyStaff")) ? 
    			null : Integer.parseInt((String)partyUser.get("partyStaff")));
    	partyUserInfo.setPartyRepresentative(StringUtil.isEmpty((String)partyUser.get("partyRepresentative")) ? 
    			null : Integer.parseInt((String)partyUser.get("partyRepresentative")));
    	partyUserInfo.setVolunteer(StringUtil.isEmpty((String)partyUser.get("volunteer")) ? 
    			null : Integer.parseInt((String)partyUser.get("volunteer")));
    	partyUserInfo.setDifficultUser(StringUtil.isEmpty((String)partyUser.get("difficultUser")) ? 
    			null : Integer.parseInt((String)partyUser.get("difficultUser")));
    	partyUserInfo.setAdvancedUser(StringUtil.isEmpty((String)partyUser.get("advancedUser")) ? 
    			null : Integer.parseInt((String)partyUser.get("advancedUser")));
    	partyUserInfo.setPositiveUser(StringUtil.isEmpty((String)partyUser.get("positiveUser")) ? 
    			null : Integer.parseInt((String)partyUser.get("positiveUser")));
    	partyUserInfo.setDevelopUser(StringUtil.isEmpty((String)partyUser.get("developUser")) ? 
    			null : Integer.parseInt((String)partyUser.get("developUser")));
    	partyUserInfo.setMissingUser(StringUtil.isEmpty((String)partyUser.get("missingUser")) ? 
    			null : Integer.parseInt((String)partyUser.get("missingUser")));
    	partyUserInfo.setIntroduce((String)partyUser.get("introduce"));


    	int insertBaseUserInfoCount = baseUserInfoMapper.updateByPrimaryKeySelective(baseUserInfo);	//更新基础信息
    	if (insertBaseUserInfoCount != 1) {	//更新失败，抛异常回滚
    		throw new Exception();
    	}
    	int insertPartyUserInfoCount = partyUserInfoMapper.updateByPrimaryKeySelective(partyUserInfo);	//更新党员信息
    	if (insertPartyUserInfoCount != 1) {	//更新失败，抛异常回滚
    		throw new Exception();
    	}
    	return R.ok().setMsg("党员信息更新成功");
    }
    
    /**
     * 删除党员
     */
    @Override
    @Transactional(rollbackFor=java.lang.Exception.class)
    public R deletePartyUserInfo(PartyUserInfo partyUserInfo) throws Exception {
    	if(partyUserInfo != null) {
			int countpui = this.deleteByPrimaryKey(partyUserInfo.getPartyUserId());	//开始删除党员用户信息
			int countBui = baseUserInfoMapper.deleteByPrimaryKey(partyUserInfo.getPartyUserId()); //删除基础用户信息
			OrganizationRelation or = new OrganizationRelation();
			or.setOrgRltUserId(partyUserInfo.getPartyUserId());
			organizationRelationService.deleteOrgRelationByUserId(or).get("data");
			
			if (countpui == 1 && countBui == 1) {	//受影响的行数，判断是否全部删除
				return R.ok().setMsg("党员删除成功！");
			} else {	//没有受影响行数或者受影响行数与要删除的用户数量不匹配表示删除失败
				throw new Exception();
			}
		} else {	//删除用户一定需要一个用户信息
			throw new Exception();
		}
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

	public String getUploadIdPhotoTempPath() {
		return uploadIdPhotoTempPath;
	}

	public void setUploadIdPhotoTempPath(String uploadIdPhotoTempPath) {
		this.uploadIdPhotoTempPath = uploadIdPhotoTempPath;
	}

	public String getUploadIdPhotoPath() {
		return uploadIdPhotoPath;
	}

	public void setUploadIdPhotoPath(String uploadIdPhotoPath) {
		this.uploadIdPhotoPath = uploadIdPhotoPath;
	}
}
