package com.zltel.broadcast.um.bean;

import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Boolean status;

    private Integer orgId;

    private Date createTime;

    private Date beforeTime; // 用于时间区间查询

    private Date afterTime; // 用于时间区间查询

    private Integer userType;

    private Date lastSignInTime;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getLastSignInTime() {
        return lastSignInTime;
    }

    public void setLastSignInTime(Date lastSignInTime) {
        this.lastSignInTime = lastSignInTime;
    }

    /** 主题颜色 **/
    private String theme = "";
    /** 与此登陆账户关联的 基础用户信息 **/
    private BaseUserInfo baseUserInfo;

    /** 获取登录显示名 **/
    public String getDisName() {
        if (null != baseUserInfo) return baseUserInfo.getName();
        return username;
    }
    
    public String getTrueName() {
        return this.getDisName();
    }

    /**
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysUser [");
        if (userId != null) {
            builder.append("userId=");
            builder.append(userId);
            builder.append(", ");
        }
        if (username != null) {
            builder.append("username=");
            builder.append(username);
            builder.append(", ");
        }
        if (status != null) {
            builder.append("status=");
            builder.append(status);
        }
        builder.append("]");
        return builder.toString();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }



    /**
     * @return the status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBeforeTime() {
        return beforeTime;
    }

    public void setBeforeTime(Date beforeTime) {
        this.beforeTime = beforeTime;
    }

    public Date getAfterTime() {
        return afterTime;
    }

    public void setAfterTime(Date afterTime) {
        this.afterTime = afterTime;
    }

    /**
     * 获取theme
     * 
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * 设置theme
     * 
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public BaseUserInfo getBaseUserInfo() {
        return baseUserInfo;
    }

    public void setBaseUserInfo(BaseUserInfo baseUserInfo) {
        this.baseUserInfo = baseUserInfo;
    }

}
