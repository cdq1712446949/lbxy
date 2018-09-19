package com.lbxy.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.service.FormService;

import java.util.Date;
import java.util.Optional;

/**
 * @author lmy
 * @description FormServiceImpl
 * @date 2018/9/11
 */

public class FormServiceImpl implements FormService {
    @Override
    @Before(Tx.class)
    public void put(long userId, JSONArray formIds) {
        for (int i = 0; i < formIds.size(); i++) {
            JSONObject object = formIds.getJSONObject(i);
            String formId = object.getString("formId");

            long timestamp = object.getLongValue("timestamp");
            long currentTimestamp = new Date().getTime() / 1000;

            int expireSeconds = Math.toIntExact(currentTimestamp - timestamp);
            if (expireSeconds <= 0) {
                continue;
            }
            FormService.put(userId, formId, expireSeconds);
        }
    }

    @Override
    public Optional<String> get(long userId) {
       return FormService.getRandom(userId);
    }
}
