package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/3/18 0018.
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 对象的所有字段全部列入序列化操作
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
        // 取消默认转换日期为timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
        // 忽略空的bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        // 所有日期的格式统一为yyyy-MM-dd HH:mm:ss格式
        objectMapper.setDateFormat(new SimpleDateFormat(DatetimeUtil.STANDARD_FORMAT));

        // 反序列化时，忽略在json字符串中存在、但是在java对象中不存在对应属性高的情况，防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转字符串（序列化）
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String Obj2String(T t) {
        if (t == null) {
            return null;
        }
        try {
            return t instanceof String ? (String) t : objectMapper.writeValueAsString(t);
        } catch (IOException e) {
            log.warn("parse obj to String error", e);
            return null;
        }
    }

    /**
     * 返回格式化好的json字符串（序列化）
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String Obj2StringPretty(T t) {
        if (t == null) {
            return null;
        }
        try {
            return t instanceof String ? (String) t : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
        } catch (IOException e) {
            log.warn("parse obj to String error", e);
            return null;
        }
    }

    /**
     * 字符串转成对象（反序列化）
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.warn("parse String to obj error", e);
            return null;
        }
    }

    /**
     * 反序列化成任意指定类型
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("parse String to obj error", e);
            return null;
        }
    }

    /**
     * 反序列化
     * @param str
     * @param collectionClass  集合类型
     * @param elementClasses 集合里面的元素类型（可变长度参数）
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.warn("parse String to obj error", e);
            return null;
        }
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        User u1 = new User();
        //User u2 = new User();
        u1.setId(1);
        u1.setEmail("ms@qq.com");
        //u2.setId(2);
        String u1Json = JsonUtil.Obj2String(u1);
        String u1Pretty = JsonUtil.Obj2StringPretty(u1);

        log.info("u1Json:{}", u1Json);
//        log.info("u1Pretty:{}", u1Pretty);
//
//        User u = JsonUtil.string2Obj(u1Json, User.class);
//
//        List<User> userList = Lists.newArrayList();
//        userList.add(u1);
//        userList.add(u2);
//        String userListStr = JsonUtil.Obj2StringPretty(userList);
//        log.info(userListStr);
//        List<User> userList1 = JsonUtil.string2Obj(userListStr, new TypeReference<List<User>>() {});
    }

}
