package com.caju.transaction_authorizer_challenge.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonConfig {
    val redisHost = System.getenv("REDIS_HOST") ?: "redis"
    val redisPort = System.getenv("REDIS_PORT")?.toInt() ?: 6379

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer()
            .address = "redis://$redisHost:$redisPort"
        return Redisson.create(config)
    }
}