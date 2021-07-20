package com.example.shirojwtredis.commons.utils;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * @author fengzeng
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtTokenUtil implements Serializable {

  /**
   * 权限类型
   */
  private String authoritiesKey;

  /**
   * 密钥KEY
   */
  private String secret;
  /**
   * TokenKey
   */
  private String tokenHeader;
  /**
   * 过期时间
   */
  private Integer expiration;
  /**
   * redis过期时间
   */
  private Integer redisExpiration;

  /**
   * 生成token
   * @param username 用户名
   * @param current 过期时间
   * @return
   */
  public  String generateToken(String username, Long current) {
    if (null == current) {
      current = System.currentTimeMillis();
    }
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create()
            .withClaim(authoritiesKey, username)
            .withClaim("current", current)
            .withExpiresAt(new Date(current + expiration * 1000))
            .sign(algorithm);
  }

  /**
   * 从token中获取username
   * @param token token
   * @return
   */
  public  String getUsername(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim(authoritiesKey).asString();
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  /**
   * 获取token
   * @return token
   */
  public  String getToken() {
    String token;
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    token = request.getHeader(tokenHeader);
    if (StrUtil.isBlank(token)) {
      return "";
    }
    return token;
  }

  /**
   * token是否过期
   * @param token token
   * @return  true or false
   */
  public  boolean isExpired(String token) {
    DecodedJWT jwt = JWT.decode(token);
    Date expiration = jwt.getExpiresAt();
    return expiration.before(new Date());
  }

  /**
   * 验证token
   * @param token token
   * @return true or false
   */
  public  boolean verify(String token) {
    try {
      //解密
      JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
      verifier.verify(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 获取token过期时间,
   * @param token token
   * @return 时间戳
   */
  public  Long getCurrent(String token){
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim("current").asLong();
    }catch (Exception e){
      return null;
    }
  }
}