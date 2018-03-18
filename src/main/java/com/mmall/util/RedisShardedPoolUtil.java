package com.mmall.util;

import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

/**
 * Created by Administrator on 2018/3/18 0018.
 */
@Slf4j
public class RedisShardedPoolUtil {

    /**
     * 向redis中存值
     * @param key
     * @param value
     * @return
     */
    public static String setKey(String key, String value) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{},value:{} error", key, value, e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    /**
     * 获取redis中指定的值
     * @param key
     * @return
     */
    public static String getKey(String key) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error", key, e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
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
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key:{},value:{} error", key, value, e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    /**
     * 重置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long reExpire(String key, Integer exTime) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key:{} error", key, e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    /**
     * 删除指定key的值
     * @param key
     * @return
     */
    public static Long delKey(String key) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} error", key, e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        ShardedJedis jedis = RedisShardedPool.getJedis();
        RedisShardedPoolUtil.setKey("a", "a");
        String a = RedisShardedPoolUtil.getKey("a");

        RedisShardedPoolUtil.setKeyEx("b", "b", 120);
        RedisShardedPoolUtil.reExpire("b", 240);
        RedisShardedPoolUtil.delKey("b");
    }

}
