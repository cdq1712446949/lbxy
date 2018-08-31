package com.lbxy.weixin.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.lbxy.core.interceptors.WeixinLoginInterceptor;
import com.lbxy.service.UserService;
import com.lbxy.service.impl.UserServiceImpl;
import com.lbxy.weixin.service.PayService;
import com.lbxy.weixin.utils.WeixinMessageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Created by lmy on 2017/9/16.
 */

public class PayController extends Controller {

    public static final UserService USER_SERVICE = new UserServiceImpl();
    private static final PayService PAY_SERVICE = PayService.getInstance();

    @Before(WeixinLoginInterceptor.class)
    public Map<String, String> pay(@NotBlank String fee, int userId) {

        String ip = getRequest().getRemoteAddr();

        Map<String, String> result = PAY_SERVICE.doPay(fee, ip, USER_SERVICE.findById(userId).getOpenId());

        return PAY_SERVICE.getH5PayParams(result);
    }

    public String processResult(HttpServletRequest request) throws Exception {

        Map<String, String> map = WeixinMessageUtil.xml2Map(request);

        return PAY_SERVICE.callBack(map);
    }
}
