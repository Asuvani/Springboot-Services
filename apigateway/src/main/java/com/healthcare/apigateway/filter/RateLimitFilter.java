package com.healthcare.apigateway.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.healthcare.apigateway.service.GatewayRateLimiterService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

	private final GatewayRateLimiterService rateLimiter;

	public RateLimitFilter(GatewayRateLimiterService rateLimiter) {
		this.rateLimiter = rateLimiter;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		if (!rateLimiter.isAllowed()) {
			response.setStatus(429);
			response.setContentType("application/json");

			response.getWriter().write("{\"error\":\"Too Many Requests\"}");
			return;
		}

		chain.doFilter(request, response);
	}

}
