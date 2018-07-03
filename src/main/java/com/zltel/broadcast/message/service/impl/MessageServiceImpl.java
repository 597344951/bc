package com.zltel.broadcast.message.service.impl;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.message.bean.Message;
import com.zltel.broadcast.message.dao.MessageMapper;
import com.zltel.broadcast.message.service.MessageService;
import com.zltel.broadcast.um.bean.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void addMessage(Message message) {
        messageMapper.insertSelective(message);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void addMessage(int type, String title, String content, int userId, int sourceId) {
        addMessage(new Message(type, title, content, userId, sourceId));
    }

    @Override
    public PageInfo<Message> queryPending(SysUser user) {
        return new PageInfo<>(messageMapper.queryPendingByUser(user.getUserId(), 1, Integer.MAX_VALUE));
    }

    @Override
    public PageInfo<Message> queryNotice(SysUser user, int pageNum, int pageSize) {
        return new PageInfo<>(messageMapper.queryNoticeByUser(user.getUserId(), pageNum, pageSize));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void handleMessage(SysUser user, Integer sourceId) {
        Message message = new Message();
        message.setState(Message.STATE_PROCESSED);
        message.setUserId(user.getUserId());
        message.setSourceId(sourceId);
        messageMapper.updateStateBySource(message);
    }
}
