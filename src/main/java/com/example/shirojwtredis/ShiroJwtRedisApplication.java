package com.example.shirojwtredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author fengzeng
 */
@SpringBootApplication
@EnableConfigurationProperties
public class ShiroJwtRedisApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShiroJwtRedisApplication.class, args);
  }

}
