package com.ite.authservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public ResponseUtil() {
    }
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"Message\":\""+responseMessage+"\"}",httpStatus);
    }

    public static ResponseEntity<String> getResponseEntity(String errorCode, String errorMessage, HttpStatus httpStatus){
        String response = "{\"error_code\": \"" + errorCode + "\", \"error_message\": \"" + errorMessage + "\"}";
        return new ResponseEntity<>(response, httpStatus);
    }
}
