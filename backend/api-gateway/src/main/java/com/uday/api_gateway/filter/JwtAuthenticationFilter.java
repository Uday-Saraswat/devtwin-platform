package com.uday.api_gateway.filter;

import com.uday.api_gateway.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

         /*
         PUBLIC ROUTES
         */

        if (path.contains("/auth/login") || path.contains("/auth/register")) {
            return chain.filter(exchange);
        }

        /*
            CHECK AUTH HEADER
         */

        String authHeader = exchange.getRequest().getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(exchange, "Missing Authorization Header",
                    HttpStatus.UNAUTHORIZED);
        }

         /*
            EXTRACT TOKEN
         */

        String token = authHeader.substring(7);

         /*
            VALIDATE TOKEN
         */

        if (!jwtUtil.isTokenValid(token)) {
            return onError(exchange, "Invalid or Expired Token",
                    HttpStatus.UNAUTHORIZED);
        }

         /*
            ROLE BASED ACCESS
         */

        String role = jwtUtil.extractRole(token);

        if (path.contains("/admin") && !role.equals("ADMIN")) {
            return onError(exchange, "Access Denied",
                    HttpStatus.FORBIDDEN);
        }


        return chain.filter(exchange);
    }

     /*
        CUSTOM ERROR RESPONSE
     */

    private Mono<Void> onError(ServerWebExchange exchange,
                               String message, HttpStatus httpStatus) {

        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String body = """
                {
                    "success": false,
                    "message": "%s"
                }
                """.formatted(message);

        var buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes());

        return exchange.getResponse().writeWith(Mono.just(buffer));

    }

    @Override
    public int getOrder() {
        return -1;
    }
}
