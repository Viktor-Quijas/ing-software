package com.codea.backend.services;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final Map<String, Date> revoked = new ConcurrentHashMap<>();

    public void revoke(String jti, Date expiresAt) {
        purgeExpired();
        if (jti != null && expiresAt != null) {
            revoked.put(jti, expiresAt);
        }
    }

    public boolean isRevoked(String jti) {
        return jti != null && revoked.containsKey(jti);
    }

    private void purgeExpired() {
        Date now = new Date();
        revoked.entrySet().removeIf(e -> e.getValue().before(now));
    }
}