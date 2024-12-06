package com.seongjun.paymentmodule.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean(name = "myReactiveRedisTemplate")
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        RedisSerializationContext<String, String> serializationContext = RedisSerializationContext
                .<String, String>newSerializationContext(new StringRedisSerializer())
                .key(new StringRedisSerializer())
                .value(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new StringRedisSerializer())
                .build();

        return new ReactiveRedisTemplate<>(factory, serializationContext);
    }
//    @Bean
//    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
//        // RedisSerializationContext를 설정합니다.
//        RedisSerializationContext<String, String> serializationContext = RedisSerializationContext
//                .<String, String>newSerializationContext(new StringRedisSerializer())
//                .key(new StringRedisSerializer()) // Key Serializer
//                .value(new StringRedisSerializer()) // Value Serializer
//                .hashKey(new StringRedisSerializer()) // Hash Key Serializer
//                .hashValue(new StringRedisSerializer()) // Hash Value Serializer
//                .build();
//
//        return new ReactiveRedisTemplate<>(factory, serializationContext);
//    }
}