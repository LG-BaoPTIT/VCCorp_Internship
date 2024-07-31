package com.ite.authservice.security.signature;

import com.ite.authservice.security.encrypt.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class SignatureServiceImpl implements SignatureService{
    @Autowired
    private AES aes;

    @Value("${signature.admin.privateKey}")
    private String ADMIN_PRIVATE_KEY;

    @Value("${signature.ecommerce.privateKey}")
    private String ECOMMERCE_PRIVATE_KEY;

    @Override
    public boolean verifySignatureOfWebAdmin(HttpServletRequest request) {
        String encryptedBody = request.getHeader("Encrypted-Body");
       return verifySignature(request, aes.adminDecrypt(encryptedBody),ADMIN_PRIVATE_KEY);
    }

    @Override
    public boolean verifySignatureOfWebEcom(HttpServletRequest request) {
        String encryptedBody = request.getHeader("Encrypted-Body");
        return verifySignature(request,aes.ecomDecrypt(encryptedBody),ECOMMERCE_PRIVATE_KEY);

    }

    private boolean verifySignature(HttpServletRequest request,String payload, String key){
        try {
            String accessToken = null;
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                accessToken = authorizationHeader.substring(7);
            }
//            String header = extractHeaders(request);
//            String method = request.getMethod();
//            String uri = request.getRequestURI();
//            String payload = extractPayload(request);

            String receivedSignature = request.getHeader("Signature");
            String calculatedSignature = signRequest(payload,accessToken,key);
            System.out.println(calculatedSignature);
            return receivedSignature != null && receivedSignature.equals(calculatedSignature);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String signRequest(String payload,String accessToken,String key) throws NoSuchAlgorithmException, InvalidKeyException {
        StringBuilder requestDataBuilder = new StringBuilder();
        if(payload != null){
            requestDataBuilder.append(payload);
        }

        if(accessToken!= null){
            requestDataBuilder.append(accessToken);
        }
        String requestData = requestDataBuilder.toString()  ;
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] signatureBytes = mac.doFinal(requestData.getBytes());
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

}
//
//
//
//    private String extractAdminPayload(HttpServletRequest request) throws IOException {
//        InputStream inputStream = request.getInputStream();
//        byte[] body = StreamUtils.copyToByteArray(inputStream);
//        String payloadString = new String(body, StandardCharsets.UTF_8);
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode payloadJson = objectMapper.readTree(payloadString);
//
//        String payload = payloadJson.toString();
//
//        String encryptedBody = request.getHeader("Encrypted-Body");
//        return aes.adminDecrypt(encryptedBody);
//    }
//
//    private String extractHeaders(HttpServletRequest request) {
//        StringBuilder headersBuilder = new StringBuilder();
//
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            headersBuilder.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
//        }
//
//        return headersBuilder.toString();
//    }
