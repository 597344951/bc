package com.zltel.broadcast.question.bean;

import java.util.Date;
import java.util.List;

public class Question {
    private Integer questionId;

    private Integer orgId;

    private String content;

    private Type type;

    private Category category;

    private Subject subject;

    private List<Answer> answers;

    private Date addDate;

    private Date updateDate;

    public Question() {
    }

    public Question(int orgId, String content, int type, int subject, int category, List<Answer> answers) {
        this.orgId = orgId;
        this.content = content;
        this.type = new Type(type);
        this.category = new Category(category);
        this.subject = new Subject(subject);
        this.answers = answers;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}