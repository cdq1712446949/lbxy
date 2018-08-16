package com.lbxy;

import com.lbxy.core.utils.JWTUtil;
import org.junit.jupiter.api.Test;

/**
 * @author lmy
 * @description UtilTest
 * @date 2018/8/14
 */

class UtilTest {
    @Test
    void test() {
        System.out.println(JWTUtil.createToken(10));

    }
}
