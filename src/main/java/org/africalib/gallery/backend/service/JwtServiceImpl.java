package org.africalib.gallery.backend.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("jwtService")
public class JwtServiceImpl implements JwtService{

    // 밑은 임의 문자임. 문자가 상당히 길어야, 아니면 에러남.
    private String secretKey = "YWJjY2kyaW9hZGlqQEBAYWkxN2E2NjIjIzM4MTM5IyEjQGF1c3VkYWhkMTc4MzE2NzM4Njg2ODdAYWRn";

    @Override
    public String getToken(String key, Object value) { // 토큰 만들기.
        Date expTime = new Date();
        expTime.setTime(expTime.getTime()+1000*60*30); // 30분
        byte[] secretByteKey = Base64.getDecoder().decode(secretKey);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "H256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder= Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }

    @Override
    public Claims getClaims(String token) {
        if(token !=null && !"".equals(token)){
            try {
                byte[] secretByteKey = Base64.getDecoder().decode(secretKey);
                Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
                Claims claims = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
                return claims;

            }catch (ExpiredJwtException e){
                // 만료시
            }catch (JwtException e){
                //유효하지 않을 시

            }
        }
        return null;
    }

    @Override
    public boolean isValid(String token) {
        return this.getClaims(token) !=null;
    }

    @Override
    public int getId(String token) {
        Claims claims = this.getClaims(token);
        if(claims !=null){
            return Integer.parseInt(claims.get("id").toString());
        }
        return 0;
    }
}
