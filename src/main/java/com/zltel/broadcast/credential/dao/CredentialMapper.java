package com.zltel.broadcast.credential.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CredentialMapper {
    public Map<String, Object> selectPartyDueRecord(Integer id);
}
