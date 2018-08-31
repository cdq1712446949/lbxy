package com.lbxy.core.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 96428 on 2017/7/17.
 * This in TestWeixin, ${PACKAGE_NAME}
 */
public class XmlUtil {

    /**
     * 将xml字符串转化为一个java实体对象
     *
     * @param clazz 对象的class
     * @param xml   xml字符串
     * @param <T>   对象
     * @return xml对应的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlStr2Object(Class<T> clazz, String xml) throws JAXBException {
        T xmlObject = null;

        JAXBContext context = JAXBContext.newInstance(clazz);
        // 进行将Xml转成对象的核心接口
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader sr = new StringReader(xml);
        xmlObject = (T) unmarshaller.unmarshal(sr);


        return xmlObject;
    }

    /**
     * 将xml文件转化为一个java实体对象
     *
     * @param clazz    对象的class
     * @param filePath xml文件所处位置
     * @param <T>      对象
     * @return xml对应的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlFile2Object(Class<T> clazz, String filePath) throws FileNotFoundException, JAXBException {
        T xmlObject = null;

        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        try {
            InputStreamReader fr = new InputStreamReader(
                    new FileInputStream(filePath), "UTF-8");
            BufferedReader br = new BufferedReader(fr);
            xmlObject = (T) unmarshaller.unmarshal(br);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return xmlObject;
    }

    /**
     * 对象转xml字符串
     *
     * @param object 需要转化的对象
     * @return xml字符串
     */
    public static String object2XmlString(Object object) {
        StringWriter writer = new StringWriter();
        String result = null;

        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(object, writer);

            writer.flush();
            result = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void object2XmlFile(Object object, String filePath) {
        String result = null;
        try {
            FileWriter writer = new FileWriter(filePath);
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(object, writer);

            writer.flush();
            writer.close();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }

    }

    public static void object2XmlStream(Object object, BufferedWriter writer) {
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(object, writer);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将map转换为xml
     * @param map
     * @return
     */
    public static String map2Xml(Map map) {
        LoggerUtil.debug(XmlUtil.class, String.format("将Map转成Xml, Map：%s", map.toString()));
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<xml>");
        map2Xml(map, sb);
        sb.append("</xml>");
        String result = sb.toString();
        LoggerUtil.debug(XmlUtil.class, "将Map转成Xml, Xml：" + result);
        return result;
    }

    private static void map2Xml(Map map, StringBuffer sb) {
        Set set = map.keySet();
        for (Object aSet : set) {
            String key = (String) aSet;
            Object value = map.get(key);
            if (null == value)
                value = "";
            if (value.getClass().getName().equals("java.util.ArrayList")) {
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<").append(key).append(">");
                for (Object aList : list) {
                    HashMap hm = (HashMap) aList;
                    map2Xml(hm, sb);
                }
                sb.append("</").append(key).append(">");

            } else {
                if (value instanceof HashMap) {
                    sb.append("<").append(key).append(">");
                    map2Xml((HashMap) value, sb);
                    sb.append("</").append(key).append(">");
                } else {
                    sb.append("<").append(key).append(">")
                            .append(value)
                            .append("</").append(key).append(">");
                }

            }

        }
    }

}
