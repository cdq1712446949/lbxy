package com.lbxy.manager;

import com.alibaba.fastjson.JSONObject;
import com.lbxy.core.annotation.Service;
import com.lbxy.core.utils.LoggerUtil;
import com.lbxy.core.utils.NetWorkUtil;
import com.lbxy.manager.api.Api;

import java.util.Optional;

/**
 * @author lmy
 * @description UploadManager
 * @date 2018/9/14
 */

@Service
public class UploadManager {
    public Optional<String> upload2SM(String imagePath) {
        JSONObject result = NetWorkUtil.uploadFile(Api.SM_MS, imagePath, "smfile");
        if ("success".equalsIgnoreCase(result.getString("code"))) {
            LoggerUtil.info(getClass(), "上传图片到SM.MS成功: " + result.toJSONString());
            return Optional.ofNullable(result.getJSONObject("data").getString("url"));
        } else {
            LoggerUtil.error(getClass(), "上传图片到SM.MS失败: " + result.toJSONString());
            return Optional.empty();
        }
    }
}
