package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.util.StringUtil;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.DeedsImg;
import com.zltel.broadcast.um.bean.DeedsType;
import com.zltel.broadcast.um.bean.DeedsUser;
import com.zltel.broadcast.um.dao.DeedsImgMapper;
import com.zltel.broadcast.um.dao.DeedsTypeMapper;
import com.zltel.broadcast.um.dao.DeedsUserMapper;
import com.zltel.broadcast.um.service.DeedsUserService;
import com.zltel.broadcast.um.util.DateUtil;

@Service
public class DeedsUserServiceImpl extends BaseDaoImpl<DeedsUser> implements DeedsUserService {
	@Resource
    private DeedsUserMapper deedsUserMapper;
	@Resource
    private DeedsTypeMapper deedsTypeMapper;
	@Resource
    private DeedsImgMapper deedsImgMapper;
	@Override
    public BaseDao<DeedsUser> getInstince() {
        return this.deedsUserMapper;
    }
	
	/**
     * 查询用户事迹
     * @param conditions
     * @return
     */
	@Override
    public R queryDeedsUsers(Map<String, Object> conditions) {
    	List<Map<String, Object>> dusMap = deedsUserMapper.queryDeedsUsers(conditions);	
		if (dusMap != null && dusMap.size() > 0) {	
			return R.ok().setData(dusMap).setMsg("查询成功");
		} else {
			return R.ok().setMsg("没有查询到信息");
		}
    }
	
	private void insertDeedsImgs(String deedsImgs, DeedsUser du) {
		if (StringUtil.isNotEmpty(deedsImgs)) {
			String[] imgs = deedsImgs.split(",");
			for (String img : imgs) {
				if (StringUtil.isNotEmpty(img)) {
					DeedsImg insertDi = new DeedsImg();
					insertDi.setDeedsUserId(du.getId());
					insertDi.setPaths(img);
					deedsImgMapper.insertSelective(insertDi);
				}
			}
		}
	}
    
    /**
     * 新增
     * @param du
     * @return
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertDeedsUser(Map<String, Object> conditions) {
    	if (conditions == null) {
    		return R.error().setMsg("未填写事迹");
    	}
    	Integer userId = Integer.parseInt(String.valueOf(conditions.get("userId")));
    	String deedsTitle_jl = String.valueOf(conditions.get("deedsTitle_jl"));
    	String deedsTitle_gy = String.valueOf(conditions.get("deedsTitle_gy"));
    	String deedsTitle_pj = String.valueOf(conditions.get("deedsTitle_pj"));
    	String deedsDetail_jl = String.valueOf(conditions.get("deedsDetail_jl"));
    	String deedsDetail_ry = String.valueOf(conditions.get("deedsDetail_ry"));
    	String deedsDetail_gy = String.valueOf(conditions.get("deedsDetail_gy"));
    	String deedsDetail_pj = String.valueOf(conditions.get("deedsDetail_pj"));
		Date occurrenceTime_ry = DateUtil.toDate(DateUtil.YYYY_MM_DD, String.valueOf(conditions.get("occurrenceTime_ry")));
		String uuid = UUID.randomUUID().toString();
		
		DeedsType dt = new DeedsType();
		if (StringUtil.isNotEmpty(deedsTitle_jl) && StringUtil.isNotEmpty(deedsDetail_jl)) {
			dt.setName("个人经历");
			
			DeedsUser du = new DeedsUser();
			du.setSimilarId(uuid);
			du.setUserId(userId);
			du.setDeedsTypeId(deedsTypeMapper.queryDeedsTypes(dt).get(0).getId());
			du.setDeedsTitle(deedsTitle_jl);
			du.setDeedsDetail(deedsDetail_jl);
			du.setTime(new Date());
			deedsUserMapper.insertSelective(du);
			
			this.insertDeedsImgs(String.valueOf(conditions.get("imgs_jl")), du);
		}
		
		if (StringUtil.isNotEmpty(deedsDetail_ry) && occurrenceTime_ry != null) {
			dt.setName("获得荣誉");
			
			DeedsUser du = new DeedsUser();
			du.setSimilarId(uuid);
			du.setUserId(userId);
			du.setDeedsTypeId(deedsTypeMapper.queryDeedsTypes(dt).get(0).getId());
			du.setOccurrenceTime(occurrenceTime_ry);
			du.setDeedsDetail(deedsDetail_ry);
			du.setTime(new Date());
			deedsUserMapper.insertSelective(du);
			
			this.insertDeedsImgs(String.valueOf(conditions.get("imgs_ry")), du);
		}
		
		if (StringUtil.isNotEmpty(deedsTitle_gy) && StringUtil.isNotEmpty(deedsDetail_gy)) {
			dt.setName("个人感言");
			
			DeedsUser du = new DeedsUser();
			du.setSimilarId(uuid);
			du.setUserId(userId);
			du.setDeedsTypeId(deedsTypeMapper.queryDeedsTypes(dt).get(0).getId());
			du.setDeedsTitle(deedsTitle_gy);
			du.setDeedsDetail(deedsDetail_gy);
			du.setTime(new Date());
			deedsUserMapper.insertSelective(du);
			
			this.insertDeedsImgs(String.valueOf(conditions.get("imgs_gy")), du);
		}
		
		if (StringUtil.isNotEmpty(deedsTitle_pj) && StringUtil.isNotEmpty(deedsDetail_pj)) {
			dt.setName("他人评价");
			
			DeedsUser du = new DeedsUser();
			du.setSimilarId(uuid);
			du.setUserId(userId);
			du.setDeedsTypeId(deedsTypeMapper.queryDeedsTypes(dt).get(0).getId());
			du.setDeedsTitle(deedsTitle_pj);
			du.setDeedsDetail(deedsDetail_pj);
			du.setTime(new Date());
			deedsUserMapper.insertSelective(du);
			
			this.insertDeedsImgs(String.valueOf(conditions.get("imgs_pj")), du);
		}
    	
    	return R.ok().setMsg("添加成功");
    }
	
	
    /**
     * 补充事迹
     * @param conditions
     * @return
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R insertSupplyDeedsUser(Map<String, Object> conditions) {
    	if (conditions == null) {
    		return R.error().setMsg("未填写事迹");
    	}
    	DeedsUser du = new DeedsUser();
		du.setSimilarId(String.valueOf(conditions.get("similarId")));
		du.setUserId(Integer.parseInt(String.valueOf(conditions.get("userId"))));
		du.setDeedsTypeId(Integer.parseInt(String.valueOf(conditions.get("deedsType"))));
		du.setDeedsTitle(String.valueOf(conditions.get("deedsTitle")));
		du.setOccurrenceTime(DateUtil.toDate(DateUtil.YYYY_MM_DD, String.valueOf(conditions.get("occurrenceTime"))));
		du.setDeedsDetail(String.valueOf(conditions.get("deedsDetail")));
		du.setTime(new Date());
		deedsUserMapper.insertSelective(du);
		
		this.insertDeedsImgs(String.valueOf(conditions.get("imgs")), du);
    	return R.ok().setMsg("添加成功");
    }
    
    /**
     * 删除用户事迹
     * @param du
     * @return
     */
    @Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R deleteDeedsUser(DeedsUser du) {
    	if (du.getId() == null) {
    		return R.ok().setMsg("未选择要删除的经历");
    	}
    	deedsUserMapper.deleteByPrimaryKey(du.getId());
    	deedsImgMapper.deleteDeedsImgByDeedsUserId(du.getId());
    	
    	return R.ok().setMsg("删除成功");
    }
    
    
    /**
     * 修改用户事迹
     * @param du
     * @param paths
     * @return
     */
    public R updateDeedsUser(Map<String, Object> conditions) {
    	if (conditions == null) {
    		return R.error().setMsg("未填写事迹");
    	}
    	Integer id = Integer.parseInt(String.valueOf(conditions.get("id")));
    	String deedsTitle = String.valueOf(conditions.get("deedsTitle"));
    	Date occurrenceTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, String.valueOf(conditions.get("occurrenceTime")));
    	String deedsDetail = String.valueOf(conditions.get("deedsDetail"));
    	String imgs = String.valueOf(conditions.get("imgs"));
    	String deleteDiMgs = String.valueOf(conditions.get("deleteDiMgs"));
    	
		DeedsUser du = new DeedsUser();
		du.setId(id);
		du.setDeedsTitle(deedsTitle);
		du.setOccurrenceTime(occurrenceTime);
		du.setDeedsDetail(deedsDetail);
		deedsUserMapper.updateByPrimaryKeySelective(du);
		
		if (StringUtil.isNotEmpty(imgs)) {
			this.insertDeedsImgs(imgs, du);
		}
		if (StringUtil.isNotEmpty(deleteDiMgs)) {
			String[] deleteDiMg = deleteDiMgs.split(",");
			for (String deleteId : deleteDiMg) {
				if (StringUtil.isNotEmpty(deleteId)) {
					deedsImgMapper.deleteByPrimaryKey(Integer.parseInt(deleteId));
				}
			}
		}
		
		return R.ok().setMsg("修改成功");
    }
	
	
	
	
	
	@Override
	public int updateByPrimaryKeyWithBLOBs(DeedsUser record) {
		return 0;
	}
}
