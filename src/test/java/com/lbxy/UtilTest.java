package com.lbxy;

import com.lbxy.utils.LoggerUtil;
import org.junit.jupiter.api.Test;

/**
 * @author lmy
 * @description UtilTest
 * @date 2018/8/14
 */

public class UtilTest {
    @Test
    void test() {
        LoggerUtil.info(getClass(), "hello");
    }
}
