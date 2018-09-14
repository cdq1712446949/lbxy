package com.lbxy.common.request;

import com.lbxy.common.exception.InvalidRequestParamException;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lmy
 * @description UpdateUserInfoBean
 * @date 2018/8/17
 */
public class UpdateUserInfoBean {
    private String realName;
    private String phoneNumber;
    private String address;

    private Map<String, String> params = new LinkedHashMap<>();

    public Map<String,String> getUpdateUserInfo() throws InvalidRequestParamException {
        params.clear();

        if (StringUtils.isAllBlank(realName, phoneNumber, address)) {
            throw new InvalidRequestParamException("更新用户信息的参数不能都为空！");
        } else {
            if (StringUtils.isNotBlank(realName)) {
                params.put("realName", realName);
            } else if (StringUtils.isNotBlank(phoneNumber)) {
                params.put("phoneNumber", phoneNumber);
            } else if (StringUtils.isNotBlank(address)) {
                params.put("address", address);
            } else {
                throw new InvalidRequestParamException("更新用户信息的参数不能为空！");
            }
        }

        return params;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
