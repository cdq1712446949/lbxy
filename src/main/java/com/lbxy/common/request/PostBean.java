package com.lbxy.common.request;

import javax.validation.constraints.NotBlank;

/**
 * @author lmy
 * @description PostBean
 * @date 2018/8/27
 */
public class PostBean {

    @NotBlank(message = "发布内容不能为空")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
