package com.opensponsor.utils.wechat;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

import static com.wechat.pay.contrib.apache.httpclient.constant.WechatPayHttpHeaders.*;


public class WechatPayVerifier {
    private static final String certificate = "-----BEGIN CERTIFICATE-----" +
        "-----END CERTIFICATE-----";

    protected static IllegalArgumentException parameterError(String message, Object... args) {
        message = String.format(message, args);
        return new IllegalArgumentException("parameter error: " + message);
    }

    //
    public final boolean validate(CloseableHttpResponse response) throws IOException {
        validateParameters(response);
        String message = buildMessage(response);
        String serial = response.getFirstHeader(WECHAT_PAY_SERIAL).getValue(); //Should be used to find which cert should be used to verify
        String signature = response.getFirstHeader(WECHAT_PAY_SIGNATURE).getValue();
        return verify(loadCertificate(certificate), message.getBytes(StandardCharsets.UTF_8), signature);
    }

    public X509Certificate loadCertificate(String certificate) {
        InputStream certStream = new ByteArrayInputStream(certificate.getBytes());
        X509Certificate cert = null;
        try{
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            cert = (X509Certificate) cf.generateCertificate(certStream);
            cert.checkValidity();
        } catch (CertificateException e) {
            throw new RuntimeException("Fail to load and vailid the certificate", e);
        }
        return cert;
    }

    protected boolean verify(X509Certificate certificate, byte[] message, String signature) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(certificate);
            sign.update(message);
            return sign.verify(Base64.getDecoder().decode(signature));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名验证过程发生了错误", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的证书", e);
        }
    }

    protected final void validateParameters(CloseableHttpResponse response) {
        Header firstHeader = response.getFirstHeader(REQUEST_ID);
        if (firstHeader == null) {
            throw parameterError("empty " + REQUEST_ID);
        }
        String requestId = firstHeader.getValue();

        // NOTE: ensure HEADER_WECHAT_PAY_TIMESTAMP at last
        String[] headers = {WECHAT_PAY_SERIAL, WECHAT_PAY_SIGNATURE, WECHAT_PAY_NONCE, WECHAT_PAY_TIMESTAMP};

        Header header = null;
        for (String headerName : headers) {
            header = response.getFirstHeader(headerName);
            if (header == null) {
                throw parameterError("empty [%s], request-id=[%s]", headerName, requestId);
            }
        }

        String timestampStr = header.getValue();
        try {
            Instant responseTime = Instant.ofEpochSecond(Long.parseLong(timestampStr));
            // 拒绝过期应答
            int RESPONSE_EXPIRED_MINUTES = 5;
            if (Duration.between(responseTime, Instant.now()).abs().toMinutes() >= RESPONSE_EXPIRED_MINUTES) {
                throw parameterError("timestamp=[%s] expires, request-id=[%s]", timestampStr, requestId);
            }
        } catch (DateTimeException | NumberFormatException e) {
            throw parameterError("invalid timestamp=[%s], request-id=[%s]", timestampStr, requestId);
        }
    }

    protected final String buildMessage(CloseableHttpResponse response) throws IOException {
        String timestamp = response.getFirstHeader(WECHAT_PAY_TIMESTAMP).getValue();
        String nonce = response.getFirstHeader(WECHAT_PAY_NONCE).getValue();
        String body = getResponseBody(response);
        return timestamp + "\n"
            + nonce + "\n"
            + body + "\n";
    }

    protected final String getResponseBody(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return (entity != null && entity.isRepeatable()) ? EntityUtils.toString(entity) : "";
    }
}
