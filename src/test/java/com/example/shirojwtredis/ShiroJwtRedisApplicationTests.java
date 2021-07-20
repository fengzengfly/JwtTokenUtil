package com.example.shirojwtredis;


import com.example.shirojwtredis.commons.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ShiroJwtRedisApplicationTests {
  private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjdXJyZW50IjoxNjI2Nzc2ODgyMzczLCJhdXRoIjoiZmVuZ3plbmciLCJleHAiOjE2MjY3Nzg2ODJ9.yRTWEUbwIEN3DC53bKqEfPI5mYPFJQcsgQLNtKDM8e4";


  @Resource
  private JwtTokenUtil jwtTokenUtil;

  @Test
  void contextLoads() {
  }

  @Test
  public void test() {


    String username = "fengzeng";
    String s = jwtTokenUtil.generateToken(username, System.currentTimeMillis());
    System.out.println(s);
  }

  @Test
  public void testToken() {
    System.out.println(jwtTokenUtil.getCurrent(TOKEN));
    System.out.println(jwtTokenUtil.getUsername(TOKEN));
    System.out.println(jwtTokenUtil.verify(TOKEN));
    System.out.println(jwtTokenUtil.isExpired(TOKEN));
  }

}
