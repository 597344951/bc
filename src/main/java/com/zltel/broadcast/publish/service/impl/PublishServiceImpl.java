package com.zltel.broadcast.publish.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.zltel.broadcast.common.dao.SimpleDao;
import com.zltel.broadcast.incision.sola.service.SolaProgramService;
import com.zltel.broadcast.incision.sola.service.impl.SolaProgramServiceImpl;
import com.zltel.broadcast.message.bean.Message;
import com.zltel.broadcast.message.service.MessageService;
import com.zltel.broadcast.publish.Constant;
import com.zltel.broadcast.publish.dao.PublishDao;
import com.zltel.broadcast.publish.service.PublishService;
import com.zltel.broadcast.um.bean.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * PublishServiceImpl class
 *
 * @author ToussmoreEditCommit
 * @date 2018/5/7
 */
@Service
public class PublishServiceImpl implements PublishService {
    private static final Log log = LogFactory.getLog(PublishServiceImpl.class);

    @Value("${zltel.host}")
    private String host;

    @Autowired
    private SimpleDao simpleDao;
    @Autowired
    private PublishDao publishDao;
    @Autowired
    private SolaProgramService solaProgramService;
    @Autowired
    private MessageService messageService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public Map<String, Object> create(SysUser user, Map<String, Object> content) {
        Date add_date = new Date();
        int contentTypeId = (int) content.get("type");
        // 添加内容
        Map<String, Object> detail = new HashMap<String, Object>();
        detail.put("title", content.get("title"));
        detail.put("content", content.get("templateText"));
        detail.put("content_type_id", contentTypeId);
        detail.put("user_id", user.getUserId());
        /*detail.put("start_date", content.get("startDate"));
        detail.put("end_date", content.get("endDate"));
        detail.put("period", content.get("period"));*/
        detail.put("relate_content_id", content.get("relateContent"));
        detail.put("add_date", add_date);
        detail.put("update_date", add_date);
        // 获取发布流程
        List<Map<String, Object>> process = getProcess((int) detail.get("content_type_id"));
        detail.put("process_item_id", process.get(0).get("process_item_id"));
        simpleDao.add("publish_content", detail);
        if (Constant.CONTENT_TYPE_ACTIVITY == contentTypeId) {
            // 添加活动附加信息
            Map<String, Object> addition = (Map<String, Object>) content.get("addition");
            Map<String, Object> activityAddition = new HashMap<String, Object>();
            activityAddition.put("content_id", detail.get("id"));
            activityAddition.put("activity_date", addition.get("date"));
            activityAddition.put("description", addition.get("desc"));
            activityAddition.put("ordain_participant_number", addition.get("ordainParticipantNumber"));
            activityAddition.put("participant_type", addition.get("participantType"));
            activityAddition.put("add_date", add_date);
            activityAddition.put("update_date", add_date);
            simpleDao.add("publish_activity_addition", activityAddition);
        }

        // 添加审核人员
        List<Map<String, Object>> examineUsers = (List<Map<String, Object>>) content.get("exUsers");
        Map<String, Object> examineUser;
        for (int i = 0; i < examineUsers.size(); i++) {
            examineUser = new HashMap<String, Object>();
            examineUser.put("user_id", examineUsers.get(i).get("id"));
            examineUser.put("content_id", detail.get("id"));
            examineUser.put("sort", i + 1);
            examineUser.put("state", Constant.VERIFY_NOT_START);
            examineUser.put("add_date", add_date);
            examineUser.put("update_date", add_date);
            simpleDao.add("publish_examine_user", examineUser);
        }
        // 添加素材
        List<Map<String, Object>> materials = (List<Map<String, Object>>) content.get("material");
        Map<String, Object> material;

        for (Map<String, Object> m : materials) {
            boolean isFile = (boolean) m.get("isFile");
            if(isFile) {
                material = new HashMap<String, Object>();
                material.put("type", m.get("type"));
                material.put("name", m.get("name"));
                material.put("content", m.get("content"));
                material.put("url", m.get("url"));
                material.put("description", m.get("name"));
                material.put("user_id", user.getUserId());
                material.put("org_id", user.getOrgId());
                material.put("upload_reason", Constant.MATERIAL_UPLOAD_REASON_MAKE);
                material.put("relate_content_id", detail.get("id"));
                material.put("add_date", add_date);
                material.put("update_date", add_date);
                simpleDao.add("material", material);
                m.put("id", material.get("id"));
            }
        }

        // 发布终端
        List<Map<String, Object>> terminals = (List<Map<String, Object>>) content.get("terminals");
        Map<String, Object> terminal;
        for (Map<String, Object> t : terminals) {
            terminal = new HashMap<String, Object>();
            terminal.put("terminal_id", t.get("id"));
            terminal.put("group_id", t.get("groupId"));
            terminal.put("content_id", detail.get("id"));
            terminal.put("add_date", add_date);
            terminal.put("update_date", add_date);
            simpleDao.add("publish_terminal", terminal);
        }
        // 添加进度（内容初稿发布完成）
        Map<String, Object> processState = new HashMap<String, Object>();
        processState.put("content_id", detail.get("id"));
        processState.put("user_id", user.getUserId());
        processState.put("process_item_id", process.get(0).get("process_item_id"));
        processState.put("add_date", add_date);
        processState.put("update_date", add_date);
        processState.put("msg", Constant.MSG_CONTENT_FIRST_EDIT + user.getUsername());
        simpleDao.add("publish_process_state", processState);

        // 添加节目到编辑器
        int editId = addToEditor(content);
        // 更新预览
        publishDao.updateSnapshot(String.valueOf(editId), ((Long) detail.get("id")).intValue());
        doNext(((Long) detail.get("id")).intValue());

        return detail;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void doNext(int contentId) {
        // 获取发布内容
        Map<String, Object> content = getContent(contentId);
        int contentTypeId = (int) content.get("content_type_id");
        int curProcess = (int) content.get("process_item_id");
        List<Map<String, Object>> process = getProcess(contentTypeId);
        int nextProcess = getNextProcess(process, curProcess);
        if (Constant.MORE_EDIT == curProcess) {
            List<Map<String, Object>> moreEditUser = getMoreEditUser(contentId);
            if (moreEditUser.size() > 0) {
                // 已开始编辑
                int editState = getEditState(moreEditUser);
                Map<Integer, List<Map<String, Object>>> processState = getProcessState(contentId);
                // 编辑提交状态
                if (Constant.MORE_EDIT_COMMIT == editState) {
                    // 进入下一步
                    changeProcess(contentId, nextProcess);
                }
            }
        } else if (Constant.VERIFY == curProcess) {
            List<Map<String, Object>> examineUser = getExamineUser(contentId);
            int verifyState;
            boolean verifyCompleted = true;
            for (Map<String, Object> exu : examineUser) {
                verifyState = (int) exu.get("state");
                if (Constant.VERIFY_NOT_START == verifyState || Constant.VERIFY_ONCE == verifyState) {
                    // 审核还在继续
                    verifyCompleted = false;
                    // 顺位审核
                    // TODO: 2018/5/13 添加审核待办
                    messageService.addMessage(Message.TYPE_VERIFY_PENDING, content.get("title") + ": 待审核", null, (Integer) exu.get("user_id"), contentId);
                    break;
                }
            }
            if (verifyCompleted) {
                // 审核完成下一步
                changeProcess(contentId, nextProcess);
            }
        } else {
            // 下一个步骤
            changeProcess(contentId, nextProcess);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void changeProcess(int contentId, int processItemId) {
        Map<String, Object> content = getContent(contentId);
        Date update_date = new Date();
        Map<String, Object> queryParam = null;
        Map<Integer, List<Map<String, Object>>> processState = getProcessState(contentId);
        if (Constant.MORE_EDIT == processItemId) {
            if (processState.get(Constant.VERIFY) == null) {
                // 通知所有编辑人员
                // TODO: 2018/7/2 添加待办
                messageService.addMessage(Message.TYPE_HANDLE_PENDING, content.get("title") + ": 待编辑", null, Message.USER_ALL, contentId);
            } else {
                // 回退清除提交标记
                List<Map<String, Object>> meUsers = getMoreEditUser(contentId);
                queryParam = new HashMap<String, Object>();
                for (Map<String, Object> meUser : meUsers) {
                    meUser.put("state", Constant.MORE_EDIT_REEDITING);
                    meUser.put("update_date", update_date);
                    queryParam.put("more_edit_user_id", meUser.get("more_edit_user_id"));
                    simpleDao.update("publish_more_edit_user", meUser, queryParam);
                    // TODO: 2018/7/2 添加待办
                    messageService.addMessage(Message.TYPE_HANDLE_PENDING, content.get("title") + ": 待编辑", null, (Integer) meUser.get("user_id"), contentId);
                }
            }

        } else if (Constant.VERIFY == processItemId) {
            // 重置状态
            List<Map<String, Object>> examineUser = getExamineUser(contentId);
            queryParam = new HashMap<String, Object>();
            boolean isFirst = true;
            for(int i=0; i<examineUser.size(); i++) {
                Map<String, Object> exu = examineUser.get(i);
                exu.put("state", processState.get(Constant.VERIFY) == null ? Constant.VERIFY_NOT_START : Constant.VERIFY_ONCE);
                //一天时间审核
                exu.put("deadline", new Date(System.currentTimeMillis() + (i+1) * 24 * 60 * 60 * 1000));
                exu.put("update_date", update_date);
                queryParam.put("examine_user_id", exu.get("examine_user_id"));
                simpleDao.update("publish_examine_user", exu, queryParam);
                // 通知顺序一
                messageService.addMessage(Message.TYPE_VERIFY_PENDING, content.get("title") + ": 待审核", null, (Integer) content.get("user_id"), contentId);
            }

        } else if (Constant.PUBLISHABLE == processItemId) {
            // 通知发起人
            // TODO: 2018/7/2 添加待办
            messageService.addMessage(Message.TYPE_HANDLE_PENDING, content.get("title") + ": 待发布", null, (Integer) content.get("user_id"), contentId);
        }
        // 更改状态
        content = new HashMap<>();
        content.put("process_item_id", processItemId);
        content.put("update_date", update_date);
        queryParam = new HashMap<String, Object>();
        queryParam.put("content_id", contentId);
        simpleDao.update("publish_content", content, queryParam);
    }

    /**
     * 像编辑器添加新编辑内容
     * 
     * @param content
     * @return
     */
    private int addToEditor(Map<String, Object> content) {
        Map<String, Object> program = new HashMap<String, Object>();
        String programTemplateId = (String) content.get("programTemplateId");
        String programTemplateCategoryId = (String) content.get("programTemplateCategoryId");
        int addType;
        if(StringUtils.isNotEmpty(programTemplateId) && StringUtils.isNotEmpty(programTemplateCategoryId)) {
            //使用模板添加节目
            program.put("templetId", programTemplateId);
            program.put("categoryId", programTemplateCategoryId);
            addType = SolaProgramServiceImpl.ADD_PROGRAM_WITH_TEMPLATE;
        } else {
            //不适用模板添加节目
            String modeltype = (String) content.get("screenType");
            String playtime = (String) content.get("playLength");
            String resolution = (String) content.get("resolution");
            int resolutionw = Integer.parseInt(resolution.split("x")[0]);
            int resolutionh = Integer.parseInt(resolution.split("x")[1]);
            program.put("title", content.get("title"));
            program.put("categoryId", 1);
            program.put("modeltype", Integer.parseInt(modeltype));
            program.put("resolutionw", resolutionw);
            program.put("resolutionh", resolutionh);
            program.put("playtime", Integer.parseInt(playtime));
            program.put("des", content.get("title"));
            addType = SolaProgramServiceImpl.ADD_PROGRAM_NO_TEMPLATE;
        }
        // 素材
        List<Map<String, Object>> materials = (List<Map<String, Object>>) content.get("material");
        List<Map<String, Object>> resources = new ArrayList<>();
        for (Map<String, Object> material : materials) {
            boolean isFile = (boolean) material.get("isFile");
            Map<String, Object> res = new HashMap<String, Object>();
            String type = (String) material.get("type");
            String name = (String) material.get("name");
            String url;
            if(isFile) {
                url = material.get("id") + name.substring(name.lastIndexOf("."), name.length());
            } else {
                url = "";
            }

            if (Constant.MATERIAL_TYPE_PICTURE.equals(type)) {
                res.put("Type", 2);
                res.put("Url", host + "/material/image/" + url);
                res.put("Content", "");
            } else if (Constant.MATERIAL_TYPE_AUDIO.equals(type)) {
                res.put("Type", 4);
                res.put("Url", host + "/material/download/" + material.get("id"));
                res.put("Content", "");
            } else if(Constant.MATERIAL_TYPE_VIDEO.equals(type)) {
                res.put("Type", 3);
                res.put("Url", host + "/material/download/" + material.get("id"));
                res.put("Content", "");
            } else if(Constant.MATERIAL_TYPE_TEXT.equals(type)) {
                res.put("Type", 1);
                res.put("Content", material.get("content"));
                res.put("Url", "");
            } else {
                continue;
            }
            res.put("Name", name);
            resources.add(res);
        }
        program.put("resourcesJson", JSON.toJSONString(resources));
        return solaProgramService.addProgram(program, addType);
    }

    /**
     * 在编辑状态
     * 
     * @param moreEditUser 编辑用户
     * @return 状态
     */
    private int getEditState(List<Map<String, Object>> moreEditUser) {
        int state = Constant.MORE_EDIT_REEDITING;
        int cur;
        for (Map<String, Object> u : moreEditUser) {
            cur = (int) u.get("state");
            if (cur <= state) {
                state = cur;
            }
        }
        return state;
    }

    /**
     * 下一个流程
     * 
     * @param process    全流程
     * @param curProcess 当前流程
     * @return 下一个
     */
    private int getNextProcess(List<Map<String, Object>> process, int curProcess) {
        int processItem;
        for (int i = 0; i < process.size(); i++) {
            processItem = (int) process.get(i).get("process_item_id");
            if (curProcess == processItem && (i + 1) < process.size()) {
                return (int) process.get(i + 1).get("process_item_id");
            }
        }
        return -1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void verify(SysUser user, int operate, String opinion, int contentId, int contentTypeId) {
        Date add_date = new Date();
        Map<String, Object> examineUser = new HashMap<String, Object>();
        boolean hasNext = true;
        if (Constant.VERIFY_OPERATE_APPROVED == operate) {
            // 审核通过
            // 添加状态
            addProcessState(contentId, user.getUserId(), Constant.VERIFY, Constant.MSG_CONTENT_VERIFY_PASS + user.getUsername(), null);
            // 更新审核人状态
            examineUser.put("state", Constant.VERIFY_PASS);
        } else if(Constant.VERIFY_OPERATE_NOT_APPROVED == operate) {
            // 审核未通过
            // 添加状态
            addProcessState(contentId, user.getUserId(), Constant.VERIFY,Constant.MSG_CONTENT_VERIFY_NOT_PASS + user.getUsername(), opinion);
            // 更新审核人状态
            examineUser.put("state", Constant.VERIFY_NOT_PASS);
            // 返回到上一个状态
            List<Map<String, Object>> process = getProcess(contentTypeId);
            int processItemId;
            for (int i = 0; i < process.size(); i++) {
                processItemId = (int) process.get(i).get("process_item_id");
                if (Constant.VERIFY == processItemId && i >= 1) {
                    processItemId = (int) process.get(i - 1).get("process_item_id");
                    if (Constant.MORE_EDIT == processItemId) {
                        // 回退到在编辑
                        changeProcess(contentId, Constant.MORE_EDIT);
                    } else {
                        // 废弃
                        changeProcess(contentId, Constant.DISCARD);
                        hasNext = false;
                    }

                }
            }
        } else {
            //放弃审核
            // 添加状态
            addProcessState(contentId, user.getUserId(), Constant.VERIFY, Constant.MSG_CONTENT_VERIFY_ABSTAIN + user.getUsername(), null);
            // 更新审核人状态
            examineUser.put("state", Constant.VERIFY_ABSTAIN);
            List<Map<String, Object>> examineUsers = getExamineUser(contentId);
            if(examineUsers.get(examineUsers.size()-1).get("user_id").equals(user.getUserId())) {
                // 废弃
                changeProcess(contentId, Constant.DISCARD);
                hasNext = false;
            }

        }
        //消息处理
        messageService.handleMessage(user, contentId);

        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("content_id", contentId);
        queryParam.put("user_id", user.getUserId());
        examineUser.put("update_date", add_date);
        simpleDao.update("publish_examine_user", examineUser, queryParam);
        // 下一步
        if(hasNext) {
            doNext(contentId);
        }

    }

    @Override
    public List<Map<String, Object>> getProcess(int contentTypeId) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("content_type_id", contentTypeId);
        List<Map<String, Object>> process = simpleDao.query("publish_process", queryParam);
        process.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                int s1 = (int) o1.get("process_sort");
                int s2 = (int) o2.get("process_sort");
                return s1 > s2 ? 1 : -1;
            }
        });
        return process;
    }

    @Override
    public List<Map<String, Object>> getContentType() {
        return simpleDao.query("publish_content_type", null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void moreEditCommit(SysUser user, int contentId, String snapshot) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("content_id", contentId);
        Date update_date = new Date();

        // 更新编辑状态
        Map<String, Object> moreEditUser = new HashMap<String, Object>();
        moreEditUser.put("state", Constant.MORE_EDIT_COMMIT);
        moreEditUser.put("update_date", update_date);
        queryParam.put("user_id", user.getUserId());
        simpleDao.update("publish_more_edit_user", moreEditUser, queryParam);

        // 添加状态记录
        addProcessState(contentId, user.getUserId(), Constant.MORE_EDIT,
                Constant.MSG_CONTENT_MORE_EDIT_COMMIT + user.getUsername(), null);
        //消息处理
        messageService.handleMessage(user, contentId);
        // 下一步
        doNext(contentId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public int moreEditStart(SysUser user, int contentId) {
        // 添加在编辑人员
        Date add_date = new Date();
        Map<String, Object> moreEditUser = new HashMap<String, Object>();
        moreEditUser.put("user_id", user.getUserId());
        moreEditUser.put("content_id", contentId);
        moreEditUser.put("state", Constant.MORE_EDIT_EDITING);
        moreEditUser.put("add_date", add_date);
        moreEditUser.put("update_date", add_date);
        simpleDao.add("publish_more_edit_user", moreEditUser);

        addProcessState(contentId, user.getUserId(), Constant.MORE_EDIT,
                Constant.MSG_CONTENT_MORE_EDIT_START + user.getUsername(), null);

        Map<String, Object> content = publishDao.get(contentId);
        // 下一步
        doNext(contentId);

        return Integer.parseInt((String) content.get("snapshot"));
    }

    @Override
    public List<Map<String, Object>> getMoreEditUser(int contentId) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("content_id", contentId);
        return simpleDao.query("publish_more_edit_user", queryParam);
    }

    @Override
    public Map<String, Object> getContent(int contentId) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("content_id", contentId);
        return simpleDao.get("publish_content", queryParam);
    }

    @Override
    public Map<Integer, List<Map<String, Object>>> getProcessState(int contentId) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("content_id", contentId);
        List<Map<String, Object>> processState = simpleDao.query("publish_process_state", queryParam);

        Map<Integer, List<Map<String, Object>>> state = new HashMap<Integer, List<Map<String, Object>>>();
        List<Map<String, Object>> list = null;
        Integer processItemId = null;

        for (Map<String, Object> s : processState) {
            processItemId = (Integer) s.get("process_item_id");
            list = state.get(processItemId);
            if (list == null) {
                list = new ArrayList<Map<String, Object>>();
                state.put(processItemId, list);
            }
            list.add(s);
        }
        // 排序
        for (Integer key : state.keySet()) {
            list = state.get(key);
            list.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Date d1 = (Date) o1.get("add_date");
                    Date d2 = (Date) o2.get("add_date");
                    return d1.getTime() > d2.getTime() ? 1 : -1;
                }
            });
        }
        return state;
    }

    @Override
    public List<Map<String, Object>> getExamineUser(int contentId) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("content_id", contentId);
        List<Map<String, Object>> examineUser = simpleDao.query("publish_examine_user", queryParam);
        examineUser.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                int s1 = (int) o1.get("sort");
                int s2 = (int) o2.get("sort");
                return s1 > s2 ? 1 : -1;
            }
        });
        return examineUser;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void addProcessState(int contentId, int userId, int processItemId, String msg, String remark) {
        Date add_date = new Date();
        Map<String, Object> processState = new HashMap<String, Object>();
        processState.put("content_id", contentId);
        processState.put("user_id", userId);
        processState.put("process_item_id", processItemId);
        processState.put("add_date", add_date);
        processState.put("update_date", add_date);
        processState.put("msg", msg);
        processState.put("remark", remark);
        simpleDao.add("publish_process_state", processState);
    }

    @Override
    public List<Map<String, Object>> queryProcessContent(SysUser user) {
        List<Integer> processes = new ArrayList<Integer>();
        processes.add(Constant.FIRST_EDIT);
        processes.add(Constant.MORE_EDIT);
        processes.add(Constant.VERIFY);
        processes.add(Constant.PUBLISHABLE);
        List<Map<String, Object>> content = publishDao.queryContentByProcess(processes, 1, Integer.MAX_VALUE);
        // 获取运行用户可用当前操作: 开始在编辑，在编辑提交，审核（通过/不通过），查看节目，删除
        int processItem, contentId;
        List<Map<String, Object>> exUsers = null;
        List<Map<String, Object>> meUsers = null;
        Map<String, Object> operate = null;
        int userId;
        for (Map<String, Object> c : content) {
            operate = new HashMap<String, Object>();
            c.put("operate", operate);
            // 判断用户职责
            processItem = (int) c.get("process_item_id");
            contentId = (int) c.get("content_id");
            userId = (int) c.get("user_id");
            if (Constant.FIRST_EDIT == processItem) {
                // 发布初期
                if (user.getUserId() == userId) {
                    // 发起人
                    operate.put("delete", true);
                }
            } else if (Constant.MORE_EDIT == processItem) {
                // 在编辑
                meUsers = getMoreEditUser(contentId);
                if (meUsers == null || meUsers.size() <= 0) {
                    // 还没有人编辑
                    if (user.getUserId() == userId) {
                        // 发起人可以删除
                        operate.put("delete", true);
                    }
                    // 判断是否为有编辑人权限
                    // 如果事编辑人，可以开始编辑
                    operate.put("more_edit_start", true);
                } else {
                    // 判断是否为编辑人
                    int meUserId;
                    for (Map<String, Object> meUser : meUsers) {
                        meUserId = (int) meUser.get("user_id");
                        if (meUserId == user.getUserId()) {
                            // 目前编辑人
                            operate.put("more_edit", true);
                            operate.put("more_edit_commit", true);
                        }
                    }
                }
            } else if (Constant.VERIFY == processItem) {
                // 审核阶段
                exUsers = getExamineUser(contentId);
                // 判断是否为审核人
                int exUserId, state;
                Map<String, Object> exUser = null;
                for (int i = 0; i < exUsers.size(); i++) {
                    exUser = exUsers.get(i);
                    exUserId = (int) exUser.get("user_id");
                    state = (int) exUser.get("state");
                    if(Constant.VERIFY_NOT_START == state || Constant.VERIFY_ONCE == state) {
                        if (exUserId == user.getUserId()) {
                            if (i == 0) {
                                // 第一个审核人
                                operate.put("verify", true);
                            } else {
                                exUser = exUsers.get(i - 1);
                                state = (int) exUser.get("state");
                                if (Constant.VERIFY_PASS == state) {
                                    // 前一位审核人已经通过
                                    operate.put("verify", true);
                                }
                            }
                            break;
                        }
                    }
                }
            } else if (Constant.PUBLISHABLE == processItem) {
                // 可发布状态
                if (user.getUserId() == userId) {
                    // 发起人可发布
                    operate.put("publish", true);
                }
            }
            // 公用操作
            // 查看模板内容、查看节目预览、查看进度
            operate.put("template", true);
            operate.put("snapshot", true);
            operate.put("process", true);

        }
        return content;
    }

    @Override
    public List<Map<String, Object>> queryPublishingContent(int pageNum, int pageSize) {
        List<Integer> processes = new ArrayList<Integer>();
        processes.add(Constant.PUBLISHING);
        return publishDao.queryContentByProcess(processes, pageNum, pageSize);
    }

    @Override
    public List<Map<String, Object>> queryPublishedContent(int pageNum, int pageSize) {
        List<Integer> processes = new ArrayList<Integer>();
        processes.add(Constant.PUBLISHED);
        return publishDao.queryContentByProcess(processes, pageNum, pageSize);
    }

    @Override
    public List<Map<String, Object>> queryDiscardContent(int pageNum, int pageSize) {
        List<Integer> processes = new ArrayList<Integer>();
        processes.add(Constant.PUBLISHING);
        return publishDao.queryContentByProcess(processes, pageNum, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void discard(SysUser user, int contentId) {
        changeProcess(contentId, Constant.DISCARD);
        // 添加状态
        addProcessState(contentId, user.getUserId(), Constant.DISCARD,
                Constant.MSG_CONTENT_DISCARD + user.getUsername(), null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void publish(SysUser user, int contentId, Map<String, Object> period) {
        // 调用发布接口
        Map<String, Object> content = publishDao.get(contentId);
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("content_id", contentId);
        List<Map<String, Object>> terminals = simpleDao.query("publish_terminal", queryParam);
        content.put("terminals", terminals);
        content.put("start_date", period.get("startDate"));
        content.put("end_date", period.get("endDate"));
        content.put("period", period.get("period"));
        content.put("weeks", period.get("weeks"));
        solaProgramService.publish(content);
        //更新播放时间内容
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("start_date", period.get("startDate"));
        updateData.put("end_date", period.get("endDate"));
        updateData.put("period", period.get("period"));
        updateData.put("weeks", period.get("weeks"));
        updateData.put("update_date", new Date());
        simpleDao.update("publish_content", updateData, queryParam);
        changeProcess(contentId, Constant.PUBLISHING);
        addProcessState(contentId, user.getUserId(), Constant.PUBLISHING, Constant.MSG_CONTENT_PUBLISHING + user.getUsername(), null);

        //消息处理
        messageService.handleMessage(user, contentId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void offline(SysUser user, int contentId) {
        // 取消调用
        Map<String, Object> content = publishDao.get(contentId);
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("content_id", contentId);
        List<Map<String, Object>> terminals = simpleDao.query("publish_terminal", queryParam);
        String tids = "";
        for (Map<String, Object> terminal : terminals) {
            tids += "," + terminal.get("terminal_id");
        }
        tids = tids.replace(",", "");
        solaProgramService.cancelProgram("" + content.get("snapshot"), "2");
        changeProcess(contentId, Constant.PUBLISHED);
        addProcessState(contentId, user.getUserId(), Constant.PUBLISHED,
                Constant.MSG_CONTENT_OFFLINE + user.getUsername(), null);
    }

    @Override
    public Map<String, Object> getShowProcessState(int contentTypeId, int contentId) {
        Map<String, Object> state = new HashMap<String, Object>();
        state.put("steps", publishDao.queryProcess(contentTypeId));
        state.put("log", publishDao.queryProcessState(contentId));
        return state;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public int offline() {
        return publishDao.offline();
    }

    @Override
    public List<Map<String, Object>> queryPublishTerminal(int contentId) {
        return publishDao.queryPublishTerminal(contentId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void verifyTimeout() {
        List<Map<String, Object>> users = publishDao.queryVerifyingUser();
        Map<Integer, Object> contentVerifyingUsers = new HashMap<>();
        int contentId;
        List<Map<String, Object>> list;
        //分组
        for(Map<String, Object> user : users) {
            contentId = (int) user.get("content_id");
            list = (List<Map<String, Object>>) contentVerifyingUsers.get(contentId);
            if(list == null) {
                list = new ArrayList<>();
                contentVerifyingUsers.put(contentId, list);
            }
            list.add(user);
        }
        //检查超时
        Map<String, Object> user, state, queryParam;
        Date deadline;
        for(Integer key : contentVerifyingUsers.keySet()) {
            list = (List<Map<String, Object>>) contentVerifyingUsers.get(key);
            for(int i=0; i<list.size(); i++) {
                user = list.get(i);
                deadline = (Date) user.get("deadline");
                if(deadline == null) {
                    continue;
                }
                if(deadline.getTime() < System.currentTimeMillis()) {
                    //超时
                    state = new HashMap<>();
                    state.put("state", Constant.VERIFY_ABSTAIN);
                    queryParam = new HashMap<>();
                    queryParam.put("examine_user_id", user.get("examine_user_id"));
                    queryParam.put("update_date", user.get("update_date"));
                    simpleDao.update("publish_examine_user", state, queryParam);
                    addProcessState(key, (Integer) user.get("user_id"), Constant.VERIFY, Constant.MSG_CONTENT_VERIFY_ABSTAIN + user.get("username"), null);
                    log.info("审核超时, 自动放弃审核.");
                    if(list.size() <= 1) {
                        //没有后续审核人, 内容废止
                        Map<String, Object> content = publishDao.get(key);
                        state = new HashMap<>();
                        state.put("process_item_id", Constant.DISCARD);
                        queryParam = new HashMap<>();
                        queryParam.put("content_id", content.get("content_id"));
                        queryParam.put("update_date", content.get("update_date"));
                        simpleDao.update("publish_content", state, queryParam);
                        addProcessState(key, (Integer) user.get("user_id"), Constant.VERIFY, Constant.MSG_CONTENT_DISCARD + user.get("username"), null);
                        log.info("审核超时且无其他审核人, 内容废弃处理.");
                    }


                }
            }
        }
    }
}
