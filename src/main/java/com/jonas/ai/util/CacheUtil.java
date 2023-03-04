package com.jonas.ai.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jonas.ai.bean.req.ChatReq;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Guava缓存工具类
 *
 * @author shenjy
 * @version 1.0
 * @date 2023-03-04
 */
public class CacheUtil {

    private static final Cache<String, List<ChatReq.ChatMessage>> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(1, TimeUnit.DAYS)
            .build();

    public static void put(String key, List<ChatReq.ChatMessage> val) {
        cache.put(key, val);
    }

    public static List<ChatReq.ChatMessage> get(String key) {
        List<ChatReq.ChatMessage> messages = cache.getIfPresent(key);
        return CollectionUtils.isEmpty(messages) ? new ArrayList<>() : messages;
    }
}
