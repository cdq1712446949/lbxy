package com.lbxy.common.request;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @author lmy
 * @description SaveUserInfoBean
 * @date 2018/9/14
 */
public class SaveUserInfoBean {
    /*
    [ nickName:[laumgjyu] ]
    [ gender:[1] ]
    [ language:[zh_CN] ]
    [ city:[Dezhou] ]
    [ province:[Shandong] ]
    [ country:[China] ]
    [ avatarUrl:[https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaELqmXEhcrKbJeDAM0zhjBkd75vMqpuiciaYGWIDPuRFkUb1E7SMzDDPibn3cgCpgFTicK4FNFalOQkF0Q/132] ]
     */
    @NotBlank
    private String nickName;

    @Range(max = 2)
    private int gender;

    @NotBlank
    private String language;

    @NotBlank
    private String city;

    @NotBlank
    private String province;

    @NotBlank
    private String country;

    @NotBlank
    private String avatarUrl;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
