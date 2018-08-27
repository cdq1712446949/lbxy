package com.lbxy.common.request;

import com.sun.istack.internal.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lmy
 * @description ReplyBean
 * @date 2018/8/27
 */
public class ReplyBean {
    @NotBlank(message = "回复的内同不能为空")
    private String content;

    @Nullable
    private Long pUserId;

    @NotNull(message = "被回复的帖子id不能为空")
    private Long pId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getpUserId() {
        return pUserId;
    }

    public void setpUserId(Long pUserId) {
        this.pUserId = pUserId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }


}
