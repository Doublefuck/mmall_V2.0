package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis分片连接池
 * Created by Administrator on 2018/3/18 0018.
 */
public class RedisShardedPool {

    // sharded jedis连接池
    private static ShardedJedisPool pool;

    // 最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));

    // 最大空闲jedis实例
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));

    // 最小空闲jedis实例
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));

    // 检测在从jedis连接池中获取jedis实例时是否正确获取jedis实例
    private static boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));
    // 检测在返还jedis实例到连接池中时是否是完整的、可用的jedis实例
    private static boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true"));

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));

    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    /**
     * 初始化连接池
     */
    private static void initPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);

        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);

        // 连接耗尽的时候是否阻塞，false会抛出异常，true会阻塞到超时
        jedisPoolConfig.setBlockWhenExhausted(true);

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port, 1000*2);
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port, 1000*2);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>(2);
        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);

        // 初始化
        pool = new ShardedJedisPool(jedisPoolConfig, jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initPool();
    }

    /**
     * 从连接池获取ShardedJedis实例
     * @return
     */
    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    /**
     * 返还ShardedJedis到连接池
     * @param jedis
     */
    public static void returnResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }

    /**
     * 返还损坏的jedis到连接池
     * @param jedis
     */
    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }


    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        for (int i = 0 ; i < 10 ; i++) {
            jedis.set("key" + i, "value" + i);
        }
        returnResource(jedis);

        pool.destroy();
    }

}
