package com.zltel.broadcast.terminal.bean;

import java.util.Date;
import java.util.List;

public class TerminalBasicInfo {
    private Integer oid;

    private String name;

    private String id;

    private String code;

    private String typeId;

    private String resTime;

    private String online;

    private String lastTime;

    private String ip;

    private String mac;

    private String sys;

    private String size;

    private String ratio;

    private String rev;

    private String ver;

    private String typ;

    private String tel;

    private String addr;

    private String gis;

    private String warranty;

    private String loc;

    /** 屏幕截图 **/
    private String coverImage;
    /** 最后同步时间 **/
    private Date lastSynTime;
    /** 所属组织id **/
    private Integer orgId;

    private Date orgConfigTime;
    /** 多个组织id **/
    private List<Integer> orgIds;
    /**关联的组织信息**/
    private com.zltel.broadcast.um.bean.OrganizationInformation orgInfo;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getGis() {
        return gis;
    }

    public void setGis(String gis) {
        this.gis = gis;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getResTime() {
        return resTime;
    }

    public void setResTime(String resTime) {
        this.resTime = resTime == null ? null : resTime.trim();
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online == null ? null : online.trim();
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime == null ? null : lastTime.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys == null ? null : sys.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio == null ? null : ratio.trim();
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev == null ? null : rev.trim();
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver == null ? null : ver.trim();
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ == null ? null : typ.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Date getLastSynTime() {
        return lastSynTime;
    }

    public void setLastSynTime(Date lastSynTime) {
        this.lastSynTime = lastSynTime;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Date getOrgConfigTime() {
        return orgConfigTime;
    }

    public void setOrgConfigTime(Date orgConfigTime) {
        this.orgConfigTime = orgConfigTime;
    }

    public List<Integer> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Integer> orgIds) {
        this.orgIds = orgIds;
    }

    public com.zltel.broadcast.um.bean.OrganizationInformation getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(com.zltel.broadcast.um.bean.OrganizationInformation orgInfo) {
        this.orgInfo = orgInfo;
    }

}
