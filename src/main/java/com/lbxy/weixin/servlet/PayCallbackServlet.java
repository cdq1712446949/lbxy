package com.lbxy.weixin.servlet;

import com.alibaba.fastjson.JSON;
import com.lbxy.common.status.BillStatus;
import com.lbxy.core.plugins.cache.InjectionCache;
import com.lbxy.model.Bill;
import com.lbxy.service.BillService;
import com.lbxy.service.OrderService;
import com.lbxy.weixin.service.PayService;
import com.lbxy.weixin.utils.PayCacheUtil;
import com.lbxy.weixin.utils.WeixinMessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author lmy
 * @description PayCallbackServlet
 * @date 2018/8/31
 */
@WebServlet(
        name = "PayServlet",
        urlPatterns = "/wx/payDoneCallback",
        loadOnStartup = 1
)
public class PayCallbackServlet extends HttpServlet {
    private static final PayService PAY_SERVICE = PayService.getInstance();

    private static final OrderService ORDER_SERVICE = (OrderService) InjectionCache.get("orderService");

    private static final BillService BILL_SERVICE = (BillService) InjectionCache.get("billService");

    @Override
    public void init() throws ServletException {
        super.init();

        PAY_SERVICE.setSuccessHandler(result -> {
            String out_trade_no = result.get("out_trade_no");
            Double totalFee = Double.valueOf(result.get("total_fee"));
            String orderId = PayCacheUtil.get(out_trade_no + "orderId");
            String userId = PayCacheUtil.get(out_trade_no + "userId");
            ORDER_SERVICE.payOrder(Integer.parseInt(orderId));

            Bill bill = new Bill();
            bill.setOrderId(Long.valueOf(orderId));
            bill.setUserId(Long.valueOf(userId));

            bill.setMoney(BigDecimal.valueOf(totalFee / 100));
            bill.setStatus(BillStatus.PAY);
            bill.setCreatedDate(new Date());

            BILL_SERVICE.add(bill);
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Map<String, String> map = null;
        try {
            map = WeixinMessageUtil.xml2Map(req);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSONString(PAY_SERVICE.callBack(map)));
        out.flush();
        out.close();
    }
}
