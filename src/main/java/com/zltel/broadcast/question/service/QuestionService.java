package com.zltel.broadcast.question.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.question.bean.Category;
import com.zltel.broadcast.question.bean.Question;
import com.zltel.broadcast.question.bean.Subject;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface QuestionService {
    public int addSubject(String name, String desc);

    public List<Subject> querySubject();

    public int addCategory(String name, String desc);

    public List<Category> queryCategory();

    public int addQuestion(Question question);

    public List<Question> query(int orgId,
                                String keyword,
                                List<Integer> type,
                                List<Integer> subject,
                                List<Integer> category,
                                int pageNum,
                                int pageSize);

    public long count(int orgId,
                      String keyword,
                      List<Integer> type,
                      List<Integer> subject,
                      List<Integer> category);

    public R importQuestion(int orgId, int type, int subject, int category, File questionFile);

    public void removeQuestion(Question question);

    public List<Question> randomQuestion(int orgId,
                                         String keyword,
                                         List<Integer> type,
                                         List<Integer> subject,
                                         List<Integer> category,
                                         int size);
}
