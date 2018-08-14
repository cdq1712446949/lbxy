package com.lbxy;

import com.lbxy.utils.LoggerUtil;
import com.lbxy.utils.NetWorkUtil;
import com.lbxy.utils.RandomAvatarUtil;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

/**
 * @author lmy
 * @description UtilTest
 * @date 2018/8/14
 */

class UtilTest {
    @Test
    void test() {
            RandomAvatarUtil.generateAvatar("./test.png");
    }
}
