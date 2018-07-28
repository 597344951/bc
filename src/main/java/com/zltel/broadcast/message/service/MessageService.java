package com.zltel.broadcast.message.service;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.message.bean.Message;
import com.zltel.broadcast.um.bean.SysUser;

public interface MessageService {
    public void addMessage(Message message);
    public void addMessage(int type, String title, String content, int userId, int sourceId, String url);

    public PageInfo<Message> queryPending(SysUser user);
    public PageInfo<Message> queryNotice(SysUser user, int pageNum, int pageSize);

    public void handleMessage(SysUser user, Integer sourceId);


}
