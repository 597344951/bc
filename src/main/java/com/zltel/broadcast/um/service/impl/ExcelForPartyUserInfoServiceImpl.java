package com.zltel.broadcast.um.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import com.zltel.broadcast.um.bean.BaseUserInfo;
import com.zltel.broadcast.um.bean.EducationLevel;
import com.zltel.broadcast.um.bean.JoinPartyBranchType;
import com.zltel.broadcast.um.bean.JoinPartyOrgStep;
import com.zltel.broadcast.um.bean.JoinPartyOrgUser;
import com.zltel.broadcast.um.bean.NationType;
import com.zltel.broadcast.um.bean.OrganizationDuty;
import com.zltel.broadcast.um.bean.OrganizationInformation;
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.bean.PartyUserInfo;
import com.zltel.broadcast.um.bean.SysRole;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.bean.SysUserRole;
import com.zltel.broadcast.um.bean.WorkNatureType;
import com.zltel.broadcast.um.dao.BaseUserInfoMapper;
import com.zltel.broadcast.um.dao.EducationLevelMapper;
import com.zltel.broadcast.um.dao.JoinPartyBranchTypeMapper;
import com.zltel.broadcast.um.dao.JoinPartyOrgStepMapper;
import com.zltel.broadcast.um.dao.JoinPartyOrgUserMapper;
import com.zltel.broadcast.um.dao.NationTypeMapper;
import com.zltel.broadcast.um.dao.OrganizationDutyMapper;
import com.zltel.broadcast.um.dao.OrganizationInformationMapper;
import com.zltel.broadcast.um.dao.OrganizationJoinProcessMapper;
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
    @Resource
    private OrganizationJoinProcessMapper organizationJoinProcessMapper;
    @Autowired
    private NationTypeMapper nationTypeMapper;
    @Autowired
    private EducationLevelMapper educationLevelMapper;
    @Autowired
    private WorkNatureTypeMapper workNatureTypeMapper;
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
    private JoinPartyOrgUserMapper joinPartyOrgUserMapper;
	@Resource
    private JoinPartyOrgStepMapper joinPartyOrgStepMapper;
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
     * 
     * @param baseUser 条件
     * @return
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public R importPartyUserInfosExcel(HttpServletResponse response, MultipartFile file) throws Exception {
        Workbook wb = null;
        if (file == null) return R.error().setMsg("导入失败").setData("请选择导入文件");
        wb = this.createWorkboot(this.getExcelSuffix(file.getOriginalFilename()), file.getInputStream());
        Sheet hs = wb.getSheetAt(0); // 得到第一页，正式党员
        Map<Integer, BaseUserInfo> baseUserInfoMaps = new HashMap<>();
        Map<Integer, PartyUserInfo> partyUserInfoMaps = new HashMap<>();
        Map<Integer, OrganizationRelation> orgRelationMaps = new HashMap<>();
        Map<Integer, SysUser> sysUsersMaps = new HashMap<>();
        StringBuffer validataErrorMsg = new StringBuffer(); // 保存错误信息
        boolean thisValidateSuccess = true;
        thisValidateSuccess = validateImportPartyUserInfosExcel(hs, baseUserInfoMaps, partyUserInfoMaps,
                orgRelationMaps, validataErrorMsg, sysUsersMaps, thisValidateSuccess); // 本次验证是否通过
        
        Sheet joinHs = wb.getSheetAt(1); // 得到第二页，入党申请人
        Map<Integer, BaseUserInfo> joinBuiMap = new HashMap<>();
        Map<Integer, PartyUserInfo> joinPuiMap = new HashMap<>();
        Map<Integer, OrganizationRelation> joinOrMap = new HashMap<>();
        Map<Integer, SysUser> suMap = new HashMap<>();
        Map<Integer, Map<String, Object>> joinInfoMap = new HashMap<>();
        thisValidateSuccess = validateImportJoinUserInfosExcel(joinHs, joinBuiMap, joinPuiMap,
        		joinOrMap, validataErrorMsg, suMap, thisValidateSuccess, joinInfoMap); // 本次验证是否通过
        if (!thisValidateSuccess) {
            return R.error().setMsg("导入失败，请查看失败信息 ：导入错误信息.txt").setData(validataErrorMsg);
        }
        
        
        //开始添加
        if (joinBuiMap.keySet().size() != 0) {
        	for (Integer num : joinBuiMap.keySet()) {
        		//添加基础用户数据
        		BaseUserInfo bui = joinBuiMap.get(num);
        		baseUserInfoMapper.insertSelective(bui);
                //为此基础用户添加账号
        		SysUser su = suMap.get(num);
                sysUserMapper.insertSelective(su);
                //赋予默认角色
                SysRole sysRole = new SysRole();
                sysRole.setRoleName("party_role");
                List<SysRole> srs = sysRoleMapper.querySysRoles(sysRole);
                if (srs != null && srs.size() == 1) {
                    SysUserRole sur = new SysUserRole();
                    sur.setUserId(Long.valueOf(su.getUserId()));
                    sur.setRoleId(srs.get(0).getRoleId());
                    sysUserRoleMapper.insertSelective(sur);
                } else {
                    throw new Exception();
                }
                
                Map<String, Object> joinInfo = joinInfoMap.get(num);
                if (joinInfo.get("joinOrgId") != null && joinInfo.get("joinIndexNum") != null && 
                		joinInfo.get("stepStatus") != null) {	//存在入党流程
                	//读取到此步骤包括之前的步骤
            		Map<String, Object> condition = new HashMap<>();
            		condition.put("orgId", String.valueOf(joinInfo.get("joinOrgId")));
            		condition.put("indexNumXD", String.valueOf(joinInfo.get("joinIndexNum")));
            		List<Map<String, Object>> ojps = organizationJoinProcessMapper.queryOrgOjp(condition);
            		//先增加一个申请记录
            		JoinPartyOrgUser jpou = new JoinPartyOrgUser();
            		jpou.setUserId(bui.getBaseUserId());
            		jpou.setJoinOrgId(Integer.parseInt(String.valueOf(joinInfo.get("joinOrgId"))));
            		jpou.setJoinPartyType(1);
            		jpou.setTime(new Date());
            		jpou.setNowStep(Integer.parseInt(String.valueOf(ojps.get(0).get("processId"))));
            		jpou.setIsHistory(0);
            		joinPartyOrgUserMapper.insertSelective(jpou);
            		
            		//遍历生成申请记录添加步骤并设置步骤状态
            		for (int i = 0; i < ojps.size(); i++) {
            			Map<String, Object> ojp = ojps.get(i);
            			//变更申请信息
            			JoinPartyOrgUser upJpou = new JoinPartyOrgUser();
            			upJpou.setId(jpou.getId());
        				upJpou.setNowStep(Integer.parseInt(String.valueOf(ojp.get("processId"))));
        				//新增步骤信息
            			JoinPartyOrgStep newJpos = new JoinPartyOrgStep();
            			newJpos.setJoinId(jpou.getId());
                		newJpos.setProcessId(Integer.parseInt(String.valueOf(ojp.get("processId"))));
                		
                		JoinPartyOrgStep nextJpos = null;
                		if (i == ojps.size() - 1) {
                			//最后一个步骤
                			if ("success".equals(joinInfo.get("stepStatus"))) {
                				//步骤通过
                				upJpou.setJoinStatus("success");	//设置状态
                				
                				newJpos.setStepStatus("success");
                				newJpos.setTime(new Date());
                				
                				//步骤通过后进入下一步骤
                				condition.clear();
                	    		condition.put("orgId", jpou.getJoinOrgId());
                	    		condition.put("indexNum", Integer.parseInt(String.valueOf(ojp.get("indexNum"))) + 1);
                	    		List<Map<String, Object>> orgJoinProcess = organizationJoinProcessMapper.queryOrgOjp(condition);
                	    		if (orgJoinProcess == null || orgJoinProcess.size() == 0) {	//没有找到下一步，表示这步时最后一步
                	    			upJpou.setJoinStatus("success");
                	    		} else {
                	    			upJpou.setNowStep(Integer.parseInt(String.valueOf(orgJoinProcess.get(0).get("processId"))));
                	        		if ((boolean) orgJoinProcess.get(0).get("isFile")) {
                	        			//更新状态为null，为避免null项不被更新，updateByPrimaryKeySelective，此处使用空值
                	        			upJpou.setJoinStatus("");
                	        		} else {
                	        			upJpou.setJoinStatus("wait");
                	        			//如果不需要提交资料，直接进入下一步
                	        			nextJpos = new JoinPartyOrgStep();
                	        			nextJpos.setJoinId(jpou.getId());
                	        			nextJpos.setProcessId(upJpou.getNowStep());
                	        			nextJpos.setStepStatus("wait");
                	        		}
                	    		}
                			} else {
                				//步骤待审核
                				upJpou.setJoinStatus("wait");	//设置状态
                				
                				newJpos.setStepStatus("wait");
                				newJpos.setTime(new Date());
                			}
                		} else {
                			//非表格设定的最后一步
                			upJpou.setJoinStatus("success");	//设置状态
                			newJpos.setStepStatus("success");
                			newJpos.setTime(new Date());
                		}
                		
                		if (i < ojps.size() - 1 || (i == ojps.size() -1 && "success".equals(joinInfo.get("stepStatus")))) {
                			/**
                    		 * 当进行到某些特定步骤时，更新人员状态
                    		 */
                    		BaseUserInfo upBuiInfo = null;
                    		PartyUserInfo upPuiInfo = null;
                    		switch (newJpos.getProcessId()) {
                				case 3:	//积极分子
                					upBuiInfo = new BaseUserInfo();
                					upBuiInfo.setBaseUserId(bui.getBaseUserId());
                					upBuiInfo.setPositiveUser(1);
                    				baseUserInfoMapper.updateByPrimaryKeySelective(upBuiInfo);
                					break;
                				case 9:	//发展对象
                					upBuiInfo = new BaseUserInfo();
                					upBuiInfo.setBaseUserId(bui.getBaseUserId());
                					upBuiInfo.setDevPeople(1);
                    				baseUserInfoMapper.updateByPrimaryKeySelective(upBuiInfo);
                					break;
                				case 14:	//预备党员
                					upBuiInfo = new BaseUserInfo();
                					upBuiInfo.setBaseUserId(bui.getBaseUserId());
                					upBuiInfo.setIsParty(1);
                    				baseUserInfoMapper.updateByPrimaryKeySelective(upBuiInfo);
                    				
                    				upPuiInfo = new PartyUserInfo();
                    				upPuiInfo.setPartyUserId(bui.getBaseUserId());
                    				upPuiInfo.setType(0);	//0：预备党员
                    				upPuiInfo.setStatus(1);	//1：状态-正常
                    				upPuiInfo.setJoinDateReserve((Date)joinInfo.get("prepareTime"));
                    				upPuiInfo.setJoinPartyBranchTypeId(jpou.getJoinPartyType());
                    				partyUserInfoMapper.insertSelective(upPuiInfo);
                    				
                    				
                    				OrganizationRelation _or = new OrganizationRelation();
                    				organizationRelationMapper.deleteOrgRelationByUserId(bui.getBaseUserId());
                    				_or.setOrgRltJoinTime((Date)joinInfo.get("prepareTime"));
                    				_or.setOrgRltDutyId(Integer.parseInt(String.valueOf(joinInfo.get("distributionDutyId"))));
                    				_or.setOrgRltInfoId(Integer.parseInt(String.valueOf(joinInfo.get("distributionOrgId"))));
                    				_or.setOrgRltUserId(bui.getBaseUserId());
                    				_or.setThisOrgFlow(true);
                    				organizationRelationMapper.insertSelective(_or);	//开始添加组织关系
                    				
                    				//变更登录用户信息
                    		    	SysUser _su = new SysUser();
                    		    	_su.setUserId(su.getUserId());
                    		    	_su.setOrgId(Integer.parseInt(String.valueOf(joinInfo.get("distributionOrgId"))));
                    				sysUserMapper.updateByPrimaryKeySelective(_su);
                					break;
                				case 16:	//确认入党
                					upJpou.setIsHistory(1);	//成为历史
                    				
                    				upPuiInfo = new PartyUserInfo();
                    				upPuiInfo.setPartyUserId(jpou.getUserId());
                    				upPuiInfo.setType(1);	//1：正式党员
                    				upPuiInfo.setJoinDateFormal(new Date());
                    				partyUserInfoMapper.updateByPrimaryKeySelective(upPuiInfo);
                					break;
                			}
                		}
            			joinPartyOrgUserMapper.updateByPrimaryKeySelective(upJpou);
                		joinPartyOrgStepMapper.insertSelective(newJpos);
                		if (nextJpos != null) {
                			nextJpos.setTime(new Date());
    	        			joinPartyOrgStepMapper.insertSelective(nextJpos);
                		}
					}
                }
			}
        }

        // 开始添加
        if (baseUserInfoMaps.keySet().size() != 0) {
            for (Integer num : baseUserInfoMaps.keySet()) {
                BaseUserInfo bui = baseUserInfoMaps.get(num);
                baseUserInfoMapper.insertSelective(bui);

                SysUser su = sysUsersMaps.get(num);
                sysUserMapper.insertSelective(su);
                // 赋予默认角色
                SysRole sysRole = new SysRole();
                sysRole.setRoleName("party_role");
                List<SysRole> srs = sysRoleMapper.querySysRoles(sysRole);
                if (srs != null && srs.size() == 1) {
                    SysUserRole sur = new SysUserRole();
                    if (su != null) sur.setUserId(Long.valueOf(su.getUserId()));
                    sur.setRoleId(srs.get(0).getRoleId());
                    sysUserRoleMapper.insertSelective(sur);
                } else {
                    throw new Exception();
                }

                PartyUserInfo pui = partyUserInfoMaps.get(num);
                pui.setPartyUserId(bui.getBaseUserId());
                partyUserInfoMapper.insertSelective(pui);
                OrganizationRelation orgR = orgRelationMaps.get(num);
                if (orgR != null) {
                    orgR.setOrgRltUserId(bui.getBaseUserId());
                    organizationRelationMapper.insertSelective(orgR);
                }
            }
        } 

        return R.ok().setMsg("信息导入成功");
    }
    
    
    /**
     * 入党申请人导入校验
     * 
     * @param hs
     * @param partys
     * @return
     */
    private boolean validateImportJoinUserInfosExcel(Sheet hs, Map<Integer, BaseUserInfo> baseUserInfoMaps,
            Map<Integer, PartyUserInfo> partyUserInfoMaps, Map<Integer, OrganizationRelation> orgRelationMaps,
            StringBuffer validataErrorMsg, Map<Integer, SysUser> sysUsersMaps, boolean thisValidateSuccess, 
            Map<Integer, Map<String, Object>> joinInfoMap) throws Exception {
    	for (int i = 4;; i++) { // 从第4行开始读取
    		Row row = hs.getRow(i);
            if (row == null) {
                break;
            }
            BaseUserInfo baseUserInfo = new BaseUserInfo();
            PartyUserInfo partyUserInfo = new PartyUserInfo();
            OrganizationRelation orgRelation = new OrganizationRelation();
            SysUser su = new SysUser();
            Map<String, Object> joinInfo = new HashMap<>();
            
            
            // 姓名
            if (row.getCell(0) != null && StringUtil.isNotEmpty(row.getCell(0).getStringCellValue())) {
                if (RegexUtil.isChinaName(row.getCell(0).getStringCellValue())) {
                    baseUserInfo.setName(row.getCell(0).getStringCellValue()); // 用户名字
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行用户名不合法。\r\n");
                }
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户名不能为空。\r\n");
            }
            // 性别
            if (row.getCell(1) != null && StringUtil.isNotEmpty(row.getCell(1).getStringCellValue())) {
                if (row.getCell(1).getStringCellValue().equals("男")
                        || row.getCell(1).getStringCellValue().equals("女")) {
                    baseUserInfo.setSex(row.getCell(1).getStringCellValue()); // 用户性别
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行用户不能出现第三性别。\r\n");
                }
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户性别不能为空。\r\n");
            }
            // 民族
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
            // 身份证号码
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
                            year = "19" + idCard.substring(6, 8);
                            month = idCard.substring(8, 10);
                            day = idCard.substring(10, 12);
                        } else if (idCard.length() == 18) {
                            year = idCard.substring(6, 10);
                            month = idCard.substring(10, 12);
                            day = idCard.substring(12, 14);
                        }
                        String birthDate = year + "-" + month + "-" + day;
                        if (RegexUtil.isDate(birthDate)) {
                            Date birthDay = DateUtil.toDate(DateUtil.YYYY_MM_DD, birthDate); // 生日
                            int age = PartyUserInfoServiceImpl.getPartyUserAge(birthDay);
                            if (age >= 18 && age < 120) { // 理论正确年龄
                                baseUserInfo.setIdCard(idCard);
                                baseUserInfo.setBirthDate(birthDay);
                            } else {
                                thisValidateSuccess = false;
                                validataErrorMsg.append("第" + (i + 1) + "行身份证号码格式不正确。\r\n");
                            }
                        } else { // 不是一个正确的日期
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
            // 手机号码
            try {
				if (row.getCell(4) != null && StringUtil.isNotEmpty(row.getCell(4).getStringCellValue())) {
				    if (RegexUtil.isMobilePhone(row.getCell(4).getStringCellValue())) {
				        baseUserInfo.setMobilePhone(row.getCell(4).getStringCellValue());
				    } else {
				        thisValidateSuccess = false;
				        validataErrorMsg.append("第" + (i + 1) + "行手机号码格式不正确。\r\n");
				    }
				} 
			} catch (IllegalStateException e1) {	//转换异常
				thisValidateSuccess = false;
		        validataErrorMsg.append("第" + (i + 1) + "行手机号码设置出错，请避免使用复制过来的手机号码，可能会导致单元格格式同时复制过来，尽量使用手写，纯数字设置文本格式成功时左上角会有绿色的三角。\r\n");
			}
            // 学历
            if (row.getCell(5) != null && StringUtil.isNotEmpty(row.getCell(5).getStringCellValue())) {
                EducationLevel el = new EducationLevel();
                el.setName(row.getCell(5).getStringCellValue());
                List<EducationLevel> els = educationLevelMapper.queryEducationLevels(el);
                if (els != null && els.size() == 1) {
                    baseUserInfo.setEducation(els.get(0).getEduLevelEid());
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行学历填写错误。\r\n");
                }
            } 
            // 家庭住址
            if (row.getCell(6) != null && StringUtil.isNotEmpty(row.getCell(6).getStringCellValue())) {
                String[] homeAddress = row.getCell(6).getStringCellValue().split("-");
                if (homeAddress.length == 4) {
                    baseUserInfo.setHomeAddressProvince(homeAddress[0]);
                    baseUserInfo.setHomeAddressCity(homeAddress[1]);
                    baseUserInfo.setHomeAddressArea(homeAddress[2]);
                    baseUserInfo.setHomeAddressDetail(homeAddress[3]);
                    baseUserInfo.setNativePlace(homeAddress[0] + "-" + homeAddress[1]);
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行家庭地址填写错误。\r\n");
                }
            } 
            // 现居住地址
            if (row.getCell(7) != null && StringUtil.isNotEmpty(row.getCell(7).getStringCellValue())) {
                String[] presentAddress = row.getCell(7).getStringCellValue().split("-");
                if (presentAddress.length == 4) {
                    baseUserInfo.setPresentAddressProvince(presentAddress[0]);
                    baseUserInfo.setPresentAddressCity(presentAddress[1]);
                    baseUserInfo.setPresentAddressArea(presentAddress[2]);
                    baseUserInfo.setPresentAddressDetail(presentAddress[3]);
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行现居住地址填写错误。\r\n");
                }
            } 
            // 工作单位
            if (row.getCell(8) != null) {
                baseUserInfo.setWorkUnit(row.getCell(8).getStringCellValue());
            }
            // 工作性质
            if (row.getCell(9) != null && StringUtil.isNotEmpty(row.getCell(9).getStringCellValue())) {
                WorkNatureType wnt = new WorkNatureType();
                wnt.setName(row.getCell(9).getStringCellValue());
                List<WorkNatureType> wnts = workNatureTypeMapper.queryWorkNatureTypes(wnt);
                if (wnts != null && wnts.size() == 1) {
                    baseUserInfo.setWorkNature(wnts.get(0).getWorkNatureId());
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行工作性质选择有误。\r\n");
                }
            } 
            // 参加工作时间
            try {
				if (row.getCell(10) != null) {
				    baseUserInfo.setJoinWorkDate(row.getCell(10).getDateCellValue());
				} 
			} catch (IllegalStateException e1) {	//日期转换错误
				thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行日期转换错误，请避免使用复制过来的日期，可能会导致单元格设置的异常，尽量使用手写。\r\n");
			} 
			
			
            //申请加入党委编号
            if (row.getCell(11) != null && StringUtil.isNotEmpty(row.getCell(11).getStringCellValue())) {
            	if (!"我是普通用户".equals(row.getCell(11).getStringCellValue())) {
            		joinInfo.put("joinOrgId", row.getCell(11).getStringCellValue());
            	}
            } else {
            	thisValidateSuccess = false;
                validataErrorMsg.append("第二页" + (i + 1) + "必须设置要加入的党委\r\n");
            }
            //加入流程
            if (row.getCell(12) != null && StringUtil.isNotEmpty(row.getCell(12).getStringCellValue())) {
            	if (!"我是普通用户".equals(row.getCell(12).getStringCellValue()) && joinInfo.get("joinOrgId") != null) {
            		Map<String, Object> condition = new HashMap<>();
            		condition.put("orgId", joinInfo.get("joinOrgId"));
            		condition.put("joinProcessName", row.getCell(12).getStringCellValue());
            		List<Map<String, Object>> ojps = organizationJoinProcessMapper.queryOrgOjp(condition);
            		if (ojps != null && ojps.size() == 1) {
            			joinInfo.put("joinProcess", ojps.get(0).get("processId"));
            			joinInfo.put("joinIndexNum", ojps.get(0).get("indexNum"));
            		} else {
            			thisValidateSuccess = false;
                        validataErrorMsg.append("第二页" + (i + 1) + "要加入的党委组织未设置此流程\r\n");
            		}
            	}
            } else {
            	thisValidateSuccess = false;
                validataErrorMsg.append("第二页" + (i + 1) + "必须设置入党流程\r\n");
            }
            //此步骤是否通过
            if (row.getCell(13) != null && StringUtil.isNotEmpty(row.getCell(13).getStringCellValue())) {
            	if (joinInfo.get("joinIndexNum") != null && joinInfo.get("joinOrgId") != null) {
            		if ("是".equals(row.getCell(13).getStringCellValue())) {
            			joinInfo.put("stepStatus", "success");
            		}  else if ("待审核".equals(row.getCell(13).getStringCellValue())) {
            			joinInfo.put("stepStatus", "wait");
            		} else {
            			thisValidateSuccess = false;
                        validataErrorMsg.append("第二页第" + (i + 1) + "行步骤状态设置错误\r\n");
            		}
            		
            		if (row.getCell(14) != null) joinInfo.put("positiveTime", row.getCell(14).getDateCellValue());
            		if (row.getCell(15) != null) joinInfo.put("developTime", row.getCell(15).getDateCellValue());
            		if (row.getCell(16) != null) joinInfo.put("prepareTime", row.getCell(16).getDateCellValue());
            		if (row.getCell(17) != null) joinInfo.put("distributionOrgId", row.getCell(17).getStringCellValue());
            		if (row.getCell(18) != null) joinInfo.put("distributionDutyId", row.getCell(18).getStringCellValue());
            		
            		//读取此步骤之前的步骤
            		Map<String, Object> condition = new HashMap<>();
            		condition.put("orgId", String.valueOf(joinInfo.get("joinOrgId")));
            		condition.put("indexNumXD", String.valueOf(joinInfo.get("joinIndexNum")));
            		List<Map<String, Object>> ojps = organizationJoinProcessMapper.queryOrgOjp(condition);
            		for (int j = 0; j < ojps.size(); j++) {	
            			if (j < ojps.size() - 1 || (j == ojps.size() -1 && "success".equals(joinInfo.get("stepStatus")))) {	//非最后一步，满足条件的步骤应全部是通过的状态
							Map<String, Object> ojp = ojps.get(j);
							switch (String.valueOf(ojp.get("processId"))) {
								case "3":
									if (joinInfo.get("positiveTime") == null) {
										thisValidateSuccess = false;
				                        validataErrorMsg.append("第二页第" + (i + 1) + "行没有填写成为积极分子的时间，根据填写的入党步骤，"
				                        		+ "此处必填\r\n");
									}
									break;
								case "9":
									if (joinInfo.get("developTime") == null) {
										thisValidateSuccess = false;
				                        validataErrorMsg.append("第二页第" + (i + 1) + "行没有填写成为发展对象的时间，根据填写的入党步骤，"
				                        		+ "此处必填\r\n");
									}
									break;
								case "14":
									if (joinInfo.get("prepareTime") == null) {
										thisValidateSuccess = false;
				                        validataErrorMsg.append("第二页第" + (i + 1) + "行没有填写成为预备党员的时间，根据填写的入党步骤，"
				                        		+ "此处必填\r\n");
									}
									if (joinInfo.get("distributionOrgId") == null) {
										thisValidateSuccess = false;
				                        validataErrorMsg.append("第二页第" + (i + 1) + "行没有填写为预备党员分配的组织，根据填写的入党步骤，"
				                        		+ "此处必填\r\n");
									}
									if (joinInfo.get("distributionDutyId") == null) {
										thisValidateSuccess = false;
				                        validataErrorMsg.append("第二页第" + (i + 1) + "行没有填写为预备党员分配的角色，根据填写的入党步骤，"
				                        		+ "此处必填\r\n");
									}
									break;
							}
						}
            		}
            	}
            } else {
            	thisValidateSuccess = false;
                validataErrorMsg.append("第二页" + (i + 1) + "必须设置此步骤状态，是否通过此步骤\r\n");
            }
            joinInfoMap.put(i, joinInfo);
            
            baseUserInfo.setIsParty(1);
            if (baseUserInfo.getIdCard() != null) {
            	su.setUsername(baseUserInfo.getIdCard());
            	su.setPassword(baseUserInfo.getIdCard().substring(baseUserInfo.getIdCard().length() - 6));
            	String salt = UUID.randomUUID().toString();
            	su.setSalt(salt); // 保存盐
            	su.setPassword(PasswordHelper.encryptPassword(su.getPassword(), salt)); // 加密
            }
            su.setEmail(baseUserInfo.getEmail());
            su.setMobile(baseUserInfo.getMobilePhone());
            su.setStatus(true);
            su.setUserType(1);
            su.setCreateTime(new Date());
            su.setOrgId(orgRelation.getOrgRltInfoId());
            sysUsersMaps.put(i, su);

            baseUserInfoMaps.put(i, baseUserInfo);
            partyUserInfoMaps.put(i, partyUserInfo);
            if (orgRelation.getOrgRltDutyId() != null && orgRelation.getOrgRltInfoId() != null) {
                orgRelationMaps.put(i, orgRelation);
            }
    	}
    	
    	return thisValidateSuccess;
    }

    /**
     * 党员导入校验
     * 
     * @param hs
     * @param partys
     * @return
     */
    private boolean validateImportPartyUserInfosExcel(Sheet hs, Map<Integer, BaseUserInfo> baseUserInfoMaps,
            Map<Integer, PartyUserInfo> partyUserInfoMaps, Map<Integer, OrganizationRelation> orgRelationMaps,
            StringBuffer validataErrorMsg, Map<Integer, SysUser> sysUsersMaps, boolean thisValidateSuccess) throws Exception {
        for (int i = 3;; i++) { // 从第4行开始读取
            Row row = hs.getRow(i);
            if (row == null) {
                break;
            }
            BaseUserInfo baseUserInfo = new BaseUserInfo();
            PartyUserInfo partyUserInfo = new PartyUserInfo();
            OrganizationRelation orgRelation = new OrganizationRelation();

            
            
            // 姓名
            if (row.getCell(0) != null && StringUtil.isNotEmpty(row.getCell(0).getStringCellValue())) {
                if (RegexUtil.isChinaName(row.getCell(0).getStringCellValue())) {
                    baseUserInfo.setName(row.getCell(0).getStringCellValue()); // 用户名字
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行用户名不合法。\r\n");
                }
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户名不能为空。\r\n");
            }
            // 性别
            if (row.getCell(1) != null && StringUtil.isNotEmpty(row.getCell(1).getStringCellValue())) {
                if (row.getCell(1).getStringCellValue().equals("男")
                        || row.getCell(1).getStringCellValue().equals("女")) {
                    baseUserInfo.setSex(row.getCell(1).getStringCellValue()); // 用户性别
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行用户不能出现第三性别。\r\n");
                }
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户性别不能为空。\r\n");
            }
            // 民族
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
            // 身份证号码
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
                            year = "19" + idCard.substring(6, 8);
                            month = idCard.substring(8, 10);
                            day = idCard.substring(10, 12);
                        } else if (idCard.length() == 18) {
                            year = idCard.substring(6, 10);
                            month = idCard.substring(10, 12);
                            day = idCard.substring(12, 14);
                        }
                        String birthDate = year + "-" + month + "-" + day;
                        if (RegexUtil.isDate(birthDate)) {
                            Date birthDay = DateUtil.toDate(DateUtil.YYYY_MM_DD, birthDate); // 生日
                            int age = PartyUserInfoServiceImpl.getPartyUserAge(birthDay);
                            if (age >= 18 && age < 120) { // 理论正确年龄
                                baseUserInfo.setIdCard(idCard);
                                baseUserInfo.setBirthDate(birthDay);
                            } else {
                                thisValidateSuccess = false;
                                validataErrorMsg.append("第" + (i + 1) + "行身份证号码格式不正确。\r\n");
                            }
                        } else { // 不是一个正确的日期
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
            // 手机号码
            try {
				if (row.getCell(4) != null && StringUtil.isNotEmpty(row.getCell(4).getStringCellValue())) {
				    if (RegexUtil.isMobilePhone(row.getCell(4).getStringCellValue())) {
				        baseUserInfo.setMobilePhone(row.getCell(4).getStringCellValue());
				    } else {
				        thisValidateSuccess = false;
				        validataErrorMsg.append("第" + (i + 1) + "行手机号码格式不正确。\r\n");
				    }
				} 
			} catch (IllegalStateException e1) {	//转换异常
				thisValidateSuccess = false;
		        validataErrorMsg.append("第" + (i + 1) + "行手机号码设置出错，请避免使用复制过来的手机号码，可能会导致单元格格式同时复制过来，尽量使用手写，纯数字设置文本格式成功时左上角会有绿色的三角。\r\n");
			}
            // 学历
            if (row.getCell(5) != null && StringUtil.isNotEmpty(row.getCell(5).getStringCellValue())) {
                EducationLevel el = new EducationLevel();
                el.setName(row.getCell(5).getStringCellValue());
                List<EducationLevel> els = educationLevelMapper.queryEducationLevels(el);
                if (els != null && els.size() == 1) {
                    baseUserInfo.setEducation(els.get(0).getEduLevelEid());
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行学历填写错误。\r\n");
                }
            } 
            // 家庭住址
            if (row.getCell(6) != null && StringUtil.isNotEmpty(row.getCell(6).getStringCellValue())) {
                String[] homeAddress = row.getCell(6).getStringCellValue().split("-");
                if (homeAddress.length == 4) {
                    baseUserInfo.setHomeAddressProvince(homeAddress[0]);
                    baseUserInfo.setHomeAddressCity(homeAddress[1]);
                    baseUserInfo.setHomeAddressArea(homeAddress[2]);
                    baseUserInfo.setHomeAddressDetail(homeAddress[3]);
                    baseUserInfo.setNativePlace(homeAddress[0] + "-" + homeAddress[1]);
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行家庭地址填写错误。\r\n");
                }
            } 
            // 现居住地址
            if (row.getCell(7) != null && StringUtil.isNotEmpty(row.getCell(7).getStringCellValue())) {
                String[] presentAddress = row.getCell(7).getStringCellValue().split("-");
                if (presentAddress.length == 4) {
                    baseUserInfo.setPresentAddressProvince(presentAddress[0]);
                    baseUserInfo.setPresentAddressCity(presentAddress[1]);
                    baseUserInfo.setPresentAddressArea(presentAddress[2]);
                    baseUserInfo.setPresentAddressDetail(presentAddress[3]);
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行现居住地址填写错误。\r\n");
                }
            } 
            // 工作单位
            if (row.getCell(8) != null) {
                baseUserInfo.setWorkUnit(row.getCell(8).getStringCellValue());
            }
            // 工作性质
            if (row.getCell(9) != null && StringUtil.isNotEmpty(row.getCell(9).getStringCellValue())) {
                WorkNatureType wnt = new WorkNatureType();
                wnt.setName(row.getCell(9).getStringCellValue());
                List<WorkNatureType> wnts = workNatureTypeMapper.queryWorkNatureTypes(wnt);
                if (wnts != null && wnts.size() == 1) {
                    baseUserInfo.setWorkNature(wnts.get(0).getWorkNatureId());
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行工作性质选择有误。\r\n");
                }
            } 
            // 参加工作时间
            try {
				if (row.getCell(10) != null) {
				    baseUserInfo.setJoinWorkDate(row.getCell(10).getDateCellValue());
				} 
			} catch (IllegalStateException e1) {	//日期转换错误
				thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行日期转换错误，请避免使用复制过来的日期，可能会导致单元格设置的异常，尽量使用手写。\r\n");
			}
            baseUserInfo.setDevPeople(1);	//党员一定是发展党员
            baseUserInfo.setPositiveUser(1);	//一定是入党积极分子
            

            // 党员类型
            if (row.getCell(11) != null && StringUtil.isNotEmpty(row.getCell(11).getStringCellValue())) {
                if ("正式党员".equals(row.getCell(11).getStringCellValue())) {
                    partyUserInfo.setType(1);
                } else if ("预备党员".equals(row.getCell(11).getStringCellValue())) {
                    partyUserInfo.setType(0);
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行党员类型填写错误。\r\n");
                }
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行党员类型不能为空。\r\n");
            }
            // 党员状态
            if (row.getCell(12) != null && StringUtil.isNotEmpty(row.getCell(12).getStringCellValue())) {
                if ("正常".equals(row.getCell(12).getStringCellValue())) {
                    partyUserInfo.setStatus(1);
                } else if ("停止党籍".equals(row.getCell(12).getStringCellValue())) {
                    partyUserInfo.setStatus(0);
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行党员状态填写错误。\r\n");
                }
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行党员状态不能为空。\r\n");
            }
            // 入党时间
            try {
				if (row.getCell(13) != null) {
				    partyUserInfo.setJoinDateFormal(row.getCell(13).getDateCellValue());
				}
			} catch (IllegalStateException e1) {
				thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行入党日期转换错误，请避免使用复制过来的日期，可能会导致单元格设置的异常，尽量使用手写。\r\n");
			}
            // 预备党员入党时间
            try {
				if (row.getCell(14) != null) {
				    partyUserInfo.setJoinDateReserve(row.getCell(14).getDateCellValue());
				}
			} catch (IllegalStateException e1) {
				thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行成为预备党员日期转换错误，请避免使用复制过来的日期，可能会导致单元格设置的异常，尽量使用手写。\r\n");
			} 
            // 如何加入党支部
            if (row.getCell(15) != null && StringUtil.isNotEmpty(row.getCell(15).getStringCellValue())) {
                JoinPartyBranchType jpbt = new JoinPartyBranchType();
                jpbt.setJoinType(row.getCell(15).getStringCellValue());
                List<JoinPartyBranchType> jpbts = joinPartyBranchTypeMapper.queryJoinPartyBranchTypes(jpbt);
                if (jpbts != null && jpbts.size() == 1) {
                    partyUserInfo.setJoinPartyBranchTypeId(jpbts.get(0).getJpbtId());
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行加入党支部方式选择有误。\r\n");
                }
            } 
            // 收入
            if (row.getCell(16) != null) {
            	Double income = row.getCell(16).getNumericCellValue();
            	if (income < 0.00) {
            		thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行收入设置有误。\r\n");
            	} else {            		
            		baseUserInfo.setIncome(new BigDecimal(income));
            	}
            }
            // 党费占收入百分比
            if (row.getCell(17) != null) {
            	Double party_proportion = row.getCell(17).getNumericCellValue();
            	if (party_proportion < 0.00 || party_proportion > 100.00) {
            		thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行党费占收入比设置有误。\r\n");
            	} else {            		
            		baseUserInfo.setPartyProportion(new BigDecimal(party_proportion));
            	}
            }

            // 组织编号
            if (row.getCell(18) != null && row.getCell(19) != null) {
                Integer orgRltInfoId;
                Integer orgRltDutyId;
                try {
                    orgRltInfoId = Integer.parseInt(row.getCell(18).getStringCellValue());
                    orgRltDutyId = Integer.parseInt(row.getCell(19).getStringCellValue());
                } catch (Exception e) {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行请填写正确的组织编号和职责编号。\r\n");
                    logout.error(e.getMessage(), e);
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
            } 

            // 加入组织时间
            if (row.getCell(20) != null) {
                orgRelation.setOrgRltJoinTime(row.getCell(20).getDateCellValue());
            } 

            baseUserInfo.setIsParty(1);

            SysUser su = new SysUser();
            if (baseUserInfo.getIdCard() != null) {
            	su.setUsername(baseUserInfo.getIdCard());
            	su.setPassword(baseUserInfo.getIdCard().substring(baseUserInfo.getIdCard().length() - 6));
            	String salt = UUID.randomUUID().toString();
            	su.setSalt(salt); // 保存盐
            	su.setPassword(PasswordHelper.encryptPassword(su.getPassword(), salt)); // 加密
            }
            su.setEmail(baseUserInfo.getEmail());
            su.setMobile(baseUserInfo.getMobilePhone());
            su.setStatus(true);
            su.setUserType(1);
            su.setCreateTime(new Date());
            su.setOrgId(orgRelation.getOrgRltInfoId());
            sysUsersMaps.put(i, su);

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
     * 
     * @param response 条件
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public R exportPartyUserInfosExcel(HttpServletResponse response, PartyUserInfo partyUserInfo) throws Exception {
        if (partyUserInfo == null) return R.error().setMsg("请选择党员");
        Map<String, Object> partyUserMap = new HashMap<>();
        partyUserMap.put("id", partyUserInfo.getPartyUserId());
        Map<String, Object> partyUserInfoMaps = partyUserInfoMapper.queryPartyUserInfos(partyUserMap).get(0);

        String fn = "/exportPartyUserInfoExcel.xlsx";
        Workbook wb = this.createWorkboot(ExcelForPartyUserInfoServiceImpl.OFFICE_EXCEL_2010,
                this.getClass().getResourceAsStream(fn));
        if (wb == null) throw new RRException("找不到文件:" + fn);
        Sheet hs = wb.getSheetAt(0); // 得到第一页
        Row row1 = hs.getRow(0);
        row1.getCell(0).setCellValue("党员信息-" + partyUserInfoMaps.get("name")); // 标题，模板有内容，用getCell

        CellStyle cellCenterStyle = wb.createCellStyle(); // 设置一个居中样式
        cellCenterStyle.setAlignment(HorizontalAlignment.CENTER);

        InputStream bis = null;
        try {
            // 设置党员头像
            CreationHelper helper = wb.getCreationHelper();
            Drawing drawing = hs.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            if (StringUtil.isEmpty((String) partyUserInfoMaps.get("idPhoto"))) {
                bis = this.getClass().getResourceAsStream("/noFoundIdPhoto.jpg");
            } else {
                bis = new FileInputStream(new File((String) partyUserInfoMaps.get("idPhoto")));
            }
            byte[] bytes = IOUtils.toByteArray(bis);
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
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
        setCell(row11, 3, cellCenterStyle,
                partyUserInfoMaps.get("homeAddressProvince") + " " + partyUserInfoMaps.get("homeAddressCity") + " "
                        + partyUserInfoMaps.get("homeAddressArea") + " " + partyUserInfoMaps.get("homeAddressDetail"));

        Row row12 = hs.getRow(11);
        setCell(row12, 3, cellCenterStyle,
                partyUserInfoMaps.get("presentAddressProvince") + " " + partyUserInfoMaps.get("presentAddressCity")
                        + " " + partyUserInfoMaps.get("presentAddressArea") + " "
                        + partyUserInfoMaps.get("presentAddressDetail"));

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
        row25.getCell(0).setCellValue((String) partyUserInfoMaps.get("introduce"));

        ByteArrayInputStream bain = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);
        byte[] b = baos.toByteArray();
        bain = new ByteArrayInputStream(b);
        if (baos != null) {
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
        if (value instanceof Integer) {
            cell.setCellValue(String.valueOf(value));
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Date) {
            cell.setCellValue(DateUtil.formatDate(DateUtil.YYYY_MM_DD, (Date) value));
        } else {
            cell.setCellValue((String) value);
        }
    }

    /**
     * 下载错误信息
     * 
     * @param response 条件
     * @return
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    @Deprecated
    public R downloadValidataMsg(HttpServletResponse response) throws Exception {
        StringBuffer validataErrorMsg = new StringBuffer();
        if (StringUtil.isNotEmpty(validataErrorMsg.toString())) {
            this.downloadFile(response, new ByteArrayInputStream(validataErrorMsg.toString().getBytes()), "导入错误信息.txt"); // 下载错误提示信息
            validataErrorMsg = new StringBuffer();
            return R.ok().setMsg("信息校验结果");
        } else {
            return R.ok();
        }
    }

    /**
     * 下载党员导入excel格式示例
     * 
     * @param baseUser 条件
     * @return
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public void exportPartyUserInfosExcelExample(HttpServletResponse response) throws Exception {
        this.downloadFile(response, this.getClass().getResourceAsStream("/importPartyUserInfosExcelExample.xlsx"),
                "党员信息导入示例.xlsx");
    }



    /**
     * 根据不同版本的excel创建对应的对应的对象
     * 
     * @param fileSuffix
     * @param is
     * @return
     */
    private Workbook createWorkboot(String fileSuffix, InputStream is) {
        try {
            if (OFFICE_EXCEL_2010.equals(fileSuffix.toUpperCase())) {
                return new XSSFWorkbook(is);
            } else if (OFFICE_EXCEL_2003.equals(fileSuffix.toUpperCase())) {
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
     * 
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
     * 
     * @param fileName 文件名
     * @return
     */
    private String getExcelSuffix(String fileName) {
        String[] excelFormats = fileName.split("\\.");
        return excelFormats[excelFormats.length - 1];
    }
}
