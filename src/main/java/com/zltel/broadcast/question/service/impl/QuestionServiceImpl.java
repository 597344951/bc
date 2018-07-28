package com.zltel.broadcast.question.service.impl;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.question.bean.Answer;
import com.zltel.broadcast.question.bean.Category;
import com.zltel.broadcast.question.bean.Question;
import com.zltel.broadcast.question.bean.Subject;
import com.zltel.broadcast.question.dao.AnswerMapper;
import com.zltel.broadcast.question.dao.CategoryMapper;
import com.zltel.broadcast.question.dao.QuestionMapper;
import com.zltel.broadcast.question.dao.SubjectMapper;
import com.zltel.broadcast.question.service.QuestionService;
import io.swagger.models.auth.In;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public int addSubject(String name, String desc) {
        return subjectMapper.insertSelective(new Subject(name, desc));
    }

    @Override
    public List<Subject> querySubject() {
        return subjectMapper.query();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public int addCategory(String name, String desc) {
        return categoryMapper.insertSelective(new Category(name, desc));
    }

    @Override
    public List<Category> queryCategory() {
        return categoryMapper.query();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public int addQuestion(Question question) {
        Date date = new Date();
        question.setAddDate(date);
        question.setUpdateDate(date);
        int rows = questionMapper.insert(question);
        List<Answer> answers = question.getAnswers();
        answers.stream().forEach(answer -> {
            answer.setQuestionId(question.getQuestionId());
            answer.setAddDate(date);
            answer.setUpdateDate(date);
            answerMapper.insertSelective(answer);
        });
        return rows;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void removeQuestion(Question question) {
        question.getAnswers().stream().forEach(answer -> {
            answerMapper.deleteByPrimaryKey(answer.getAnswerId());
        });
        questionMapper.deleteByPrimaryKey(question.getQuestionId());
    }

    @Override
    public List<Question> query(int orgId, String keyword, List<Integer> type, List<Integer> subject, List<Integer> category, int pageNum, int pageSize) {
        int rowStart = (pageNum - 1) * pageSize;
        return questionMapper.query(orgId, keyword, type, subject, category, rowStart, pageSize);
    }

    @Override
    public long count(int orgId, String keyword, List<Integer> type, List<Integer> subject, List<Integer> category) {
        return questionMapper.count(orgId, keyword, type, subject, category);
    }

    @Override
    public R importQuestion(int orgId, int type, int subject, int category, File questionFile) {
        Map result = new HashMap();
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(questionFile);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error("上传文件有误: " + e.getMessage());
        }
        R r = R.ok();
        Sheet sheet = workbook.getSheetAt(0);
        int rowNum = -1;
        int correctCount = 0;
        List<Integer> errorRows = new ArrayList<>();
        for (Row row : sheet) {
            rowNum++;
            if (rowNum == 0) {
                //第一行跳过
                continue;
            }
            try {

                String questionContent = row.getCell(0).getStringCellValue();
                double answerCount = row.getCell(1).getNumericCellValue();
                double correctAnswerIndex = row.getCell(2).getNumericCellValue();
                List<Answer> answers = new ArrayList<>();
                for (Double i = 1d; i <= answerCount; i++) {
                    answers.add(new Answer(getCellText(row.getCell(i.intValue() + 2)), i == correctAnswerIndex ? 1 : 0));
                }
                addQuestion(new Question(orgId, questionContent, type, subject, category, answers));
                correctCount++;
            } catch (Exception e) {
                //添加错误
                log.warn(e.getMessage());
                errorRows.add(rowNum + 1);
            }

        }
        r.put("total", rowNum);
        r.put("correct", correctCount);
        r.put("errorRow", errorRows);
        FileUtils.deleteQuietly(questionFile);
        return r;

    }

    @Override
    public List<Question> randomQuestion(int orgId, String keyword, List<Integer> type, List<Integer> subject, List<Integer> category, int size) {
        return questionMapper.rand(orgId, keyword, type, subject, category, size);
    }

    private String getCellText(Cell cell) {
        if(cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if(cell.getCellTypeEnum() == CellType.NUMERIC) {
            if(HSSFDateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(cell.getDateCellValue());
            } else {
                return "" + Double.valueOf(cell.getNumericCellValue()).intValue();
            }
        } else {
            throw new RuntimeException("内容不可用...");
        }
    }
}
