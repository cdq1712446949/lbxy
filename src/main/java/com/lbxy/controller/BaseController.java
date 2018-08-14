package com.lbxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Model;

public class BaseController extends Controller {

    public void getJsonParamToModel(Model type) {
        String jsonString = HttpKit.readData(getRequest());
        JSONObject jsonObject = JSON.parseObject(jsonString);
        jsonObject.keySet().forEach((key)->{
            type.set(key, jsonObject.get(key));
        });
    }

    /**
     * 此方法每次请求只能调用一次
     * @param key
     * @return
     */
    public int getJsonParamToInt(String key) {
        String jsonString = HttpKit.readData(getRequest());
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return jsonObject.getIntValue(key);
    }

    /**
     * 此方法每次请求只能调用一次
     * @param key
     * @return
     */
    public String getJsonParamToString(String key) {
        String jsonString = HttpKit.readData(getRequest());
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return jsonObject.getString(key);
    }

    public JSONObject getJsonParam() {
        String jsonString = HttpKit.readData(getRequest());
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return jsonObject;
    }
}
