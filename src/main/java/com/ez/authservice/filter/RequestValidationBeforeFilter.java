package com.ez.authservice.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestValidationBeforeFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization != null) {

            authorization = authorization.trim();

            if (StringUtils.startsWithIgnoreCase(authorization, "Basic")) {
                byte[] base64Bytes = authorization.substring(6).getBytes(StandardCharsets.UTF_8);

                byte[] decode = Base64.getDecoder().decode(base64Bytes);
                String decodedToken = new String(decode, StandardCharsets.UTF_8);
                int deli = decodedToken.indexOf(":");
                if (deli == -1) {
                    throw new BadCredentialsException("Invalid Basic Authentication Toke");
                }
                String email = decodedToken.substring(0,deli);
                if (email.toLowerCase().contains("test")) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
