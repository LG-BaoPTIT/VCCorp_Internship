package com.ite.authservice.security.signature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SignatureValidationFilter extends OncePerRequestFilter {

    @Autowired
    private SignatureService signatureService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        // Kiểm tra URI của yêu cầu
        String uri = request.getRequestURI();
        // check signature of request
        System.out.println(uri);
        if (uri.contains("/swagger-ui.html") || uri.contains("/swagger-resources")
                || uri.contains("/v2/api-docs") || uri.contains("/webjars/swagger-ui") || uri.contains("/favicon.ico") ||uri.contains("swagger") || uri.contains("api-docs")

        ) {
            filterChain.doFilter(request, response);
        }
        else {
            if (uri.startsWith("/admin") && !signatureService.verifySignatureOfWebAdmin(request)) {
                //  401 Unauthorized
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid signature");
                return;

            }
            else {
                if(uri.startsWith("/ecommerce") && !signatureService.verifySignatureOfWebEcom(request)){
                    //  401 Unauthorized
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid signature");
                    return;
                }
            }
            //if the signature is valid,continue processing the request
            filterChain.doFilter(request, response);
        }

    }
}
