package com.ite.authservice.security.signature;

import javax.servlet.http.HttpServletRequest;

public interface SignatureService {
    boolean verifySignatureOfWebAdmin(HttpServletRequest request);
    boolean verifySignatureOfWebEcom(HttpServletRequest request);

}
