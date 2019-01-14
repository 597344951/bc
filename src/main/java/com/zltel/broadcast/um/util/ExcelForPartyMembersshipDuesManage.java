package com.zltel.broadcast.um.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.IntegralChangeType;
import com.zltel.broadcast.um.bean.OrganizationInformation;
import com.zltel.broadcast.um.bean.PartyIntegralRecord;
import com.zltel.broadcast.um.bean.PartyMembershipDuesManage;
import com.zltel.broadcast.um.bean.PartyMembershipDuesPaidWay;
import com.zltel.broadcast.um.bean.PartyMembershipDuesStatus;
import com.zltel.broadcast.um.bean.PartyUserInfo;
import com.zltel.broadcast.um.dao.IntegralChangeTypeMapper;
import com.zltel.broadcast.um.dao.IntegralConstituteMapper;
import com.zltel.broadcast.um.dao.OrganizationInformationMapper;
import com.zltel.broadcast.um.dao.PartyIntegralRecordMapper;
import com.zltel.broadcast.um.dao.PartyMembershipDuesManageMapper;
import com.zltel.broadcast.um.dao.PartyMembershipDuesPaidWayMapper;
import com.zltel.broadcast.um.dao.PartyMembershipDuesStatusMapper;
import com.zltel.broadcast.um.dao.PartyUserInfoMapper;

@Component
public class ExcelForPartyMembersshipDuesManage {
    private final static String OFFICE_EXCEL_2003 = "XLS";
    private final static String OFFICE_EXCEL_2010 = "XLSX";

    @Resource
    private PartyUserInfoMapper partyUserInfoMapper;
    @Resource
    private OrganizationInformationMapper organizationInformationMapper;
    @Resource
    private PartyMembershipDuesPaidWayMapper partyMembershipDuesPaidWayMapper;
    @Resource
    private PartyMembershipDuesManageMapper partyMembershipDuesManageMapper;
    @Resource
    private PartyMembershipDuesStatusMapper partyMembershipDuesStatusMapper;
    @Resource
    private IntegralConstituteMapper integralConstituteMapper;
    @Resource
    private IntegralChangeTypeMapper integralChangeTypeMapper;
    @Resource
    private PartyIntegralRecordMapper partyIntegralRecordMapper;

    /**
     * 批量导入党费缴纳记录
     * 
     * @param file 文件
     * @return
     */
    public R importPMDMsExcel(HttpServletResponse response, MultipartFile file) {
        Workbook wb = null;
        try {
            wb = createWorkboot(getExcelSuffix(file.getOriginalFilename()), file.getInputStream());
        } catch (Exception e) {
            RRException.makeThrow(e);
        }
        if (wb == null) throw new RRException("找不到文件:" + file.getName());
        Sheet hs = wb.getSheetAt(0); // 得到第一页
        List<PartyMembershipDuesManage> pmdms = new ArrayList<>();
        StringBuilder validataErrorMsg = new StringBuilder(); // 保存错误信息
        boolean thisValidateSuccess = validateImportPMDMsExcel(hs, pmdms, validataErrorMsg); // 本次验证是否通过
        if (!thisValidateSuccess) {
            return R.error().setMsg("导入失败，请查看失败信息 ：导入错误信息.txt").setData(validataErrorMsg);
        }

        if (pmdms.size() > 0) {
            for (PartyMembershipDuesManage pmdm : pmdms) {
                int pmdmCount = partyMembershipDuesManageMapper.insertSelective(pmdm);
                //根据党费缴纳记录自动变更分值时需验证更此党费缴纳记录分值后不能超过该设置的上限也不能低于0
                Map<String, Object> conditions = new HashMap<>();
                conditions.put("orgId", pmdm.getOrgId());
                conditions.put("type", "缴纳党费");
                conditions.put("isInnerIntegral", 1);
                List<Map<String, Object>> ics = integralConstituteMapper.queryOrgIntegralConstitute(conditions);
                if (ics != null && ics.size() == 1) {	//如果该组织有积分
                	Map<String, Object> ic = ics.get(0);	//积分信息
                	
                	IntegralChangeType ict = new IntegralChangeType();
					ict.setIcId(Integer.parseInt(String.valueOf(ic.get("icId"))));
					ict.setOperation(0);	//是否有扣分处理方式
					List<IntegralChangeType> reduce_icts = integralChangeTypeMapper.queryICT(ict);
					ict.setOperation(1);	//是否有加分处理方式
					List<IntegralChangeType> add_icts = integralChangeTypeMapper.queryICT(ict);
					
					if (reduce_icts != null && reduce_icts.size() == 1 && add_icts != null && add_icts.size() == 1) {
						//单项的积分不可能为0，当党员积分为0时，这时在扣分会记录下来，虽然显示是0分，但是会和后来的加分相抵消
						PartyIntegralRecord pir = new PartyIntegralRecord();
						pir.setOrgId(pmdm.getOrgId());
						pir.setPartyId(pmdm.getUserId());
						pir.setChangeTypeId(Integer.parseInt(String.valueOf(ic.get("icId"))));
						PartyMembershipDuesStatus pmds = partyMembershipDuesStatusMapper.selectByPrimaryKey(pmdm.getPayStatus());
						if ("按时缴清".equals(pmds.getName())) {	//加分
							pir.setChangeIntegralType(1);
							pir.setChangeDescribes(pmds.getName() + "党费，加" + add_icts.get(0).getChangeProposalIntegral() + "分");
							pir.setChangeScore(add_icts.get(0).getChangeProposalIntegral());
						} else {	//扣分
							pir.setChangeIntegralType(0);
							pir.setChangeDescribes(pmds.getName() + "党费，扣" + reduce_icts.get(0).getChangeProposalIntegral() + "分");
							pir.setChangeScore(reduce_icts.get(0).getChangeProposalIntegral());
						}
						pir.setIsMerge(1);
						
						pir.setChangeTime(new Date());
				    	int count = partyIntegralRecordMapper.insertSelective(pir);
				    	if(count != 1) {
				    		return R.error().setMsg("导入失败，请查看失败信息 ：导入错误信息.txt").setData("请完善组织积分信息以使用导入党员积分并自动更新积分");
				    	} 
					} else {
						return R.error().setMsg("导入失败，请查看失败信息 ：导入错误信息.txt").setData("该组织党费缴纳积分没有相应的加扣分设置");
					}
                } else {
                	return R.error().setMsg("导入失败，请查看失败信息 ：导入错误信息.txt").setData("该组织积分结构中没有关于党费缴纳的积分信息");
                }
                if (pmdmCount != 1) {
                    throw new RuntimeException();
                }
            }
        } else {
            return R.error().setMsg("没有要导入的用户信息");
        }

        return R.ok().setMsg("信息导入成功");
    }

    /**
     * 党费缴纳记录导入校验
     * 
     * @param hs
     * @param pmdmMaps
     * @return
     */
    private boolean validateImportPMDMsExcel(Sheet hs, List<PartyMembershipDuesManage> pmdms,
                                             StringBuilder validataErrorMsg) {
        boolean thisValidateSuccess = true;
        for (int i = 2;; i++) { // 从第3行开始读取
            Row row = hs.getRow(i);
            if (row == null) {
                break;
            }
            PartyMembershipDuesManage pmdm = new PartyMembershipDuesManage();

            // 身份证号码
            if (row.getCell(0) != null && StringUtil.isNotEmpty(row.getCell(0).getStringCellValue())) {
                String idCard = row.getCell(0).getStringCellValue();
                if (RegexUtil.isIDCard(idCard)) { // 是不是一个正确的身份证号
                    Map<String, Object> conditionMap = new HashMap<>();
                    conditionMap.put("idCard", idCard);
                    List<PartyUserInfo> buis = partyUserInfoMapper.queryPartyUserInfosForValidata(conditionMap);
                    if (buis != null && buis.size() == 1) {
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
                            pmdm.setUserId(buis.get(0).getPartyUserId());
                        } else { // 不是一个正确的日期
                            thisValidateSuccess = false;
                            validataErrorMsg.append("第" + (i + 1) + "行身份证号码格式不正确\r\n");
                        }
                    } else {
                        thisValidateSuccess = false;
                        validataErrorMsg.append("第" + (i + 1) + "行没有查询到此党员，请确认后在添加或添加此党员信息\r\n");
                    }
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行身份证号码格式不正确\r\n");
                }
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行身份证号码是必须的\r\n");
            }

            // 所在组织
            if (row.getCell(1) != null) {
                Integer orgInfoId;
                try {
                    orgInfoId = Integer.parseInt(row.getCell(1).getStringCellValue());
                } catch (Exception e) {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行请填写正确的组织编号\r\n");
                    break;
                }
                OrganizationInformation orgInfo = new OrganizationInformation();
                orgInfo.setOrgInfoId(orgInfoId);
                List<OrganizationInformation> orgInfos = organizationInformationMapper.queryOrgInfos(orgInfo);
                if (orgInfos != null && orgInfos.size() == 1) {
                    pmdm.setOrgId(orgInfoId);
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行填写组织不存在\r\n");
                }
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行组织编号是必须的\r\n");
            }

            // 应缴纳金额
            if (row.getCell(2) != null && StringUtil.isNotEmpty(row.getCell(2).getStringCellValue())) {
            	if (new BigDecimal(row.getCell(2).getStringCellValue()).doubleValue() < 0) {
            		thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行应缴纳金额不能为负数\r\n");
            	}
                BigDecimal shouldPayMoney = new BigDecimal(row.getCell(2).getStringCellValue());
                pmdm.setShouldPayMoney(shouldPayMoney);
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行应缴纳金额是必须的\r\n");
            }

            // 应缴纳日期
            if (row.getCell(3) != null) {
                Date shouldPayDateStart;
                Date shouldPayDateEnd;
                try {
                    shouldPayDateStart = row.getCell(3).getDateCellValue();
                } catch (Exception e) {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行应缴纳日期不正确\r\n");
                    break;
                }
                shouldPayDateStart = DateUtil.getDateOfMonthStartDayTime(shouldPayDateStart);
                shouldPayDateEnd = DateUtil.getDateOfMonthEndDayTime(shouldPayDateStart);
                pmdm.setShouldPayDateStart(shouldPayDateStart);
                pmdm.setShouldPayDateEnd(shouldPayDateEnd);
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行应缴纳日期是必须的\r\n");
            }

            // 应缴纳说明
            if (row.getCell(4) != null) {
                pmdm.setShouldPayDescribe(row.getCell(4).getStringCellValue());
            }

            // 实缴纳金额
            if (row.getCell(5) != null && StringUtil.isNotEmpty(row.getCell(5).getStringCellValue())) {
            	if (new BigDecimal(row.getCell(5).getStringCellValue()).doubleValue() < 0) {
            		thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行实缴纳金额不能为负数\r\n");
            	}
                BigDecimal paidMoney = new BigDecimal(row.getCell(5).getStringCellValue());
                pmdm.setPaidMoney(paidMoney);
            } else {
                pmdm.setPaidMoney(new BigDecimal(0));
            }

            // 实缴纳日期
            if (row.getCell(6) != null) {
                Date paidDate;
                try {
                    paidDate = row.getCell(6).getDateCellValue();
                } catch (Exception e) {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行实缴纳日期不正确\r\n");
                    break;
                }
                pmdm.setPaidDate(paidDate);
            } else {
            	if (pmdm.getPaidMoney().doubleValue() > 0) {
            		thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行实缴纳日期是必须的\r\n");
            	}
            }

            // 缴纳备注
            if (row.getCell(7) != null) {
                pmdm.setPaidDescribe(row.getCell(7).getStringCellValue());
            }

            // 缴纳方式
            if (row.getCell(8) != null && StringUtil.isNotEmpty(row.getCell(8).getStringCellValue())) {
                PartyMembershipDuesPaidWay padpWay = new PartyMembershipDuesPaidWay();
                padpWay.setName(row.getCell(8).getStringCellValue());
                List<PartyMembershipDuesPaidWay> padpWays = partyMembershipDuesPaidWayMapper.queryPMDWs(padpWay);
                if (padpWays != null && padpWays.size() == 1) {
                    pmdm.setPaidWay(padpWays.get(0).getId());
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行没有该支付方式，请重新选择\r\n");
                }
            } else {
                PartyMembershipDuesPaidWay padpWay = new PartyMembershipDuesPaidWay();
                padpWay.setName("其他");
                padpWay = partyMembershipDuesPaidWayMapper.queryPMDWs(padpWay).get(0);
                pmdm.setPaidWay(padpWay.getId());
                validataErrorMsg.append("第" + (i + 1) + "行缴费方式已默认为 其他，（本条消息为提示消息，对导入不造成影响）\r\n");
            }
            
            if (thisValidateSuccess) {	//正确的情况下在设置缴纳状态
            	// 缴纳状态
                Map<String, Object> conditionMaps = new HashMap<>();
                conditionMaps.put("userId", pmdm.getUserId());
                conditionMaps.put("shouldPayDateStart", pmdm.getShouldPayDateStart());
                conditionMaps.put("shouldPayDateEnd", pmdm.getShouldPayDateEnd());
                conditionMaps.put("orgInfoId", pmdm.getOrgId());
                List<Map<String, Object>> partyMembershipDues =
                        partyMembershipDuesManageMapper.queryPartyMembershipDues(conditionMaps);
                PartyMembershipDuesStatus partyMembershipDuesStatus = new PartyMembershipDuesStatus();

                if (partyMembershipDues != null && !partyMembershipDues.isEmpty()) { // 该时间段已有数据，补缴
                    partyMembershipDuesStatus.setName("补缴");
                } else if (pmdm.getPaidMoney().compareTo(new BigDecimal(0)) == 0) { // 未缴
                    partyMembershipDuesStatus.setName("未缴");
                } else if (pmdm.getPaidMoney().compareTo(pmdm.getShouldPayMoney()) < 0) { // 未缴清
                    partyMembershipDuesStatus.setName("未缴清");
                } else if (pmdm.getPaidDate().after(pmdm.getShouldPayDateEnd())) { // 迟缴
                    partyMembershipDuesStatus.setName("迟缴");
                } else if (pmdm.getPaidDate().after(pmdm.getShouldPayDateStart()) // 按时缴清
                        && pmdm.getPaidDate().before(pmdm.getShouldPayDateEnd())
                        && (pmdm.getPaidMoney().compareTo(pmdm.getShouldPayMoney()) > 0
                                || pmdm.getPaidMoney().compareTo(pmdm.getShouldPayMoney()) == 0)) {
                    partyMembershipDuesStatus.setName("按时缴清");
                }
                pmdm.setPayStatus(partyMembershipDuesStatusMapper.queryPMDSs(partyMembershipDuesStatus).get(0).getId());
            }
            

            pmdms.add(pmdm);
        }


        return thisValidateSuccess;
    }

    /**
     * 批量导出学费缴纳记录
     * 
     * @param response
     * @param conditionMaps
     */
    public void exportPMDMExcel(HttpServletResponse response, @RequestParam Map<String, Object> conditionMaps) {
        List<Map<String, Object>> partyMembershipDues =
                partyMembershipDuesManageMapper.queryPartyMembershipDues(conditionMaps);
        String fn = "/exportPMDMExcel.xlsx";
        Workbook wb = this.createWorkboot(ExcelForPartyMembersshipDuesManage.OFFICE_EXCEL_2010,
                this.getClass().getResourceAsStream(fn));
        if (wb == null) throw new RRException("找不到文件:" + fn);
        Sheet hs = wb.getSheetAt(0); // 得到第一页

        CellStyle cellCenterStyle = wb.createCellStyle(); // 设置一个居中样式
        cellCenterStyle.setAlignment(HorizontalAlignment.CENTER);

        try {
            if (partyMembershipDues != null && !partyMembershipDues.isEmpty()) {
                for (int i = 0; i < partyMembershipDues.size(); i++) {
                    Map<String, Object> partyMembershipDue = partyMembershipDues.get(i);
                    Row row = hs.createRow(i + 2);
                    setCell(row, 0, cellCenterStyle, partyMembershipDue.get("name"));
                    setCell(row, 1, cellCenterStyle, partyMembershipDue.get("orgInfoName"));
                    setCell(row, 2, cellCenterStyle, partyMembershipDue.get("shouldPayMoney"));
                    setCell(row, 3, cellCenterStyle,
                            DateUtil.formatDate(DateUtil.YYYY_MM_DD,
                                    partyMembershipDue.get("shouldPayDateStart") == null
                                            || partyMembershipDue.get("shouldPayDateStart") == ""
                                                    ? null
                                                    : DateUtil.toDate(
                                                            DateUtil.YYYY_MM_DD,
                                                            partyMembershipDue.get("shouldPayDateStart").toString()))
                                    + " 至 "
                                    + DateUtil.formatDate(DateUtil.YYYY_MM_DD,
                                            partyMembershipDue.get("shouldPayDateEnd") == null
                                                    || partyMembershipDue.get("shouldPayDateEnd") == ""
                                                            ? null
                                                            : DateUtil.toDate(DateUtil.YYYY_MM_DD, partyMembershipDue
                                                                    .get("shouldPayDateEnd").toString())));
                    setCell(row, 4, cellCenterStyle, partyMembershipDue.get("shouldPayDescribe"));
                    setCell(row, 5, cellCenterStyle, partyMembershipDue.get("paidMoney"));
                    setCell(row, 6, cellCenterStyle, partyMembershipDue.get("paidDate"));
                    setCell(row, 7, cellCenterStyle, partyMembershipDue.get("paidDescribe"));
                    setCell(row, 8, cellCenterStyle, partyMembershipDue.get("paidWay"));
                    setCell(row, 9, cellCenterStyle, partyMembershipDue.get("payStatus"));
                }

            } else {
                Row row = hs.createRow(2);
                setCell(row, 0, cellCenterStyle, "没有查询到记录");
            }
            ByteArrayInputStream bain = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            wb.write(baos);
            byte[] b = baos.toByteArray();
            bain = new ByteArrayInputStream(b);
            if (baos != null) {
                baos.close();
            }
            this.downloadFile(response, bain, "党费缴纳记录" + ".xlsx");
        } catch (Exception e) {
            throw new RuntimeException();
        }
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
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(value.toString());
        } else {
            cell.setCellValue((String) value);
        }
    }



    /**
     * 下载党费缴纳记录导入excel格式示例
     * 
     * @param baseUser 条件
     * @return
     */
    public void exportPMDMExcelExample(HttpServletResponse response) {
        downloadFile(response,
                ExcelForPartyMembersshipDuesManage.class.getResourceAsStream("/importPMDMExcelExample.xlsx"),
                "党费缴纳记录导入示例.xlsx");
    }



    /**
     * 下载文件
     * 
     * @param response
     * @param is
     * @throws Exception
     */
    private void downloadFile(HttpServletResponse response, InputStream is, String fileName) {
        try (OutputStream os = response.getOutputStream(); BufferedInputStream bis = new BufferedInputStream(is);) {
            
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            byte[] buff = new byte[1024];

            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, i);
                os.flush();
                i = bis.read(buff);
            }
        } catch (Exception e) {
            RRException.makeThrow(e);
        } 
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
            if (OFFICE_EXCEL_2010.equalsIgnoreCase(fileSuffix)) {
                return new XSSFWorkbook(is);
            } else if (OFFICE_EXCEL_2003.equalsIgnoreCase(fileSuffix)) {
                return new HSSFWorkbook(is);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
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
