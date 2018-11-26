package com.zltel.broadcast.um.bean;

import java.util.Date;

public class BaseUserInfo {
    private Integer baseUserId;

    private String name;

    private String sex;

    private Integer nation;

    private String nativePlace;

    private String homeAddressProvince;

    private String homeAddressCity;

    private String homeAddressArea;

    private String homeAddressDetail;

    private String presentAddressProvince;

    private String presentAddressCity;

    private String presentAddressArea;

    private String presentAddressDetail;

    private String idCard;

    private Date birthDate;

    private String mobilePhone;

    private String email;

    private String qq;

    private String wechat;

    private String idPhoto;

    private Integer education;

    private Integer academicDegree;

    private String major;

    private Date enrolmentTime;

    private Date graduationTime;

    private String graduationSchool;

    private String specialityLiterature;

    private String specialityMajor;

    private String marriageStatus;

    private String childrenStatus;
    
    private Integer isParty;
    
    private Integer positiveUser;
    
    private String workUnit;

    private Integer workNature;

    private Date joinWorkDate;

    private Integer appointmentTimeLength;
    
    private Integer firstLineSituation;
    
    private Integer income;
    
    private Integer partyProportion;
    
    private Integer devPeople;
    
    public Integer getDevPeople() {
		return devPeople;
	}

	public void setDevPeople(Integer devPeople) {
		this.devPeople = devPeople;
	}

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public Integer getPartyProportion() {
		return partyProportion;
	}

	public void setPartyProportion(Integer partyProportion) {
		this.partyProportion = partyProportion;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public Integer getWorkNature() {
		return workNature;
	}

	public void setWorkNature(Integer workNature) {
		this.workNature = workNature;
	}

	public Date getJoinWorkDate() {
		return joinWorkDate;
	}

	public void setJoinWorkDate(Date joinWorkDate) {
		this.joinWorkDate = joinWorkDate;
	}

	public Integer getAppointmentTimeLength() {
		return appointmentTimeLength;
	}

	public void setAppointmentTimeLength(Integer appointmentTimeLength) {
		this.appointmentTimeLength = appointmentTimeLength;
	}

	public Integer getFirstLineSituation() {
		return firstLineSituation;
	}

	public void setFirstLineSituation(Integer firstLineSituation) {
		this.firstLineSituation = firstLineSituation;
	}

	public Integer getPositiveUser() {
        return positiveUser;
    }

    public void setPositiveUser(Integer positiveUser) {
        this.positiveUser = positiveUser;
    }

    public Integer getIsParty() {
		return isParty;
	}

	public void setIsParty(Integer isParty) {
		this.isParty = isParty;
	}

	public Integer getBaseUserId() {
        return baseUserId;
    }

    public void setBaseUserId(Integer baseUserId) {
        this.baseUserId = baseUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getHomeAddressProvince() {
        return homeAddressProvince;
    }

    public void setHomeAddressProvince(String homeAddressProvince) {
        this.homeAddressProvince = homeAddressProvince == null ? null : homeAddressProvince.trim();
    }

    public String getHomeAddressCity() {
        return homeAddressCity;
    }

    public void setHomeAddressCity(String homeAddressCity) {
        this.homeAddressCity = homeAddressCity == null ? null : homeAddressCity.trim();
    }

    public String getHomeAddressArea() {
        return homeAddressArea;
    }

    public void setHomeAddressArea(String homeAddressArea) {
        this.homeAddressArea = homeAddressArea == null ? null : homeAddressArea.trim();
    }

    public String getHomeAddressDetail() {
        return homeAddressDetail;
    }

    public void setHomeAddressDetail(String homeAddressDetail) {
        this.homeAddressDetail = homeAddressDetail == null ? null : homeAddressDetail.trim();
    }

    public String getPresentAddressProvince() {
        return presentAddressProvince;
    }

    public void setPresentAddressProvince(String presentAddressProvince) {
        this.presentAddressProvince = presentAddressProvince == null ? null : presentAddressProvince.trim();
    }

    public String getPresentAddressCity() {
        return presentAddressCity;
    }

    public void setPresentAddressCity(String presentAddressCity) {
        this.presentAddressCity = presentAddressCity == null ? null : presentAddressCity.trim();
    }

    public String getPresentAddressArea() {
        return presentAddressArea;
    }

    public void setPresentAddressArea(String presentAddressArea) {
        this.presentAddressArea = presentAddressArea == null ? null : presentAddressArea.trim();
    }

    public String getPresentAddressDetail() {
        return presentAddressDetail;
    }

    public void setPresentAddressDetail(String presentAddressDetail) {
        this.presentAddressDetail = presentAddressDetail == null ? null : presentAddressDetail.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto == null ? null : idPhoto.trim();
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(Integer academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public Date getEnrolmentTime() {
        return enrolmentTime;
    }

    public void setEnrolmentTime(Date enrolmentTime) {
        this.enrolmentTime = enrolmentTime;
    }

    public Date getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(Date graduationTime) {
        this.graduationTime = graduationTime;
    }

    public String getGraduationSchool() {
        return graduationSchool;
    }

    public void setGraduationSchool(String graduationSchool) {
        this.graduationSchool = graduationSchool == null ? null : graduationSchool.trim();
    }

    public String getSpecialityLiterature() {
        return specialityLiterature;
    }

    public void setSpecialityLiterature(String specialityLiterature) {
        this.specialityLiterature = specialityLiterature == null ? null : specialityLiterature.trim();
    }

    public String getSpecialityMajor() {
        return specialityMajor;
    }

    public void setSpecialityMajor(String specialityMajor) {
        this.specialityMajor = specialityMajor == null ? null : specialityMajor.trim();
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus == null ? null : marriageStatus.trim();
    }

    public String getChildrenStatus() {
        return childrenStatus;
    }

    public void setChildrenStatus(String childrenStatus) {
        this.childrenStatus = childrenStatus == null ? null : childrenStatus.trim();
    }
}