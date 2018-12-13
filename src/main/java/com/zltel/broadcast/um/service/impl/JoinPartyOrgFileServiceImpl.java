package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.JoinPartyOrgFile;
import com.zltel.broadcast.um.dao.JoinPartyOrgFileMapper;
import com.zltel.broadcast.um.service.JoinPartyOrgFileService;

@Service
public class JoinPartyOrgFileServiceImpl extends BaseDaoImpl<JoinPartyOrgFile> implements JoinPartyOrgFileService {
	@Resource
    private JoinPartyOrgFileMapper joinPartyOrgFileMapper;
	@Override
    public BaseDao<JoinPartyOrgFile> getInstince() {
        return this.joinPartyOrgFileMapper;
    }
	
	/**
     * 补充材料
     * @param condition
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R supplementFiles(String condition) {
		Map<String, Object> data = JSON.parseObject(condition);
		List<Map<String, String>> uploadFileInfo = (List<Map<String, String>>) data.get("uploadFiles");
		Integer stepId = Integer.parseInt(String.valueOf(data.get("stepId")));
		for (Map<String, String> map : uploadFileInfo) {
			JoinPartyOrgFile jpof = new JoinPartyOrgFile();
			jpof.setStepId(stepId);
			jpof.setFileTitle(map.get("fileName"));
			jpof.setFileDescribes(map.get("fileName"));
			jpof.setFilePath(map.get("fileUrl"));
			jpof.setFileType(map.get("suffix"));
			jpof.setTime(new Date());
			joinPartyOrgFileMapper.insertSelective(jpof);
		}
		return R.ok().setMsg("补充成功");
    }
}
