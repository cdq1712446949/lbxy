package com.lbxy.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Created by 96428 on 2017/7/16.
 */
public class NetWorkUtil {

    private static final Class CLAZZ = NetWorkUtil.class;

    private static HttpGet preDoGet(String uri) {

        LoggerUtil.debug(CLAZZ, "do get the " + uri);

        HttpGet get = new HttpGet(uri);
        //设置请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        get.setConfig(requestConfig);

        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

        return get;
    }

    public static void doGetDownload(String uri,String savePath) {
        HttpGet get = preDoGet(uri);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                LoggerUtil.debug(CLAZZ, "got response success " );
                response.getEntity().writeTo(new FileOutputStream(savePath));
            } else {
                LoggerUtil.error(CLAZZ, "something wrong happened" );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static JSONObject doGetUri(String uri) {
        HttpGet get = preDoGet(uri);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                LoggerUtil.debug(CLAZZ, "got response : " + result);
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                LoggerUtil.error(CLAZZ, "something wrong happened：" + EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return JSON.parseObject(result);
    }

    public static JSONObject doFormPostUriReturnJson(String uri, Map<String, String> params) {
        String result = doFormPost(uri, params);
        return JSON.parseObject(result);
    }

    public static JSONObject doPostUriReturnJson(String uri, String params) {
        String result = doPost(uri, params);
        return JSON.parseObject(result);
    }

    public static String doPostUriReturnPlainText(String uri, String params) {
        return doPost(uri, params);
    }

    private static HttpPost preDoPost(String url) {
        HttpPost post = new HttpPost(url);
        //设置请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(50000)   //设置连接超时时间
                .setConnectionRequestTimeout(50000) // 设置请求超时时间
                .setSocketTimeout(50000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        post.setConfig(requestConfig);
        return post;
    }

    private static String doFormPost(String url, Map<String, String> data) {
        LoggerUtil.fmtDebug(CLAZZ, "do post the %s, data is %s", url, data);
        HttpPost post = preDoPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        ContentType strContent = ContentType.create("text/plain", Charset.forName("UTF-8"));
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("UTF-8"));
        if (data != null && data.size() != 0) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                builder.addTextBody(entry.getKey(), entry.getValue(), strContent);
            }
            post.setEntity(builder.build());
        }

        String result = "";
        try {
            CloseableHttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
                LoggerUtil.debug(CLAZZ, "got response : " + result);
            } else {
                LoggerUtil.info(CLAZZ, "something wrong happened,error code is " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static String doPost(String uri, String params) {
        LoggerUtil.fmtDebug(CLAZZ, "do post the %s, data is %s", uri, params);

        HttpPost post = preDoPost(uri);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String result = "";

        try {
            post.setEntity(new StringEntity(params, "UTF-8"));/** 设置utf-8字符集，否则请求微信数据可能会出现中文乱码 */
            CloseableHttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
                LoggerUtil.debug(CLAZZ, "got response : " + result);
            } else {
                LoggerUtil.info(CLAZZ, "something wrong happened,error code is " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 执行文件上传
     *
     * @param url           远程接收文件的地址
     * @param localFilePath 本地文件地址
     * @return
     * @throws IOException
     */
    public static JSONObject uploadFile(String url, String localFilePath, String propertyName) {

        // 建立一个sslcontext，这里我们信任任何的证书。
        SSLContext context = null;
        try {
            context = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] arg0, String arg1)
                                throws CertificateException {
                            // 这一句就是信任任何的证书，当然你也可以去验证微信服务器的真实性
                            return true;
                        }
                    }).build();          // 建立socket工厂
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context);

        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(factory).build();

        // 把文件转换成流对象FileBody
        File localFile = new File(localFilePath);
        FileBody fileBody = new FileBody(localFile);

        // 以浏览器兼容模式运行，防止文件名乱码。
        HttpEntity reqEntity = null;
        try {
            reqEntity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart(propertyName, fileBody)
                    .addBinaryBody("media", localFile, ContentType.APPLICATION_OCTET_STREAM, localFile.getName())
                    .setCharset(CharsetUtils.get("UTF-8"))
                    .build();

            LoggerUtil.fmtDebug(NetWorkUtil.class, "上传文件体：length，%s type，%s charset，%s", reqEntity.getContentLength(), reqEntity.getContentType(), reqEntity.getContentEncoding());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // uploadFile对应服务端类的同名属性<File类型>
        // .addPart("uploadFileName", uploadFileName)
        // uploadFileName对应服务端类的同名属性<String类型>

        String result = "";

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Accept", "*/*");
            httpPost.setEntity(reqEntity);
            httpResponse = httpClient.execute(httpPost);
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                LoggerUtil.debug(CLAZZ, "got response : " + result);
            } else {
                LoggerUtil.error(CLAZZ, "something wrong happened,error code is " + statusCode);
            }
        } catch (Exception e) {
            LoggerUtil.error(NetWorkUtil.class, "发送请求失败");
        } finally {

            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                    LoggerUtil.error(NetWorkUtil.class, "关闭httpResponse时出现错误", e);
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    LoggerUtil.error(NetWorkUtil.class, "关闭httpClient时出现错误", e);
                }
            }
        }

        return JSON.parseObject(result);
    }

    /**
     * 执行文件上传同时传递参数
     *
     * @param url           远程接收文件的地址
     * @param localFilePath 本地文件地址
     * @return
     * @throws IOException
     */
    public static JSONObject uploadFile(String url, String localFilePath, String propertyName, Map<String, String> params) {

        // 建立一个sslcontext，这里我们信任任何的证书。
        SSLContext context = null;
        try {
            context = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] arg0, String arg1)
                                throws CertificateException {
                            // 这一句就是信任任何的证书，当然你也可以去验证微信服务器的真实性
                            return true;
                        }
                    }).build();          // 建立socket工厂
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context);

        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(factory).build();

        // 把文件转换成流对象FileBody
        File localFile = new File(localFilePath);
        FileBody fileBody = new FileBody(localFile);
        //map数据
        ContentType strContent = ContentType.create("text/plain", Charset.forName("UTF-8"));
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (params != null && params.size() != 0) {
            int index = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), entry.getValue(), strContent);
            }
            try {
                builder.addPart(propertyName, fileBody)
                        .addBinaryBody("media", localFile, ContentType.APPLICATION_OCTET_STREAM, localFile.getName())
                        .setCharset(CharsetUtils.get("UTF-8"))
                        .build();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        HttpEntity reqEntity = null;
        reqEntity = builder.build();
        // 以浏览器兼容模式运行，防止文件名乱码。
        LoggerUtil.fmtDebug(NetWorkUtil.class, "上传文件体：length，%s type，%s charset，%s", reqEntity.getContentLength(), reqEntity.getContentType(), reqEntity.getContentEncoding());
        // uploadFile对应服务端类的同名属性<File类型>
        // .addPart("uploadFileName", uploadFileName)
        // uploadFileName对应服务端类的同名属性<String类型>

        String result = "";

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Accept", "*/*");
            httpPost.setEntity(reqEntity);
            httpResponse = httpClient.execute(httpPost);
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                LoggerUtil.debug(CLAZZ, "got response : " + result);
            } else {
                LoggerUtil.error(CLAZZ, "something wrong happened,error code is " + statusCode);
            }
        } catch (Exception e) {
            LoggerUtil.error(NetWorkUtil.class, "发送请求失败");
        } finally {

            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                    LoggerUtil.error(NetWorkUtil.class, "关闭httpResponse时出现错误", e);
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    LoggerUtil.error(NetWorkUtil.class, "关闭httpClient时出现错误", e);
                }
            }
        }

        return JSON.parseObject(result);
    }


}
