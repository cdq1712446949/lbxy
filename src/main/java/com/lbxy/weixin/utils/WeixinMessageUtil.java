package com.lbxy.weixin.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 96428 on 2017/7/13.
 */
public class WeixinMessageUtil {


    public static Map<String, String> xml2Map(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();

        SAXReader reader = new SAXReader();
        Document document = reader.read(request.getInputStream());

        Element root = document.getRootElement();
        List<Element> nodes = root.elements();

        for (Element e : nodes) {
            map.put(e.getName(), e.getText());
        }
        request.getInputStream().close();

        return map;
    }

    public static Map<String, String> xml2Map(String xml) throws DocumentException {
        Map<String, String> map = new HashMap<String, String>();

        StringReader stringReader = new StringReader(xml);
        InputSource source = new InputSource(stringReader);

        SAXReader reader = new SAXReader();
        Document document = reader.read(source);

        Element root = document.getRootElement();
        List<Element> nodes = root.elements();

        for (Element e : nodes) {
            map.put(e.getName(), e.getText());
        }

        return map;
    }

}
