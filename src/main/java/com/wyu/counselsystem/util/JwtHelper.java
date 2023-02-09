package com.wyu.counselsystem.util;

/**
 * @author afglow
 * @Date Create in 2022-10-2022/10/31-10:51
 * @Description token口令生成工具 JwtHelper
 */
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class JwtHelper {
    //过期时间
    private static long tokenExpiration = 24*60*60*1000;
    //秘钥
    private static String tokenSignKey = "9678524";

    //生成token字符串
    public static String createToken(String userName, Collection<? extends GrantedAuthority> authorities) {

        List<String> list = new ArrayList<>();
        for(GrantedAuthority g:authorities){
            list.add(g.toString());
        }
        String s = JSON.toJSONString(list);

        String token = Jwts.builder()

                .setSubject("User-Token")

                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))

                .claim("userName", userName)
                .claim("authorities", s)

                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }



    //从token字符串获取userType
    public static Integer getUserType(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (Integer)(claims.get("userType"));
    }

    //从token字符串获取userName
    public static String getUserName(String token) {
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("userName");
    }

    //从token字符串获取权限
    public static Collection<? extends GrantedAuthority> getUserGrant(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        String list = (String) claims.get("authorities");


        List<String> s = JSONArray.parseArray(list,String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String ss:s){
            authorities.add(new SimpleGrantedAuthority(ss));
        }
        return authorities;
    }

    //判断token是否有效
    public static boolean isExpiration(String token){
        try {
            boolean isExpire = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration().before(new Date());
            //没有过期，有效，返回false
            return isExpire;
        }catch(Exception e) {
            //过期出现异常，返回true
            return true;
        }
    }


    /**
     * 刷新Token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody();
            refreshedToken = JwtHelper.createToken(getUserName(token), getUserGrant(token));
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public static void main(String[] args) {
//        String token = JwtHelper.createToken(1L, "lucy");
//        System.out.println(token);
//        System.out.println(JwtHelper.getUserId(token));
//        System.out.println(JwtHelper.getUserName(token));
    }
}
