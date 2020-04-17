package com.teligen.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class JedisCache {
    @Autowired
    private JedisConfig jedisConfig;
    private static StringRedisSerializer keySerializer;
    private static JdkSerializationRedisSerializer valueSerializer;

    public final static String APP_JW_POLICE_USER_KEY_PREFIX = "user_jw_police_";
    public final static String APP_POLICE_USER_TOKEN_KEY_PREFIX = "app_police_user_token_prefix_";
    public final static String APP_POLICE_USER_TOKEN_TO_USRID_KEY_PREFIX = "app_police_user_token_to_usrid_prefix_";
    public final static String APP_JW_POLICE_DEPT_KEY_PREFIX = "dept_jw_police_";

    public final static long KEY_EXPIRE_TIME = 3 * 60 * 1000;

    public Jedis getJedis(){
        keySerializer = new StringRedisSerializer();
        valueSerializer = new JdkSerializationRedisSerializer();
        return jedisConfig.redisPoolFactory().getResource();
    }

    //给某个key设值
    public void set(String key, Object value) {
        Jedis client = getJedis();
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            byte[] valueBytes = valueSerializer.serialize(value);
            client.set(keyBytes, valueBytes);
        } finally {
            client.close();
        }
    }

    //给某个key设值
    public void set(String key, Object value, long existTime) {
        Jedis client = getJedis();
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            byte[] valueBytes = valueSerializer.serialize(value);
            byte[] nxxx = keySerializer.serialize("NX");
            byte[] expx = keySerializer.serialize("PX");
            client.set(keyBytes, valueBytes, nxxx, expx, existTime);
        } finally {
            client.close();
        }
    }

    //根据key获取value
    public Object get(String key) {
        Jedis client = getJedis();
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            byte[] valueBytes = client.get(keyBytes);
            return valueSerializer.deserialize(valueBytes);
        } finally {
            client.close();
        }
    }

    //根据键值获取value
    public Object hashGet(String key, String field) {
        Jedis client = getJedis();
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            byte[] fieldBytes = keySerializer.serialize(field);
            byte[] valueBytes = client.hget(keyBytes, fieldBytes);
            return valueSerializer.deserialize(valueBytes);
        } finally {
            client.close();
        }

    }

    public void hashSet(String key, String field, Object value) {
        Jedis client = getJedis();
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            byte[] fieldBytes = keySerializer.serialize(field);
            byte[] valueBytes = valueSerializer.serialize(value);
            client.hset(keyBytes, fieldBytes, valueBytes);
        } finally {
            client.close();
        }

    }


    public Map<String, Object> hashAllGet(String key) {
        Jedis client = getJedis();
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            Map<byte[], byte[]> map = client.hgetAll(keyBytes);
            Map<String, Object> valueMap = new HashMap<>();
            Set<Map.Entry<byte[], byte[]>> valueSet = map.entrySet();
            for (Map.Entry<byte[], byte[]> entry : valueSet) {
                valueMap.put(keySerializer.deserialize(entry.getKey()), valueSerializer.deserialize(entry.getValue()));
            }
            return valueMap;
        } finally {
            client.close();
        }

    }

    //判断key是否存在
    public boolean existKey(String key) {
        Jedis client = getJedis();
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            return client.exists(keyBytes);
        } finally {
            client.close();
        }
    }

    //判断key是否存在
    public void remove(String key) {
        Jedis client = getJedis();
        try {
            byte[] keyBytes = keySerializer.serialize(key);
            client.del(key);
        } finally {
            client.close();
        }
    }

}
