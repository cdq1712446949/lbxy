package com.lbxy.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @author lmy
 * @description JWTUtil
 * @date 2018/8/14
 */
public class JWTUtil {
    private final static Algorithm ALGORITHM = Algorithm.HMAC256("X8FYY5O51k7w");
    private final static String ISSUER = "laumgjyu";
    private final static String SUBJECT = "wechat_app_lbxy";
    private final static String SIGN_CLASS = "com.lbxy.core.utils.JWTUtil";

    private JWTUtil() {

    }

    private static class VerifierHolder{
        private static JWTVerifier verifier = JWT.require(ALGORITHM)
                .withIssuer(ISSUER)
                .withSubject(SUBJECT)
                .withClaim("signClass", SIGN_CLASS)
                .build(); //Reusable verifier instance;
    }

    /**
     * 签发签名，其中带入userid参数
     * @param userId
     * @return
     */
    public static String createToken(long userId) {
        String token = JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withSubject(SUBJECT)
                .withClaim("signClass", SIGN_CLASS)
                .withClaim("id", userId)
                .sign(ALGORITHM);
        return token;
    }

    /**
     * 验证，验证成功之后返回当前user的id，如果失败返回-1
     * @param token
     * @return
     */
    public static int verifyToken(String token) {
        try {

            DecodedJWT jwt = VerifierHolder.verifier.verify(token);
            return jwt.getClaim("id").asInt();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
