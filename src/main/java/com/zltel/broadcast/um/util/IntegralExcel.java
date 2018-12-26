package com.zltel.broadcast.um.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationInformation;
import com.zltel.broadcast.um.bean.PartyIntegralRecord;
import com.zltel.broadcast.um.dao.IntegralConstituteMapper;
import com.zltel.broadcast.um.dao.OrganizationInformationMapper;
import com.zltel.broadcast.um.dao.OrganizationRelationMapper;
import com.zltel.broadcast.um.dao.PartyIntegralRecordMapper;
import com.zltel.broadcast.um.service.IntegralConstituteService;

/**
 * 积分excel
 * @author 张毅
 * @since jdk 1.8.0
 * date: 2018.12.19
 */
@Component
public class IntegralExcel {
	private final static String OFFICE_EXCEL_2003 = "XLS";
    private final static String OFFICE_EXCEL_2010 = "XLSX";
    @Resource
    private OrganizationInformationMapper organizationInformationMapper;
    @Resource
    private IntegralConstituteService integralConstituteService;
    @Resource
    private OrganizationRelationMapper organizationRelationMapper;
    @Resource
    private IntegralConstituteMapper integralConstituteMapper;
    @Resource
    private PartyIntegralRecordMapper partyIntegralRecordMapper;
	
	/**
     * 批量导入积分记录明细
     * 
     * @param file 文件
     * @return
     */
    public R importIntegralExcel(HttpServletResponse response, MultipartFile file) throws Exception {
    	Workbook wb = null;
        if (file == null) return R.error().setMsg("导入失败").setData("请选择导入文件");
        wb = this.createWorkboot(this.getExcelSuffix(file.getOriginalFilename()), file.getInputStream());
        Sheet hs = wb.getSheetAt(0);
        StringBuffer validataErrorMsg = new StringBuffer(); // 保存错误信息
        boolean validateSuccess = true;
        List<PartyIntegralRecord> pirs = new ArrayList<>();
        validateSuccess = validateImportIntegralExcel(hs, validataErrorMsg, validateSuccess, pirs); // 本次验证是否通过
        if (!validateSuccess) {
            return R.error().setMsg("导入失败，请查看失败信息 ：导入错误信息.txt").setData(validataErrorMsg);
        }
        
        if (pirs.size() > 0) {
        	for (PartyIntegralRecord pir : pirs) {
        		partyIntegralRecordMapper.insertSelective(pir);
			}
        }
        return R.ok().setMsg("信息导入成功");
    }
    
    @SuppressWarnings("unchecked")
	private boolean validateImportIntegralExcel(Sheet hs, StringBuffer validataErrorMsg, boolean validateSuccess, 
			List<PartyIntegralRecord> pirs) {
    	for (int i = 2;; i++) {// 从第3行开始读取
    		Row row = hs.getRow(i);
            if (row == null) {
                break;
            }
            
            PartyIntegralRecord pir = new PartyIntegralRecord();
            
            //组织编号
            if (row.getCell(0) != null && StringUtil.isNotEmpty(row.getCell(0).getStringCellValue())) {
            	OrganizationInformation oi = new OrganizationInformation();
            	oi.setOrgInfoId(Integer.parseInt(String.valueOf(row.getCell(0).getStringCellValue())));
            	List<OrganizationInformation> orgInfos = organizationInformationMapper.queryOrgInfos(oi);
            	if (orgInfos != null && orgInfos.size() > 0) {
            		//查询积分是否设置完毕
            		Map<String, Object> condition = new HashMap<>();
            		condition.put("orgId", Integer.parseInt(String.valueOf(row.getCell(0).getStringCellValue())));
            		condition.put("parentIcId", -1);
            		HashMap<String, Object> result = (HashMap<String, Object>)integralConstituteService.queryOrgIntegralInfo(condition).get("data");
            		if (!(boolean)result.get("integralError")) {
            			pir.setOrgId(Integer.parseInt(String.valueOf(row.getCell(0).getStringCellValue())));
            		} else {
            			validateSuccess = false;
                        validataErrorMsg.append("第" + (i + 1) + "行该组织的积分没有配置完毕，请在此页面找到该组织配置积分。\r\n");
            		}
            	} else {
            		validateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行按照组织编号没有查询到此组织。\r\n");
            	}
            } else {
            	validateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行组织编号不能为空。\r\n");
            }
            
            //用户
            if (row.getCell(1) != null && StringUtil.isNotEmpty(row.getCell(1).getStringCellValue())) {
            	if (pir.getOrgId() != null) {
            		//现有组织，才有组织下的用户
					String idCard = row.getCell(1).getStringCellValue();
					if (RegexUtil.isIDCard(idCard)) {
						Map<String, Object> condition = new HashMap<>();
						condition.put("idCard", idCard);
						condition.put("orgRltInfoId", pir.getOrgId());
						List<Map<String, Object>> ors = organizationRelationMapper.queryOrgRelationsNew(condition);
						if (ors != null && ors.size() > 0) {
							pir.setPartyId(Integer.parseInt(String.valueOf(ors.get(0).get("baseUserId"))));
						} else {
							validateSuccess = false;
							validataErrorMsg.append("第" + (i + 1) + "行该组织下没有此用户。\r\n");
						}
					} else {
						validateSuccess = false;
						validataErrorMsg.append("第" + (i + 1) + "行身份证号码格式不正确。\r\n");
					} 
				}
            } else {
            	validateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户身份证号不能为空。\r\n");
            }
            
            //积分类型
            if (row.getCell(2) != null && StringUtil.isNotEmpty(row.getCell(2).getStringCellValue())) {
            	if (pir.getOrgId() != null) {
            		//现有组织，才有积分类型
            		Map<String, Object> conditions = new HashMap<>();
            		conditions.put("orgId", pir.getOrgId());
            		conditions.put("type", row.getCell(2).getStringCellValue());
            		List<Map<String, Object>> ics = integralConstituteMapper.queryOrgIntegralConstitute(conditions);
                    if (ics != null && ics.size() > 0) {
                    	Map<String, Object> ic = ics.get(0);
                    	conditions.remove("type");
                    	conditions.put("parentIcId", ic.get("icId"));
                    	ics = integralConstituteMapper.queryOrgIntegralConstitute(conditions);
                    	if (ics != null && ics.size() > 0) {
                    		validateSuccess = false;
                            validataErrorMsg.append("第" + (i + 1) + "行该积分类型不能用于计算积分。\r\n");
                    	} else {
                    		pir.setChangeTypeId(Integer.parseInt(String.valueOf(ic.get("icId"))));
                    	}
                    } else {
                    	validateSuccess = false;
                        validataErrorMsg.append("第" + (i + 1) + "行没有此积分类型。\r\n");
                    }
				}
            } else {
            	validateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行积分类型不能为空。\r\n");
            }
            
            //变更操作
            if (row.getCell(3) != null && StringUtil.isNotEmpty(row.getCell(3).getStringCellValue())) {
            	if ("加分".equals(row.getCell(3).getStringCellValue())) {
            		pir.setChangeIntegralType(1);
            	} else if ("扣分".equals(row.getCell(3).getStringCellValue())) {
            		pir.setChangeIntegralType(0);
            	} else {
            		validateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行变更操作设置错误。\r\n");
            	}
            } else {
            	validateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行变更操作不能为空。\r\n");
            }
            
            //变更分值
            if (row.getCell(4) != null && StringUtil.isNotEmpty(row.getCell(4).getStringCellValue())) {
            	if (pir.getChangeIntegralType() != null) {
					try {
						BigDecimal integralF = new BigDecimal(row.getCell(4).getStringCellValue());
						if (pir.getChangeIntegralType() == 1 && integralF.doubleValue() < 0) {
							validateSuccess = false;
							validataErrorMsg.append("第" + (i + 1) + "行加分变更分值应该大于0。\r\n");
							break;
						} else if (pir.getChangeIntegralType() == 0 && integralF.doubleValue() > 0) {
							validateSuccess = false;
							validataErrorMsg.append("第" + (i + 1) + "行扣分变更分值应该小于0。\r\n");
							break;
						}
						pir.setChangeScore(integralF);
					} catch (Exception e) {
						validateSuccess = false;
						validataErrorMsg.append("第" + (i + 1) + "行变更分值填写有误。\r\n");
					} 
				}
            } else {
            	validateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行变更分值不能为空。\r\n");
            }
            
            //分值变更说明
            if (row.getCell(5) != null && StringUtil.isNotEmpty(row.getCell(5).getStringCellValue())) {
            	pir.setChangeDescribes(row.getCell(5).getStringCellValue());
            }
            
            pirs.add(pir);
    	}
    	return validateSuccess;
    }
	
	/**
     * 下载积分记录明细导入excel格式示例
     * 
     * @param baseUser 条件
     * @return
     */
    public void exportIntegralExcelExample(HttpServletResponse response) {
        downloadFile(response,
        		IntegralExcel.class.getResourceAsStream("/importIntegralExample.xlsx"),
                "积分明细导入示例.xlsx");
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
