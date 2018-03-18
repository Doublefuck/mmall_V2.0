package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * Created by Administrator on 2018/3/17 0017.
 */
public class RedisPool {

    // jedis连接池
    private static JedisPool pool;

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

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));
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

        pool = new JedisPool(jedisPoolConfig, redisIp, redisPort, 1000*2);
    }

    static {
        initPool();
    }

    /**
     * 从连接池获取jedis实例
     * @return
     */
    public static Jedis getJedis() {
        return pool.getResource();
    }

    /**
     * 返还jedis到连接池
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        pool.returnResource(jedis);
    }

    /**
     * 返还损坏的jedis到连接池
     * @param jedis
     */
    public static void returnBrokenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }


    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("ms", "mss");
        returnResource(jedis);

        pool.destroy();
    }

}
