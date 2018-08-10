package com.zltel.broadcast.example.cache.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.cache.Cache;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.util.CacheUtil;

@RestController
@RequestMapping("/cache")
public class CacheTestController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list() {
        Map<String, Object> map = new HashMap<>();
        map.put("authorization", readCache(CacheUtil.getAuthorizationCache()));
        map.put("KickOut", readCache(CacheUtil.getKickOutCache()));
        map.put("PasswordRetry", readCache(CacheUtil.getPasswordRetryCache()));
        map.put("TokenAuthentication", readCache(CacheUtil.getTokenAuthenticationCache()));
        map.put("UpAuthentication", readCache(CacheUtil.getUpAuthenticationCache()));

        return R.ok().setData(map);
    }

    private <K, V> Object readCache(Cache<K, V> cache) {
        Map<Object, Object> m = new HashMap<>();
        cache.keys().forEach(k -> m.put(k, cache.get(k)));

        return m;
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object delete() {
        String un = "develop";
        CacheUtil.clearAuthenticationCache(un);
        CacheUtil.clearAuthorizationCache(un);


        return list();
    }

    @DeleteMapping("/delete/{cache}/{item}")
    public Object deleteCache(@PathVariable("cache") String cache, @PathVariable("item") String item) {
        if (StringUtils.isAllEmpty(cache, item)) throw RRException.makeThrow("操作缓存信息不能为空");
        if ("UpAuthentication".equals(cache)) {
            CacheUtil.clearAuthenticationCache(item);
        } else if ("authorization".equals(cache)) {
            CacheUtil.clearAuthorizationCache(item);
        } else if ("PasswordRetry".equals(cache)) {
            CacheUtil.clearRetryCache(item);
        } else if ("KickOut".equals(cache)) {
            CacheUtil.clearLogCountCache(item);
        }

        return R.ok();
    }

}
