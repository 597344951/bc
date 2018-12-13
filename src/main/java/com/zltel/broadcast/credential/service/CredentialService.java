package com.zltel.broadcast.credential.service;

import com.zltel.broadcast.um.bean.SysUser;

import java.util.Map;

public interface CredentialService {
    public Map<String, Object> wrapPartyDueCredentialData(Integer id);
    public void sendPartyDueCredential(Integer id, SysUser user);
}
