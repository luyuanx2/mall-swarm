package com.macro.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用启动入口
 * Created by macro on 2018/4/26.
 */
@SpringBootApplication(exclude={
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
})
@EnableScheduling
public class MallTestApplication {


    public static void main(String[] args) {

        SpringApplication.run(MallTestApplication.class, args);

    }

}
