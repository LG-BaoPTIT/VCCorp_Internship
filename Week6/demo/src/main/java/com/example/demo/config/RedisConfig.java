package com.example.demo.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}") String redisHost;
    @Value("${spring.redis.port}") int redisPort;
   // @Value("${spring.redis.password}") String redisPassword;
    @Value("${spring.redis.timeout}") long redisTimeout;
    @Value("${spring.redis.database}") int redisDatabase;
    @Value("${spring.redis.lettuce.pool.max-active}") int redisPoolMaxActive;
    @Value("${spring.redis.lettuce.pool.max-idle}") int redisPoolMaxIdle;
    @Value("${spring.redis.lettuce.pool.max-wait}") int redisPoolMaxWait;
    @Value("${spring.redis.lettuce.pool.min-idle}") int redisPoolMinIdle;

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofMillis(redisTimeout)).build();
        final ClientOptions clientOptions = ClientOptions.builder().socketOptions(socketOptions).build();

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(redisTimeout))
                .clientOptions(clientOptions).build();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
       // redisStandaloneConfiguration.setPassword(redisPassword);
        redisStandaloneConfiguration.setDatabase(redisDatabase);
        final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration,
                clientConfig);
        lettuceConnectionFactory.setValidateConnection(true);
        return lettuceConnectionFactory;
    }

    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // tạo ra một RedisTemplate
        // Với Key là Object
        // Value là Object
        // RedisTemplate giúp chúng ta thao tác với Redis
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
    /**
     * GenericObjectPoolConfig connection pool configuration
     *
     * @return
     */
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(redisPoolMaxIdle);
        genericObjectPoolConfig.setMinIdle(redisPoolMinIdle);
        genericObjectPoolConfig.setMaxTotal(redisPoolMaxActive);
        genericObjectPoolConfig.setMaxWaitMillis(redisPoolMaxWait);
        return genericObjectPoolConfig;
    }
//    @Bean
//    public HashOperations<Object, Object, Object> hashOperations(RedisTemplate<Object, Object> redisTemplate) {
//        return redisTemplate.opsForHash();
//    }
}
