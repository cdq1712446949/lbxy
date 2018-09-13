package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.upload.UploadFile;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.interceptors.WeixinLoginInterceptor;
import com.lbxy.model.User;
import com.lbxy.service.ImageService;
import com.lbxy.service.UserService;
import org.hibernate.validator.constraints.Range;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.File;

public class ImageController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private ImageService imageService;

    @Before({GET.class})
    public void index(int id) {
        User user = userService.findById(id);
        String url = user.get("idNoPic");
        setAttr("url", url);
        render("/back/image_show.html");
    }

    @Before({GET.class})
    public void stuNoPice(int id) {
        User user = userService.findById(id);
        String url = user.get("stuNoPic");
        setAttr("url", url);
        render("/back/image_show.html");
    }

    @Before({WeixinLoginInterceptor.class, POST.class})
    public void image(UploadFile img, @NotBlank @Range(min = 0) long id, @NotBlank @Range(min = 0, max = 2) int type) {
        //TODO 部署之后还要调试上传路径
        String imagePath = img.getUploadPath() + File.separatorChar + img.getFileName();
        boolean result = imageService.saveImageInfo(id, type, imagePath);
        if (result) {
            renderJson(MessageVoUtil.success("上传成功"));
        } else {
            renderJson(MessageVoUtil.error("上传失败，请联系管理员"));
        }
    }

    @Before({WeixinLoginInterceptor.class, GET.class})
    public void indexSwiper() {
        renderJson(MessageVoUtil.success("请求成功", imageService.getIndexImages()));
    }
}
