package com.eren.cloudmusic.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.eren.cloudmusic.exceptions.VerifyCode;
import com.eren.cloudmusic.properties.PathProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.common.IOUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private byte[] key;
    private final int intervalLength = 1024;

    @Resource
    private AntPathMatcher antPathMatcher;

    @Resource
    private PathProperties pathProperties;

    public AuthGlobalFilter() throws IOException {

        try(InputStream reader = AuthGlobalFilter.class.getClassLoader().getResourceAsStream("private_key.txt")){
            if (reader == null){
                throw new IOException("private_key.txt not found");
            }
            key = IoUtil.readBytes(reader);
        }catch (IOException e){
            log.error("private_key.txt not found");
        }


    }

    public boolean isExclude(String path) {
        return pathProperties.getExclude().stream().anyMatch(excludePath -> antPathMatcher.match(excludePath, path));
    }


    // 登录验证
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> authorization = exchange.getRequest().getHeaders().get("Authorization");
        Long userId = null;
        if (isExclude(exchange.getRequest().getPath().toString())){
            return chain.filter(exchange);
        }

        if (!CollectionUtil.isEmpty(authorization)) {
            if (JWTUtil.verify(authorization.get(0), key)){
                JWT jwt = JWTUtil.parseToken(authorization.get(0));
                userId = (Long) jwt.getPayload("userId");
            } else {
                ServerHttpResponse response = exchange.getResponse();
                DataBuffer dataBuffer = response.bufferFactory()
                        .wrap(JSONUtil.toJsonStr(Map.of("code", VerifyCode.TOKEN_EXP.getCode(), "message", VerifyCode.TOKEN_EXP.getMessage()))
                                .getBytes(StandardCharsets.UTF_8));
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return response.writeWith(Mono.just(dataBuffer));
            }
        }

        Long finalUserId = userId;
        return chain.filter(exchange.mutate().request(request -> request.header("userId", String.valueOf(finalUserId)).build()).build());

    }




    @Override
    public int getOrder() {
        return 0;
    }
}
