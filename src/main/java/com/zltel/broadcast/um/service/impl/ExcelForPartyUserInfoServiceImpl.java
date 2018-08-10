package com.zltel.broadcast.um.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.util.PasswordHelper;
import com.zltel.broadcast.um.bean.AcademicDegreeLevel;
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.bean.EducationLevel;
import com.zltel.broadcast.um.bean.FirstLineType;
import com.zltel.broadcast.um.bean.JoinPartyBranchType;
import com.zltel.broadcast.um.bean.NationType;
import com.zltel.broadcast.um.bean.OrganizationDuty;
import com.zltel.broadcast.um.bean.OrganizationInformation;
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.bean.PartyUserInfo;
import com.zltel.broadcast.um.bean.SysRole;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.bean.SysUserRole;
import com.zltel.broadcast.um.bean.WorkNatureType;
import com.zltel.broadcast.um.dao.AcademicDegreeLevelMapper;
import com.zltel.broadcast.um.dao.BaseUserInfoMapper;
import com.zltel.broadcast.um.dao.EducationLevelMapper;
import com.zltel.broadcast.um.dao.FirstLineTypeMapper;
import com.zltel.broadcast.um.dao.JoinPartyBranchTypeMapper;
import com.zltel.broadcast.um.dao.NationTypeMapper;
import com.zltel.broadcast.um.dao.OrganizationDutyMapper;
import com.zltel.broadcast.um.dao.OrganizationInformationMapper;
import com.zltel.broadcast.um.dao.OrganizationRelationMapper;
import com.zltel.broadcast.um.dao.PartyUserInfoMapper;
import com.zltel.broadcast.um.dao.SysRoleMapper;
import com.zltel.broadcast.um.dao.SysUserMapper;
import com.zltel.broadcast.um.dao.SysUserRoleMapper;
import com.zltel.broadcast.um.dao.WorkNatureTypeMapper;
import com.zltel.broadcast.um.service.ExcelForPartyUserInfoService;
import com.zltel.broadcast.um.util.DateUtil;
import com.zltel.broadcast.um.util.FileUtil;
import com.zltel.broadcast.um.util.RegexUtil;

@Service
public class ExcelForPartyUserInfoServiceImpl extends BaseDaoImpl<Object> implements ExcelForPartyUserInfoService {
    
    private static final Logger logout = LoggerFactory.getLogger(ExcelForPartyUserInfoServiceImpl.class);

	@Autowired
	private PartyUserInfoMapper partyUserInfoMapper;
	@Autowired
	private BaseUserInfoMapper baseUserInfoMapper;
	
	@Autowired
	private NationTypeMapper nationTypeMapper;
	@Autowired
	private EducationLevelMapper educationLevelMapper;
	@Autowired
	private AcademicDegreeLevelMapper academicDegreeLevelMapper;
	@Autowired
	private WorkNatureTypeMapper workNatureTypeMapper;
	@Autowired
	private FirstLineTypeMapper firstLineTypeMapper;
	@Autowired
	private JoinPartyBranchTypeMapper joinPartyBranchTypeMapper;
	@Autowired
	private OrganizationRelationMapper organizationRelationMapper;
	@Autowired
	private OrganizationInformationMapper organizationInformationMapper;
	@Autowired
	private OrganizationDutyMapper organizationDutyMapper;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;

	private final static String OFFICE_EXCEL_2003 = "XLS";
	private final static String OFFICE_EXCEL_2010 = "XLSX";
	
	@Override
    public BaseDao<Object> getInstince() {
        return null;
    }
	
	/**
	 * 批量导入党员信息
	 * @param baseUser 条件
	 * @return
	 */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R importPartyUserInfosExcel(HttpServletResponse response, MultipartFile file) throws Exception {
		Workbook wb = null;
		if (file == null) return R.error().setMsg("导入失败").setData("请选择导入文件");
		wb = this.createWorkboot(this.getExcelSuffix(file.getOriginalFilename()), file.getInputStream());
		Sheet hs = wb.getSheetAt(0);	//得到第一页
		Map<Integer, BaseUserInfo> baseUserInfoMaps = new HashMap<>();
		Map<Integer, PartyUserInfo> partyUserInfoMaps = new HashMap<>();
		Map<Integer, OrganizationRelation> orgRelationMaps = new HashMap<>();
		Map<Integer, SysUser> sysUsersMaps = new HashMap<>();
		StringBuffer validataErrorMsg = new StringBuffer();	//保存错误信息
		boolean thisValidateSuccess = validateImportPartyUserInfosExcel(hs, baseUserInfoMaps
				, partyUserInfoMaps, orgRelationMaps, validataErrorMsg, sysUsersMaps);	//本次验证是否通过
		if(!thisValidateSuccess) {
			return R.error().setMsg("导入失败，请查看失败信息 ：导入错误信息.txt").setData(validataErrorMsg);
		}
		
		//开始添加
		if (baseUserInfoMaps.keySet().size() != 0) {
			for (Integer num : baseUserInfoMaps.keySet()) {
				BaseUserInfo bui = baseUserInfoMaps.get(num);
				int baseUserInfoCount = baseUserInfoMapper.insertSelective(bui);
				if (baseUserInfoCount != 1) {
					throw new Exception();
				}
				bui = baseUserInfoMapper.queryBaseUserInfos(bui).get(0);
				PartyUserInfo pui = partyUserInfoMaps.get(num);
				pui.setPartyUserId(bui.getBaseUserId());
				int partyUserInfoCount = partyUserInfoMapper.insertSelective(pui);
				if (partyUserInfoCount != 1) {
					throw new Exception();
				}
				
				SysUser su = sysUsersMaps.get(num);
				if (su != null) {
					int count = sysUserMapper.insertSelective(su);
					if (count != 1) {
						throw new Exception();
					}
				}
				//赋予默认角色
				SysRole sysRole = new SysRole();
				sysRole.setRoleName("party_role");
				List<SysRole> srs = sysRoleMapper.querySysRoles(sysRole);
				if (srs != null && srs.size() == 1) {
					SysUserRole sur = new SysUserRole();
					sur.setUserId((long)su.getUserId());
					sur.setRoleId(srs.get(0).getRoleId());
					int count = sysUserRoleMapper.insertSelective(sur);
					if (count != 1) {
						throw new Exception();
					}
				} else {
					throw new Exception();
				}
				
				OrganizationRelation orgR = orgRelationMaps.get(num);
				if (orgR != null) {
					orgR.setOrgRltUserId(bui.getBaseUserId());
					int orgRelationCount = organizationRelationMapper.insertSelective(orgR);
					if (orgRelationCount != 1) {
						throw new Exception();
					}
				}
			}
		} else {
			return R.error().setMsg("没有要导入的用户信息");
		}
		
		return R.ok().setMsg("信息导入成功");
    }
	
	/**
	 * 党员导入校验
	 * @param hs
	 * @param partys
	 * @return
	 */
	private boolean validateImportPartyUserInfosExcel(Sheet hs, Map<Integer, BaseUserInfo> baseUserInfoMaps, 
			Map<Integer, PartyUserInfo> partyUserInfoMaps, Map<Integer, OrganizationRelation> orgRelationMaps, StringBuffer validataErrorMsg, 
			Map<Integer, SysUser> sysUsersMaps) throws Exception {
		boolean thisValidateSuccess = true;
		for (int i = 3; ; i++) {	//从第4行开始读取
			Row row = hs.getRow(i);
			if(row == null) {
				break;
			}
			BaseUserInfo baseUserInfo = new BaseUserInfo();
			PartyUserInfo partyUserInfo = new PartyUserInfo();
			OrganizationRelation orgRelation = new OrganizationRelation();
			
			//姓名
			if (row.getCell(0) != null && StringUtil.isNotEmpty(row.getCell(0).getStringCellValue())) {
				if (RegexUtil.isChinaName(row.getCell(0).getStringCellValue())) {					
					baseUserInfo.setName(row.getCell(0).getStringCellValue());	//用户名字
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行用户名不合法。\r\n");
				}
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行用户名不能为空。\r\n");
			}
			//性别
			if (row.getCell(1) != null && StringUtil.isNotEmpty(row.getCell(1).getStringCellValue())) {
				if (row.getCell(1).getStringCellValue().equals("男") || row.getCell(1).getStringCellValue().equals("女")) {
					baseUserInfo.setSex(row.getCell(1).getStringCellValue());	//用户性别
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行用户不能出现第三性别。\r\n");
				}
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行用户性别不能为空。\r\n");
			}
			//民族
			if (row.getCell(2) != null && StringUtil.isNotEmpty(row.getCell(2).getStringCellValue())) {
				NationType nt = new NationType();
				nt.setName(row.getCell(2).getStringCellValue());
				List<NationType> nts = nationTypeMapper.queryNationType(nt);
				if (nts != null && nts.size() > 0) {
					baseUserInfo.setNation(nts.get(0).getNtId());
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行民族输入有误。\r\n");
				}
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行民族不能为空。\r\n");
			}
			//身份证号码
			if (row.getCell(3) != null && StringUtil.isNotEmpty(row.getCell(3).getStringCellValue())) {
				String idCard = row.getCell(3).getStringCellValue();
				if (RegexUtil.isIDCard(idCard)) {					
					BaseUserInfo bui = new BaseUserInfo();
					bui.setIdCard(idCard);
					List<BaseUserInfo> buis = baseUserInfoMapper.queryBaseUserInfos(bui);
					if (buis != null && buis.size() > 0) {
						thisValidateSuccess = false;
						validataErrorMsg.append("第" + (i + 1) + "行身份证号码重复，该党员已存在。\r\n");
					} else {
						String year = "";
						String month = "";
						String day = "";
						if (idCard.length() == 15) {
							year = "19" + idCard.substring(6,8);
							month = idCard.substring(8,10);
							day = idCard.substring(10,12);
						} else if (idCard.length() == 18) {
							year = idCard.substring(6,10);
							month = idCard.substring(10,12);
							day = idCard.substring(12,14);
						}
						String birthDate = year + "-" + month + "-" + day;
						if (RegexUtil.isDate(birthDate)) {
							Date birthDay = DateUtil.toDate(DateUtil.YYYY_MM_DD, birthDate);	//生日
							int age = PartyUserInfoServiceImpl.getPartyUserAge(birthDay);
							if (age >= 18 && age < 120) {	//理论正确年龄
								baseUserInfo.setIdCard(idCard);
								baseUserInfo.setBirthDate(birthDay);
							} else {
								thisValidateSuccess = false;
								validataErrorMsg.append("第" + (i + 1) + "行身份证号码格式不正确。\r\n");
							}
						} else {	//不是一个正确的日期
							thisValidateSuccess = false;
							validataErrorMsg.append("第" + (i + 1) + "行身份证号码格式不正确。\r\n");
						}
					}
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行身份证号码格式不正确。\r\n");
				}
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行身份证号码不能为空。\r\n");
			}
			//手机号码
			if (row.getCell(4) != null && StringUtil.isNotEmpty(row.getCell(4).getStringCellValue())) {
				if (RegexUtil.isMobilePhone(row.getCell(4).getStringCellValue())) {
					baseUserInfo.setMobilePhone(row.getCell(4).getStringCellValue());
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行手机号码格式不正确。\r\n");
				}
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行手机号码不能为空。\r\n");
			}
			//QQ
			if (row.getCell(5) != null && StringUtil.isNotEmpty(row.getCell(5).getStringCellValue())) {
				if (RegexUtil.isQq(row.getCell(5).getStringCellValue())) {
					baseUserInfo.setQq(row.getCell(5).getStringCellValue());
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行QQ号码格式不正确。\r\n");
				}
			} else {
				baseUserInfo.setQq(null);
			}
			//微信
			if (row.getCell(6) != null && StringUtil.isNotEmpty(row.getCell(6).getStringCellValue())) {
				if (RegexUtil.isWechat(row.getCell(6).getStringCellValue())) {
					baseUserInfo.setWechat(row.getCell(6).getStringCellValue());
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行微信格式不正确。\r\n");
				}
			} else {
				baseUserInfo.setWechat(null);
			}
			//邮箱
			if (row.getCell(7) != null && StringUtil.isNotEmpty(row.getCell(7).getStringCellValue())) {
				if (RegexUtil.isEmail(row.getCell(7).getStringCellValue())) {
					baseUserInfo.setEmail(row.getCell(7).getStringCellValue());
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行邮箱格式不正确。\r\n");
				}
			} else {
				baseUserInfo.setEmail(null);
			}
			//入学时间
			if (row.getCell(8) != null) {
				baseUserInfo.setEnrolmentTime(row.getCell(8).getDateCellValue());
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行入学日期不能为空。\r\n");
			}
			//毕业时间
			if (row.getCell(9) != null) {
				baseUserInfo.setGraduationTime(row.getCell(9).getDateCellValue());
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行毕业日期不能为空。\r\n");
			}
			//毕业学校
			if (row.getCell(10) != null && StringUtil.isNotEmpty(row.getCell(10).getStringCellValue())) {
				baseUserInfo.setGraduationSchool(row.getCell(10).getStringCellValue());
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行毕业学校不能为空。\r\n");
			}
			//学习专业
			if (row.getCell(11) != null && StringUtil.isNotEmpty(row.getCell(11).getStringCellValue())) {
				baseUserInfo.setMajor(row.getCell(11).getStringCellValue());
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行学习专业不能为空。\r\n");
			}
			//学历
			if (row.getCell(12) != null && StringUtil.isNotEmpty(row.getCell(12).getStringCellValue())) {
				EducationLevel el = new EducationLevel();
				el.setName(row.getCell(12).getStringCellValue());
				List<EducationLevel> els = educationLevelMapper.queryEducationLevels(el);
				if(els != null && els.size() == 1) {
					baseUserInfo.setEducation(els.get(0).getEduLevelEid());
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行学历填写错误。\r\n");
				}
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行学历不能为空。\r\n");
			}
			//学位
			if (row.getCell(13) != null && StringUtil.isNotEmpty(row.getCell(13).getStringCellValue())) {
				AcademicDegreeLevel adl = new AcademicDegreeLevel();
				adl.setName(row.getCell(13).getStringCellValue());
				List<AcademicDegreeLevel> adls = academicDegreeLevelMapper.queryAcademicDegreeLevels(adl);
				if(adls != null && adls.size() == 1) {
					baseUserInfo.setAcademicDegree(adls.get(0).getAdDAid());
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行学位填写错误。\r\n");
				}
			} else {
				baseUserInfo.setAcademicDegree(null);
			}
			//家庭住址
			if (row.getCell(14) != null && StringUtil.isNotEmpty(row.getCell(14).getStringCellValue())) {
				String[] homeAddress = row.getCell(14).getStringCellValue().split("-");
				if(homeAddress.length == 4) {
					baseUserInfo.setHomeAddressProvince(homeAddress[0]);
					baseUserInfo.setHomeAddressCity(homeAddress[1]);
					baseUserInfo.setHomeAddressArea(homeAddress[2]);
					baseUserInfo.setHomeAddressDetail(homeAddress[3]);
					baseUserInfo.setNativePlace(homeAddress[0] + "-" + homeAddress[1]);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行家庭地址填写错误。\r\n");
				}
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行家庭地址不能为空。\r\n");
			}
			//现居住地址
			if (row.getCell(15) != null && StringUtil.isNotEmpty(row.getCell(15).getStringCellValue())) {
				String[] presentAddress = row.getCell(15).getStringCellValue().split("-");
				if(presentAddress.length == 4) {
					baseUserInfo.setPresentAddressProvince(presentAddress[0]);
					baseUserInfo.setPresentAddressCity(presentAddress[1]);
					baseUserInfo.setPresentAddressArea(presentAddress[2]);
					baseUserInfo.setPresentAddressDetail(presentAddress[3]);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行现居住地址填写错误。\r\n");
				}
			} else {
				baseUserInfo.setPresentAddressProvince(null);
				baseUserInfo.setPresentAddressCity(null);
				baseUserInfo.setPresentAddressArea(null);
				baseUserInfo.setPresentAddressDetail(null);
			}
			if (row.getCell(16) != null) {
				baseUserInfo.setSpecialityLiterature(row.getCell(16).getStringCellValue());
			}
			if (row.getCell(17) != null) {
				baseUserInfo.setSpecialityMajor(row.getCell(17).getStringCellValue());
			}
			if (row.getCell(18) != null) {
				baseUserInfo.setMarriageStatus(row.getCell(18).getStringCellValue());
			}
			if (row.getCell(19) != null) {
				baseUserInfo.setChildrenStatus(row.getCell(19).getStringCellValue());
			}
			//工作单位
			if (row.getCell(20) != null) {				
				baseUserInfo.setWorkUnit(row.getCell(20).getStringCellValue());
			}
			//工作性质
			if (row.getCell(21) != null && StringUtil.isNotEmpty(row.getCell(21).getStringCellValue())) {
				WorkNatureType wnt = new WorkNatureType();
				wnt.setName(row.getCell(21).getStringCellValue());
				List<WorkNatureType> wnts = workNatureTypeMapper.queryWorkNatureTypes(wnt);
				if (wnts != null && wnts .size() == 1) {
					baseUserInfo.setWorkNature(wnts.get(0).getWorkNatureId());
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行工作性质选择有误。\r\n");
				}
			} else {
				baseUserInfo.setWorkNature(null);
			}
			//参加工作时间
			if (row.getCell(22) != null) {
				baseUserInfo.setJoinWorkDate(row.getCell(22).getDateCellValue());
			} else {
				baseUserInfo.setJoinWorkDate(null);
			}
			//参加工作时长
			if (row.getCell(23) != null) {
				try {
					baseUserInfo.setAppointmentTimeLength(Integer.parseInt(row.getCell(23).getStringCellValue()));
				} catch (Exception e) {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行请填写正确的工作时长。\r\n");
					logout.error(e.getMessage(),e);
				}
			} else {
				baseUserInfo.setAppointmentTimeLength(null);
			}
			//一线情况
			if (row.getCell(24) != null && StringUtil.isNotEmpty(row.getCell(24).getStringCellValue())) {
				FirstLineType flt = new FirstLineType();
				flt.setFirstLineTypeName(row.getCell(24).getStringCellValue());
				List<FirstLineType> flts = firstLineTypeMapper.queryFirstLineTypes(flt);
				if (flts != null && flts .size() == 1) {
					baseUserInfo.setFirstLineSituation(flts.get(0).getFltId());
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行加工作一线情况选择有误。\r\n");
				}
			} else {
				baseUserInfo.setFirstLineSituation(null);
			}
			//是否积极份子
			if (row.getCell(25) != null && StringUtil.isNotEmpty(row.getCell(25).getStringCellValue())) {
				if ("是".equals(row.getCell(25).getStringCellValue())) {
					baseUserInfo.setPositiveUser(1);
				} else if ("否".equals(row.getCell(25).getStringCellValue())) {
					baseUserInfo.setPositiveUser(0);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行是否积极党员选择有误。\r\n");
				}
			} else {
				baseUserInfo.setPositiveUser(null);
			}
			
			//党员类型
			if (row.getCell(26) != null && StringUtil.isNotEmpty(row.getCell(26).getStringCellValue())) {
				if ("正式党员".equals(row.getCell(26).getStringCellValue())) {
					partyUserInfo.setType(1);
				} else if ("预备党员".equals(row.getCell(26).getStringCellValue())) {
					partyUserInfo.setType(0);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行党员类型填写错误。\r\n");
				}
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行党员类型不能为空。\r\n");
			}
			//党员状态
			if (row.getCell(27) != null && StringUtil.isNotEmpty(row.getCell(27).getStringCellValue())) {
				if ("正常".equals(row.getCell(27).getStringCellValue())) {
					partyUserInfo.setStatus(1);
				} else if ("停止党籍".equals(row.getCell(27).getStringCellValue())) {
					partyUserInfo.setStatus(0);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行党员状态填写错误。\r\n");
				}
			} else {
				thisValidateSuccess = false;
				validataErrorMsg.append("第" + (i + 1) + "行党员状态不能为空。\r\n");
			}
			//入党时间
			if (row.getCell(28) != null) {
				partyUserInfo.setJoinDateFormal(row.getCell(28).getDateCellValue());
			} else {
				//两个时间必有一个
				if (row.getCell(29) != null) {
					
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行请填写入党时间或预备党员入党时间。\r\n");
				}
			}
			//预备党员入党时间
			if (row.getCell(29) != null) {
				partyUserInfo.setJoinDateReserve(row.getCell(29).getDateCellValue());
			} else {
				//两个时间必有一个
				if (row.getCell(28) != null) {
					
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行请填写入党时间或预备党员入党时间。\r\n");
				}
			}
			//如何加入党支部
			if (row.getCell(30) != null && StringUtil.isNotEmpty(row.getCell(30).getStringCellValue())) {
				JoinPartyBranchType jpbt = new JoinPartyBranchType();
				jpbt.setJoinType(row.getCell(30).getStringCellValue());
				List<JoinPartyBranchType> jpbts = joinPartyBranchTypeMapper.queryJoinPartyBranchTypes(jpbt);
				if (jpbts != null && jpbts .size() == 1) {
					partyUserInfo.setJoinPartyBranchTypeId(jpbts.get(0).getJpbtId());
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行加入党支部方式选择有误。\r\n");
				}
			} else {
				partyUserInfo.setJoinPartyBranchTypeId(null);
			}
			//是否党务工作者
			if (row.getCell(31) != null && StringUtil.isNotEmpty(row.getCell(31).getStringCellValue())) {
				if ("是".equals(row.getCell(31).getStringCellValue())) {
					partyUserInfo.setPartyStaff(1);
				} else if ("否".equals(row.getCell(31).getStringCellValue())) {
					partyUserInfo.setPartyStaff(0);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行是否党务工作者选择有误。\r\n");
				}
			} else {
				partyUserInfo.setPartyStaff(null);
			}
			//是否党代表
			if (row.getCell(32) != null && StringUtil.isNotEmpty(row.getCell(32).getStringCellValue())) {
				if ("是".equals(row.getCell(32).getStringCellValue())) {
					partyUserInfo.setPartyRepresentative(1);
				} else if ("否".equals(row.getCell(32).getStringCellValue())) {
					partyUserInfo.setPartyRepresentative(0);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行是否党务代表选择有误。\r\n");
				}
			} else {
				partyUserInfo.setPartyRepresentative(null);
			}
			//是否志愿者
			if (row.getCell(33) != null && StringUtil.isNotEmpty(row.getCell(33).getStringCellValue())) {
				if ("是".equals(row.getCell(33).getStringCellValue())) {
					partyUserInfo.setVolunteer(1);
				} else if ("否".equals(row.getCell(33).getStringCellValue())) {
					partyUserInfo.setVolunteer(0);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行是否志愿者选择有误。\r\n");
				}
			} else {
				partyUserInfo.setVolunteer(null);
			}
			//是否困难党员
			if (row.getCell(34) != null && StringUtil.isNotEmpty(row.getCell(34).getStringCellValue())) {
				if ("是".equals(row.getCell(34).getStringCellValue())) {
					partyUserInfo.setDifficultUser(1);
				} else if ("否".equals(row.getCell(34).getStringCellValue())) {
					partyUserInfo.setDifficultUser(0);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行是否困难党员选择有误。\r\n");
				}
			} else {
				partyUserInfo.setDifficultUser(null);
			}
			//是否先进党员
			if (row.getCell(35) != null && StringUtil.isNotEmpty(row.getCell(35).getStringCellValue())) {
				if ("是".equals(row.getCell(35).getStringCellValue())) {
					partyUserInfo.setAdvancedUser(1);
				} else if ("否".equals(row.getCell(35).getStringCellValue())) {
					partyUserInfo.setAdvancedUser(0);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行是否先进党员选择有误。\r\n");
				}
			} else {
				partyUserInfo.setAdvancedUser(null);
			}
			//是否发展党员
			if (row.getCell(36) != null && StringUtil.isNotEmpty(row.getCell(36).getStringCellValue())) {
				if ("是".equals(row.getCell(36).getStringCellValue())) {
					partyUserInfo.setDevelopUser(1);
				} else if ("否".equals(row.getCell(36).getStringCellValue())) {
					partyUserInfo.setDevelopUser(0);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行是否发展党员选择有误。\r\n");
				}
			} else {
				partyUserInfo.setDevelopUser(null);
			}
			//是否失联党员
			if (row.getCell(37) != null && StringUtil.isNotEmpty(row.getCell(37).getStringCellValue())) {
				if ("是".equals(row.getCell(37).getStringCellValue())) {
					partyUserInfo.setMissingUser(1);
				} else if ("否".equals(row.getCell(37).getStringCellValue())) {
					partyUserInfo.setMissingUser(0);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行是否失联党员选择有误。\r\n");
				}
			} else {
				partyUserInfo.setMissingUser(null);
			}
			//党员介绍
			if (row.getCell(38) != null) {
				partyUserInfo.setIntroduce(row.getCell(38).getStringCellValue());
			}
			
			//组织编号
			if (row.getCell(39) != null && row.getCell(40) != null) {
				Integer orgRltInfoId;
				Integer orgRltDutyId;
				try {
					orgRltInfoId = Integer.parseInt(row.getCell(39).getStringCellValue());
					orgRltDutyId = Integer.parseInt(row.getCell(40).getStringCellValue());
				} catch (Exception e) {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行请填写正确的组织编号和职责编号。\r\n");
					logout.error(e.getMessage(),e);
					break;
				}
				OrganizationInformation orgInfo = new OrganizationInformation();
				orgInfo.setOrgInfoId(orgRltInfoId);
				List<OrganizationInformation> orgInfos = organizationInformationMapper.queryOrgInfos(orgInfo);
				if (orgInfos != null && orgInfos.size() == 1) {
					orgRelation.setOrgRltInfoId(orgRltInfoId);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行没有查询到该组织\r\n");
				}
				OrganizationDuty orgDuty = new OrganizationDuty();
				orgDuty.setOrgDutyOrgInfoId(orgRltInfoId);
				orgDuty.setOrgDutyId(orgRltDutyId);
				List<OrganizationDuty> orgDutys = organizationDutyMapper.queryOrgDutys(orgDuty);
				if (orgDutys != null && orgDutys.size() == 1) {
					orgRelation.setOrgRltDutyId(orgRltDutyId);
				} else {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行在组织里不存在此职位\r\n");
				}
			} else {
				if (row.getCell(39) == null && row.getCell(40) != null) {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行请填写所在组织编号。\r\n");
				} else if (row.getCell(39) != null && row.getCell(40) == null) {
					thisValidateSuccess = false;
					validataErrorMsg.append("第" + (i + 1) + "行请填写所在组织职责。\r\n");
				}
			}
			
			//加入组织时间
			if (row.getCell(41) != null) {
				orgRelation.setOrgRltJoinTime(row.getCell(41).getDateCellValue());
			} else {
				orgRelation.setOrgRltJoinTime(new Date());
			}
			
			baseUserInfo.setIsParty(1);
			
			if (baseUserInfo.getIsParty() == 1) {
				SysUser su = new SysUser();
	        	su.setUsername(baseUserInfo.getIdCard());
	        	su.setPassword(baseUserInfo.getIdCard().substring(baseUserInfo.getIdCard().length() - 6));
	        	String salt = UUID.randomUUID().toString();
	        	su.setSalt(salt);	//保存盐
	        	su.setPassword(PasswordHelper.encryptPassword(su.getPassword(), salt));	//加密
	        	su.setEmail(baseUserInfo.getEmail());
	        	su.setMobile(baseUserInfo.getMobilePhone());
	        	su.setStatus(true);
	        	su.setUserType(1);
	        	su.setCreateTime(new Date());
	        	sysUsersMaps.put(i, su);
			}
			
			baseUserInfoMaps.put(i, baseUserInfo);
			partyUserInfoMaps.put(i, partyUserInfo);
			if (orgRelation.getOrgRltDutyId() != null && orgRelation.getOrgRltInfoId() != null) {
				orgRelationMaps.put(i, orgRelation);
			}
			
		}
		
		return thisValidateSuccess;
	}
    
    /**
	 * 导出党员信息
	 * @param response 条件
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R exportPartyUserInfosExcel(HttpServletResponse response, PartyUserInfo partyUserInfo) throws Exception {
		if (partyUserInfo == null) 
			return R.error().setMsg("请选择党员");
		Map<String, Object> partyUserMap = new HashMap<>();
		partyUserMap.put("id", partyUserInfo.getPartyUserId());
		Map<String, Object> partyUserInfoMaps = partyUserInfoMapper.queryPartyUserInfos(partyUserMap).get(0);
		
		String fn = "/exportPartyUserInfoExcel.xlsx";
        Workbook wb   = this.createWorkboot(ExcelForPartyUserInfoServiceImpl.OFFICE_EXCEL_2010, 
				this.getClass().getResourceAsStream(fn));
		if(wb == null)throw new RRException("找不到文件:"+fn);
		Sheet hs = wb.getSheetAt(0);	//得到第一页
		Row row1 = hs.getRow(0);
    	row1.getCell(0).setCellValue("党员信息-"+partyUserInfoMaps.get("name"));	//标题，模板有内容，用getCell
    	
    	CellStyle cellCenterStyle = wb.createCellStyle();	//设置一个居中样式
    	cellCenterStyle.setAlignment(HorizontalAlignment.CENTER);
    	
    	InputStream bis = null;
    	try {
			//设置党员头像
			CreationHelper helper = wb.getCreationHelper();  
			Drawing drawing = hs.createDrawingPatriarch(); 
			ClientAnchor anchor = helper.createClientAnchor();
			if (StringUtil.isEmpty((String)partyUserInfoMaps.get("idPhoto"))) {
            	bis = this.getClass().getResourceAsStream("/noFoundIdPhoto.jpg");
            } else {            	
            	bis = new FileInputStream(new File((String)partyUserInfoMaps.get("idPhoto")));
            }
			byte[] bytes = IOUtils.toByteArray(bis);
			int pictureIdx= wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			anchor.setDx1(0);
			anchor.setDy1(0);
			anchor.setDx2(0);
			anchor.setDy2(0);
			anchor.setCol1(1);  
			anchor.setRow1(2);
			anchor.setCol2(2);  
			anchor.setRow2(17);
			drawing.createPicture(anchor, pictureIdx);
		} catch (Exception e) {
			logout.error(e.getMessage());
		} finally {
			FileUtil.closeIO(bis);
		}
        
        Row row3 = hs.getRow(2);
        setCell(row3, 3, cellCenterStyle, partyUserInfoMaps.get("name"));
        setCell(row3, 5, cellCenterStyle, partyUserInfoMaps.get("sex"));
        setCell(row3, 7, cellCenterStyle, partyUserInfoMaps.get("birthDate"));
        
        Row row4 = hs.getRow(3);
        setCell(row4, 3, cellCenterStyle, partyUserInfoMaps.get("age"));
        setCell(row4, 5, cellCenterStyle, partyUserInfoMaps.get("nation"));
        setCell(row4, 7, cellCenterStyle, partyUserInfoMaps.get("nativePlace"));
        
        Row row5 = hs.getRow(4);
        setCell(row5, 3, cellCenterStyle, partyUserInfoMaps.get("idCard"));
        
        Row row6 = hs.getRow(5);
        setCell(row6, 3, cellCenterStyle, partyUserInfoMaps.get("mobilePhone"));
        setCell(row6, 5, cellCenterStyle, partyUserInfoMaps.get("qq"));
        setCell(row6, 7, cellCenterStyle, partyUserInfoMaps.get("wechat"));
        
        Row row7 = hs.getRow(6);
        setCell(row7, 3, cellCenterStyle, partyUserInfoMaps.get("email"));
        
        Row row8 = hs.getRow(7);
        setCell(row8, 3, cellCenterStyle, partyUserInfoMaps.get("enrolmentTime"));
        setCell(row8, 5, cellCenterStyle, partyUserInfoMaps.get("graduationTime"));
        setCell(row8, 7, cellCenterStyle, partyUserInfoMaps.get("graduationSchool"));
        
        Row row9 = hs.getRow(8);
        setCell(row9, 3, cellCenterStyle, partyUserInfoMaps.get("major"));
        
        Row row10 = hs.getRow(9);
        setCell(row10, 3, cellCenterStyle, partyUserInfoMaps.get("education"));
        setCell(row10, 5, cellCenterStyle, partyUserInfoMaps.get("academicDegree"));
        
        Row row11 = hs.getRow(10);
        setCell(row11, 3, cellCenterStyle, partyUserInfoMaps.get("homeAddressProvince") + " " +
        		partyUserInfoMaps.get("homeAddressCity") + " " +
				partyUserInfoMaps.get("homeAddressArea") + " " +
				partyUserInfoMaps.get("homeAddressDetail"));
        
        Row row12 = hs.getRow(11);
        setCell(row12, 3, cellCenterStyle, partyUserInfoMaps.get("presentAddressProvince") + " " +
        		partyUserInfoMaps.get("presentAddressCity") + " " +
				partyUserInfoMaps.get("presentAddressArea") + " " +
				partyUserInfoMaps.get("presentAddressDetail"));
        
        Row row13 = hs.getRow(12);
        setCell(row13, 3, cellCenterStyle, partyUserInfoMaps.get("specialityLiterature"));
        
        
        Row row14 = hs.getRow(13);
        setCell(row14, 3, cellCenterStyle, partyUserInfoMaps.get("specialityMajor"));
        
        Row row15 = hs.getRow(14);
        setCell(row15, 3, cellCenterStyle, partyUserInfoMaps.get("marriageStatus"));
        
        Row row16 = hs.getRow(15);
        setCell(row16, 3, cellCenterStyle, partyUserInfoMaps.get("childrenStatus"));
        
        Row row19 = hs.getRow(18);
        setCell(row19, 1, cellCenterStyle, partyUserInfoMaps.get("typeName"));
        setCell(row19, 3, cellCenterStyle, partyUserInfoMaps.get("statusName"));
        setCell(row19, 5, cellCenterStyle, partyUserInfoMaps.get("joinDateFormal"));
        setCell(row19, 7, cellCenterStyle, partyUserInfoMaps.get("joinDateReserve"));
        
        Row row20 = hs.getRow(19);
        setCell(row20, 1, cellCenterStyle, partyUserInfoMaps.get("orgInfoName"));
        setCell(row20, 3, cellCenterStyle, partyUserInfoMaps.get("workNature"));
        setCell(row20, 5, cellCenterStyle, partyUserInfoMaps.get("joinWorkDate"));
        setCell(row20, 7, cellCenterStyle, partyUserInfoMaps.get("appointmentTimeLength"));
        
        Row row21 = hs.getRow(20);
        setCell(row21, 1, cellCenterStyle, partyUserInfoMaps.get("joinPartyBranchType"));
        setCell(row21, 3, cellCenterStyle, partyUserInfoMaps.get("firstLineTypeName"));
        
        Row row22 = hs.getRow(21);
        setCell(row22, 1, cellCenterStyle, partyUserInfoMaps.get("partyStaffName"));
        setCell(row22, 3, cellCenterStyle, partyUserInfoMaps.get("partyRepresentativeName"));
        setCell(row22, 5, cellCenterStyle, partyUserInfoMaps.get("volunteerName"));
        setCell(row22, 7, cellCenterStyle, partyUserInfoMaps.get("difficultUserName"));
        
        Row row23 = hs.getRow(22);
        setCell(row23, 1, cellCenterStyle, partyUserInfoMaps.get("advancedUserName"));
        setCell(row23, 3, cellCenterStyle, partyUserInfoMaps.get("positiveUserName"));
        setCell(row23, 5, cellCenterStyle, partyUserInfoMaps.get("developUserName"));
        setCell(row23, 7, cellCenterStyle, partyUserInfoMaps.get("missingUserName"));
        
        Row row25 = hs.getRow(24);
        row25.getCell(0).setCellValue((String)partyUserInfoMaps.get("introduce"));

		ByteArrayInputStream bain = null;
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	wb.write(baos);
    	byte[] b = baos.toByteArray();
    	bain = new ByteArrayInputStream(b);
    	if(baos != null) {
    		baos.close();
    	}
    	this.downloadFile(response, bain, "党员信息-" + partyUserInfoMaps.get("name") + ".xlsx");
    	return R.ok().setMsg("导出成功");
    }
    
	/**
	 * 设置单元格
	 */
	private void setCell(Row row, int cellCol, CellStyle cellCenterStyle, Object value) {
		Cell cell = row.createCell(cellCol);
    	cell.setCellStyle(cellCenterStyle);
    	if(value instanceof Integer) {
    		cell.setCellValue(String.valueOf(value));
    	} else if (value instanceof String) {
    		cell.setCellValue((String)value);
    	} else if (value instanceof Date) {
    		cell.setCellValue(DateUtil.formatDate(DateUtil.YYYY_MM_DD, (Date)value));
    	} else {
    		cell.setCellValue((String)value);
    	}
    }
	
    /**
	 * 下载错误信息
	 * @param response 条件
	 * @return
	 */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	@Deprecated
    public R downloadValidataMsg(HttpServletResponse response) throws Exception {
		StringBuffer validataErrorMsg = new StringBuffer();
		if (StringUtil.isNotEmpty(validataErrorMsg.toString())) {
			this.downloadFile(response, new ByteArrayInputStream(validataErrorMsg.toString().getBytes()), "导入错误信息.txt"); //下载错误提示信息
			validataErrorMsg = new StringBuffer();
			return R.ok().setMsg("信息校验结果");
		} else {
			return R.ok();
		}
    }
    
    /**
	 * 下载党员导入excel格式示例
	 * @param baseUser 条件
	 * @return
	 */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public void exportPartyUserInfosExcelExample(HttpServletResponse response) throws Exception {
		this.downloadFile(response, 
				this.getClass().getResourceAsStream("/importPartyUserInfosExcelExample.xlsx"), "党员信息导入示例.xlsx");
    }
	
	
	
	/**
	 * 根据不同版本的excel创建对应的对应的对象
	 * @param fileSuffix
	 * @param is
	 * @return
	 */
	private Workbook createWorkboot(String fileSuffix, InputStream is) {
		try {
			if (OFFICE_EXCEL_2010.equals(fileSuffix.toUpperCase())) {
				return new XSSFWorkbook(is);
			} else if(OFFICE_EXCEL_2003.equals(fileSuffix.toUpperCase())) {
				return new HSSFWorkbook(is);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
     * 下载文件
     * @param response
     * @param is
     * @throws Exception
     */
    private void downloadFile(HttpServletResponse response, InputStream is, String fileName) throws Exception {
    	response.setHeader("content-type", "application/octet-stream");  
		response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
		os = response.getOutputStream();
		bis = new BufferedInputStream(is);
		int i = bis.read(buff);
		while (i != -1) {
			os.write(buff, 0, i);
			os.flush();
			i = bis.read(buff);
		}
		if (bis != null) {
			bis.close();
		}
		if (os != null) {
			os.close();
		}
    }
    
    /**
	 * 得到文件后缀
	 * @param fileName	文件名
	 * @return
	 */
	private String getExcelSuffix (String fileName) {
		String[] excelFormats = fileName.split("\\.");
		return excelFormats[excelFormats.length - 1];
	}
}
