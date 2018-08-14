package com.lbxy.utils;

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
    private final static Algorithm algorithm = Algorithm.HMAC256("X8FYY5O51k7w");
    private final static String ISSUER = "laumgjyu";
    private final static String SUBJECT = "wechat_app_lbxy";
    private final static String SIGN_CLASS = "com.lbxy.utils.JWTUtil";
    private static JWTVerifier verifier = null;

    public static String createToken() {
        String token = JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withSubject(SUBJECT)
                .withClaim("signClass", SIGN_CLASS)
                .sign(algorithm);
        return token;
    }

    public static boolean verifyToken(String token) {
        if (verifier == null) {
            synchronized (JWTUtil.class) {
                if (verifier == null) {
                    verifier = JWT.require(algorithm)
                            .withIssuer(ISSUER)
                            .withSubject(SUBJECT)
                            .withClaim("signClass", SIGN_CLASS)
                            .build(); //Reusable verifier instance
                }
            }
        }

        try {

            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
