package com.lbxy.common.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author lmy
 * @description CreateOrderBean
 * @date 2018/8/19
 */
public class CreateOrderBean {
    @NotNull(message = "酬金不能为空")
    private double reward;

    @NotNull(message = "联系人不能为空")
    @Length(min = 2, message = "姓名最少为两位")
    private String userName;

    @NotNull(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号必须为11位")
    private String userPhoneNumber;

    @NotNull(message = "取件地不能为空")
    private String fromAddress;

    @NotNull(message = "送达地不能为空")
    private String toAddress;

    private String remark;

    @NotNull(message = "详情不能为空")
    private String detail;

    @NotNull(message = "配送时间不能为空")
    private String availableDate;

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
