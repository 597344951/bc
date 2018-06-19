package com.zltel.broadcast.um.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.BaseUser;
import com.zltel.broadcast.um.bean.PartyUser;
import com.zltel.broadcast.um.dao.BaseUserMapper;
import com.zltel.broadcast.um.dao.PartyUserMapper;
import com.zltel.broadcast.um.service.ExcelService;

@Service
public class ExcelServiceImpl extends BaseDaoImpl<PartyUser> implements ExcelService {
    @Resource
    private PartyUserMapper partyUserMapper;
    @Resource
    private BaseUserMapper baseUserMapper;

    static StringBuffer validataErrorMsg = new StringBuffer(); // 保存错误信息

    private final static String OFFICE_EXCEL_2003 = "XLS";
    private final static String OFFICE_EXCEL_2010 = "XLSX";

    @Override
    public BaseDao<PartyUser> getInstince() {
        return this.partyUserMapper;
    }

    /**
     * 批量导入党员信息
     * 
     * @param baseUser 条件
     * @return
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public synchronized R importPartyUsersExcel(HttpServletResponse response, MultipartFile file) throws Exception {
        Workbook wb = null;
        wb = this.createWorkboot(this.getExcelSuffix(file.getOriginalFilename()), file.getInputStream());
        if (wb == null) throw new RRException("找不到:" + file.getOriginalFilename());
        Sheet hs = wb.getSheetAt(0); // 得到第一页
        List<PartyUser> partys = new ArrayList<PartyUser>();
        boolean thisValidateSuccess = true; // 本次验证是否通过
        for (int i = 2;; i++) { // 从第3行开始读取
            Row row = hs.getRow(i);
            if (row == null) {
                break;
            }
            PartyUser pu = new PartyUser();
            if (row.getCell(0) != null && StringUtil.isNotEmpty(row.getCell(0).getStringCellValue())) {
                pu.setName(row.getCell(0).getStringCellValue()); // 用户名字
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户名不能为空。\r\n");
            }
            if (row.getCell(1) != null && StringUtil.isNotEmpty(row.getCell(1).getStringCellValue())) {
                if (row.getCell(1).getStringCellValue().equals("男")
                        || row.getCell(1).getStringCellValue().equals("女")) {
                    pu.setSex(row.getCell(1).getStringCellValue().equals("男") ? 1 : 0); // 用户性别
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行用户不能出现第三性别。\r\n");
                }
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户性别不能为空。\r\n");
            }
            if (row.getCell(2) != null && StringUtil.isNotEmpty(row.getCell(2).getStringCellValue())) {
                BaseUser bu = new BaseUser();
                bu.setIdCard(row.getCell(2).getStringCellValue());
                List<BaseUser> havaUser = baseUserMapper.queryBaseUsers(bu);
                if (havaUser == null || havaUser.size() == 0) {
                    pu.setIdCard(row.getCell(2).getStringCellValue()); // 用户身份证号码
                } else {
                    thisValidateSuccess = false;
                    validataErrorMsg.append("第" + (i + 1) + "行用户已存在，不能再次添加。\r\n");
                }
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户身份证号码不能为空。\r\n");
            }
            pu.setPhone(row.getCell(3).getStringCellValue()); // 用户联系电话
            pu.setAddress(row.getCell(4).getStringCellValue()); // 用户居住地址
            pu.setNation(row.getCell(5).getStringCellValue()); // 名族
            pu.setEduLevel(row.getCell(6).getStringCellValue()); // 教育程度
            pu.setEmail(row.getCell(7).getStringCellValue()); // 邮箱
            pu.setQq(row.getCell(8).getStringCellValue()); // qq
            pu.setWechat(row.getCell(9).getStringCellValue()); // 微信
            pu.setPoliticalBg(row.getCell(10).getStringCellValue()); // 政治背景
            if (row.getCell(11) != null) {
                pu.setJoinDate(row.getCell(11).getDateCellValue()); // 入党时间
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户入党时间不能为空。\r\n");
            }
            if (row.getCell(12) != null && StringUtil.isNotEmpty(row.getCell(12).getStringCellValue())) {
                pu.setPartyStaff(row.getCell(12).getStringCellValue().equals("是") ? 1 : 0);
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户是否党务工作者不能为空。\r\n");
            }
            if (row.getCell(13) != null && StringUtil.isNotEmpty(row.getCell(13).getStringCellValue())) {
                pu.setPartRepresentative(row.getCell(13).getStringCellValue().equals("是") ? 1 : 0);
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户是否党代表不能为空。\r\n");
            }
            if (row.getCell(14) != null && StringUtil.isNotEmpty(row.getCell(14).getStringCellValue())) {
                pu.setVolunteer(row.getCell(14).getStringCellValue().equals("是") ? 1 : 0);
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户是否志愿者不能为空。\r\n");
            }
            if (row.getCell(15) != null && StringUtil.isNotEmpty(row.getCell(15).getStringCellValue())) {
                pu.setDifficultMember(row.getCell(15).getStringCellValue().equals("是") ? 1 : 0);
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户是否困难党员不能为空。\r\n");
            }
            if (row.getCell(16) != null && StringUtil.isNotEmpty(row.getCell(16).getStringCellValue())) {
                pu.setAdvancedMember(row.getCell(16).getStringCellValue().equals("是") ? 1 : 0);
            } else {
                thisValidateSuccess = false;
                validataErrorMsg.append("第" + (i + 1) + "行用户是否先进党员不能为空。\r\n");
            }
            pu.setIsParty(1); // 是党员
            pu.setReserveMember(0); // 不是预备党员
            partys.add(pu);
        }

        if (!thisValidateSuccess) {
            return R.error().setMsg("导入失败，请查看失败信息 ：导入错误信息.txt");
        }

        // 开始添加
        if (partys.size() > 0) {
            for (PartyUser pu : partys) {
                int count = baseUserMapper.insertSelective(pu);
                if (count != 1) {
                    throw new Exception();
                }
                BaseUser bu = baseUserMapper.queryBaseUsers(pu).get(0);
                pu.setUid(bu.getUid());
                partyUserMapper.insertSelective(pu);
            }
        } else {
            return R.error().setMsg("没有要导入的用户信息");
        }

        return R.ok().setMsg("信息导入成功");
    }

    /**
     * 下载错误信息
     * 
     * @param response 条件
     * @return
     */
    public R downloadValidataMsg(HttpServletResponse response) throws Exception {
        if (StringUtil.isNotEmpty(validataErrorMsg.toString())) {
            this.downloadFile(response, new ByteArrayInputStream(validataErrorMsg.toString().getBytes()), "导入错误信息.txt"); // 下载错误提示信息
            validataErrorMsg = new StringBuffer();
            return R.ok().setMsg("信息校验结果");
        } else {
            return R.ok();
        }
    }

    /**
     * 导出党员信息
     * 
     * @param response 条件
     * @return
     */
    public R exportPartyUsersExcel(HttpServletResponse response, PartyUser partyUser) throws Exception {
        BaseUser bu = baseUserMapper.queryBaseUsers(partyUser).get(0); // 下载此党员信息，一定有数据
        PartyUser pu = partyUserMapper.queryPartyUsers(partyUser).get(0);
        Workbook wb = null;
        wb = this.createWorkboot(ExcelServiceImpl.OFFICE_EXCEL_2010,
                this.getClass().getResourceAsStream("/exportPartyUserExcel.xlsx"));
        if (wb == null) throw new RRException("找不到: /exportPartyUserExcel.xlsx");
        Sheet hs = wb.getSheetAt(0); // 得到第一页
        Row row1 = hs.getRow(0);
        row1.getCell(0).setCellValue("党员信息-" + bu.getName()); // 标题，模板有内容，用getCell

        CellStyle cellCenterStyle = wb.createCellStyle();
        cellCenterStyle.setAlignment(HorizontalAlignment.CENTER);

        Row row3 = hs.getRow(2);
        Cell row3cell2 = row3.createCell(1);
        Cell row3cell4 = row3.createCell(3);
        Cell row3cell6 = row3.createCell(5);
        row3cell2.setCellStyle(cellCenterStyle);
        row3cell4.setCellStyle(cellCenterStyle);
        row3cell6.setCellStyle(cellCenterStyle);
        row3cell2.setCellValue(bu.getName());
        row3cell4.setCellValue(bu.getSex() == 0 ? "女" : "男");
        row3cell6.setCellValue(bu.getIdCard());

        Row row4 = hs.getRow(3);
        Cell row4cell2 = row4.createCell(1);
        Cell row4cell4 = row4.createCell(3);
        Cell row4cell6 = row4.createCell(5);
        row4cell2.setCellStyle(cellCenterStyle);
        row4cell4.setCellStyle(cellCenterStyle);
        row4cell6.setCellStyle(cellCenterStyle);
        row4cell2.setCellValue(bu.getAddress());
        row4cell4.setCellValue(bu.getEduLevel());
        row4cell6.setCellValue(bu.getPhone());

        Row row5 = hs.getRow(4);
        Cell row5cell2 = row5.createCell(1);
        Cell row5cell4 = row5.createCell(3);
        Cell row5cell6 = row5.createCell(5);
        row5cell2.setCellStyle(cellCenterStyle);
        row5cell4.setCellStyle(cellCenterStyle);
        row5cell6.setCellStyle(cellCenterStyle);
        row5cell2.setCellValue(bu.getNation());
        row5cell4.setCellValue(bu.getEmail());
        row5cell6.setCellValue(bu.getQq());

        Row row6 = hs.getRow(5);
        Cell row6cell2 = row6.createCell(1);
        row6cell2.setCellStyle(cellCenterStyle);
        row6cell2.setCellValue(bu.getWechat());

        Row row8 = hs.getRow(7);
        Cell row8cell2 = row8.createCell(1);
        Cell row8cell4 = row8.createCell(3);
        Cell row8cell6 = row8.createCell(5);
        row8cell2.setCellStyle(cellCenterStyle);
        row8cell4.setCellStyle(cellCenterStyle);
        row8cell6.setCellStyle(cellCenterStyle);
        row8cell2.setCellValue(pu.getPartyStaff() == 0 ? "否" : "是");
        row8cell4.setCellValue(pu.getPartRepresentative() == 0 ? "否" : "是");
        row8cell6.setCellValue(pu.getVolunteer() == 0 ? "否" : "是");

        Row row9 = hs.getRow(8);
        Cell row9cell2 = row9.createCell(1);
        Cell row9cell4 = row9.createCell(3);
        Cell row9cell6 = row9.createCell(5);
        row9cell2.setCellStyle(cellCenterStyle);
        row9cell4.setCellStyle(cellCenterStyle);
        row9cell6.setCellStyle(cellCenterStyle);
        row9cell2.setCellValue(pu.getDifficultMember() == 0 ? "否" : "是");
        row9cell4.setCellValue(pu.getAdvancedMember() == 0 ? "否" : "是");
        row9cell6.setCellValue(pu.getReserveMember() == 0 ? "否" : "是");

        Row row10 = hs.getRow(9);
        Cell row10cell2 = row10.createCell(1);
        Cell row10cell4 = row10.createCell(3);
        Cell row10cell6 = row10.createCell(5);
        row10cell2.setCellStyle(cellCenterStyle);
        row10cell4.setCellStyle(cellCenterStyle);
        row10cell6.setCellStyle(cellCenterStyle);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        row10cell2.setCellValue(pu.getReserveApprovalDate() == null ? null : sdf.format(pu.getReserveApprovalDate()));
        row10cell4.setCellValue(bu.getPoliticalBg());
        row10cell6.setCellValue(pu.getJoinDate() == null ? null : sdf.format(pu.getJoinDate()));


        ByteArrayInputStream bain = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);
        byte[] b = baos.toByteArray();
        bain = new ByteArrayInputStream(b);
        if (baos != null) {
            baos.close();
        }
        this.downloadFile(response, bain, "党员信息-" + bu.getName() + ".xlsx");
        return null;
    }

    /**
     * 下载党员导入excel格式示例
     * 
     * @param baseUser 条件
     * @return
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public void exportPartyUsersExcelExample(HttpServletResponse response) throws Exception {
        this.downloadFile(response, this.getClass().getResourceAsStream("/importPartyUsersExcelExample.xlsx"),
                "党员信息导入示例.xlsx");
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
        try {
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, i);
                os.flush();
                i = bis.read(buff);
            }
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(os);
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
}
