package com.lbxy.weixin.servlet;

import com.lbxy.core.utils.LoggerUtil;
import com.lbxy.weixin.cache.AccessTokenCache;
import com.lbxy.weixin.utils.WeixinUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * 用于获取微信accessToken
 */
@WebServlet(
        name = "AccessTokenServlet",
        urlPatterns = "/self/AccessTokenServlet",
        loadOnStartup = 1
)
public class AccessTokenServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        LoggerUtil.info(getClass(), "服务已经启动，正在加载AccessTokenServlet");
        super.init();

        new Thread(() -> {
            //进入无限循环， 持续获得Token
            while (true) {
                try {
                    //获取AccessToken
                    AccessTokenCache.setAccessToken(WeixinUtil.getAccessTokenByRequest());

                    if (AccessTokenCache.getAccessToken() == null) {
                        Thread.sleep(1000 * 2);
                    } else { //正确获取token 按照过期时间判定休眠时间
                        LoggerUtil.fmtDebug(getClass(),
                                "已经正确获取AccessToken, 现在的token为->{%s}",
                                AccessTokenCache.getAccessToken().getAccess_token());
                        Integer sleepTime =
                                Integer.parseInt(AccessTokenCache.getAccessToken().getExpires_in())
                                        - 200;
                        Thread.sleep(1000 * sleepTime);
                    }
                } catch (Exception e) {
                    //处理异常 休眠1秒
                    LoggerUtil.fmtError(getClass(), e,
                            "获取AccessToken出现错误, 错误信息为->{%s}", e.getMessage());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    //处理异常结束
                }
            }
            //无限循环结束（不会结束
        }).start();
    }
}
