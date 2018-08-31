package com.lbxy.weixin.servlet;

import com.alibaba.fastjson.JSON;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.plugins.cache.InjectionCache;
import com.lbxy.core.utils.JWTUtil;
import com.lbxy.model.User;
import com.lbxy.service.UserService;
import com.lbxy.weixin.service.PayService;
import com.lbxy.weixin.utils.PayCacheUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lmy
 * @description PayServlet
 * @date 2018/8/31
 */
@WebServlet(
        name = "PayServlet",
        urlPatterns = "/wx/pay",
        loadOnStartup = 1
)
public class PayServlet extends HttpServlet {

    private static final PayService PAY_SERVICE = PayService.getInstance();

    private static final UserService USER_SERVICE = (UserService) InjectionCache.get("userService");

    @Override
    public void init() throws ServletException {
        super.init();

        PAY_SERVICE.setPreHandler(result -> {
            String out_trade_no = result.get("out_trade_no");
            String orderId = result.get("orderId");
            String userId = result.get("userId");
            PayCacheUtil.put(out_trade_no + "orderId", orderId);
            PayCacheUtil.put(out_trade_no + "userId", userId);
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String ip = req.getRemoteAddr();
        String fee = req.getParameter("fee");
        String orderId = req.getParameter("orderId");
        User currentUser = this.getCurrentUser(req, resp);
        String openId = currentUser.getOpenId();
        long userId = currentUser.getId();

        Map<String, String> result = PAY_SERVICE.doPay(fee, ip, openId, new HashMap<String, String>() {{
            put("orderId", orderId);
            put("userId", String.valueOf(userId));
        }});

        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSONString(PAY_SERVICE.getH5PayParams(result)));
        out.flush();
        out.close();
    }

    private User getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Authorization");
        if (token == null) {
            PrintWriter out = response.getWriter();
            out.print(JSON.toJSONString(MessageVoUtil.needLogin()));
            out.flush();
            out.close();
            throw new IOException("当前请求用户未登录");
        } else {
            int userId = JWTUtil.verifyToken(token);
            User user = USER_SERVICE.findById(userId);
            return user;
        }
    }
}
