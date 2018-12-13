package com.zltel.broadcast.credential.service.impl;

import com.zltel.broadcast.credential.dao.CredentialMapper;
import com.zltel.broadcast.credential.service.CredentialService;
import com.zltel.broadcast.message.bean.Message;
import com.zltel.broadcast.message.service.MessageService;
import com.zltel.broadcast.um.bean.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CredentialServiceImpl implements CredentialService {
    @Autowired
    private CredentialMapper credentialMapper;
    @Autowired
    private MessageService messageService;

    @Override
    public Map<String, Object> wrapPartyDueCredentialData(Integer id) {
        return credentialMapper.selectPartyDueRecord(id);
    }

    @Override
    public void sendPartyDueCredential(Integer id, SysUser user) {
        messageService.addMessage(Message.TYPE_NOTICE, "党费缴纳收据", "党费缴纳收据", user.getUserId(), id, "/credential/partyDue/" + id);
    }
}
