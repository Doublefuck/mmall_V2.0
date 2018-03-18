package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2018/3/18 0018.
 */
@Slf4j
public class RedisPoolUtil {

    /**
     * 向redis中存值
     * @param key
     * @param value
     * @return
     */
    public static String setKey(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{},value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 获取redis中指定的值
     * @param key
     * @return
     */
    public static String getKey(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 向redis中存值并指定key的有效期，单位是秒
     * @param key
     * @param value
     * @param exTime
     * @return
     */
    public static String setKeyEx(String key, String value, Integer exTime) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key:{},value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 重置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long reExpire(String key, Integer exTime) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 删除指定key的值
     * @param key
     * @return
     */
    public static Long delKey(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
//        Jedis jedis = RedisPool.getJedis();
//        RedisPoolUtil.setKey("a", "a");
//        String a = RedisPoolUtil.getKey("a");
//
//        RedisPoolUtil.setKeyEx("b", "b", 120);
//        RedisPoolUtil.reExpire("b", 240);
//        RedisPoolUtil.delKey("b");
    }

}
