package org.africalib.gallery.backend.service;

import io.jsonwebtoken.Claims;

public interface JwtService { // 013140
     String getToken(String key, Object value);

     Claims getClaims(String token);
     boolean isValid(String token);
     int getId(String token);
}
