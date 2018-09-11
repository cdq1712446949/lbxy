package com.lbxy.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lbxy.core.annotation.Service;
import com.lbxy.service.FormService;

import java.util.Date;
import java.util.Optional;

/**
 * @author lmy
 * @description FormServiceImpl
 * @date 2018/9/11
 */

@Service("formService")
public class FormServiceImpl implements FormService {
    @Override
    public void put(long userId, JSONObject formIds) {
        JSONArray jsonArray = formIds.getJSONArray("formIds");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
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
    public String get(long userId) throws Exception {
        Optional<String> formId = FormService.getRandom(userId);
        return formId.orElseThrow(() -> new Exception("no formid is available（无可用formId）"));
    }
}
