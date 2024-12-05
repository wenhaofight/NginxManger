package pers.wenhao.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//@Configuration
public class RedisConfig {

    /***
     * 模板操作对象序列化设置
     * @param redissonConnectionFactory
     * @return
     */
    @Bean("redisTemplate")
    public RedisTemplate getRedisTemplate(RedisConnectionFactory redissonConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redissonConnectionFactory);
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /****
     * 序列化设置
     * @return
     */
    @Bean
    public StringRedisSerializer keySerializer() {
        return new StringRedisSerializer();
    }

    /****
     * 序列化设置
     * @return
     */
    @Bean
    public RedisSerializer valueSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
        return jackson2JsonRedisSerializer;
    }
}