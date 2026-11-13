package com.healthcare.apigateway.service;

import org.springframework.stereotype.Service;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;

@Service
public class GatewayRateLimiterService {

    private final RateLimiter rateLimiter;

    public GatewayRateLimiterService(
            RateLimiterRegistry registry) {

        this.rateLimiter =
            registry.rateLimiter("gatewayRateLimiter");
    }

    public boolean isAllowed() {
        return rateLimiter.acquirePermission();
    }
}
