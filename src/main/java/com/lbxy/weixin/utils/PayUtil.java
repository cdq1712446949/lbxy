package com.lbxy.weixin.utils;


import com.lbxy.core.utils.NetWorkUtil;
import com.lbxy.weixin.properties.Api;
import org.dom4j.DocumentException;

import java.util.Map;

/**
 * Created by lmy on 2017/12/27.
 */
public class PayUtil {
    /**
     * 查询订单接口
     */
    public static Map<String, String> orderQuery(String params) {
        String result = NetWorkUtil.doPostUriReturnPlainText(Api.ORDER_QUERY, params);
        Map<String, String> xml = null;
        try {
            xml = XmlUtil.xml2Map(result);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return xml;
    }

}
