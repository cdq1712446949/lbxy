package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.interceptors.WeixinLoginInterceptor;
import com.lbxy.core.plugins.cache.Injector;
import com.lbxy.core.utils.LoggerUtil;
import com.lbxy.manager.UploadManager;
import com.lbxy.model.User;
import com.lbxy.service.ImageService;
import com.lbxy.service.UserService;
import org.hibernate.validator.constraints.Range;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.File;
import java.util.Optional;

public class ImageController extends BaseController {
    @Inject
    private UserService userService;
    @Inject
    private ImageService imageService;
    @Inject
    private UploadManager uploadManager;

    public ImageController() {
        Injector.getInjector().injectMembers(this);
    }

    @Inject
    public ImageController(UserService userService, ImageService imageService, UploadManager uploadManager) {
        this.userService = userService;
        this.imageService = imageService;
        this.uploadManager = uploadManager;
    }

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
    public void image(UploadFile img, @Range(min = 0) long id, @Range(min = 0, max = 2) int type) {
        boolean result;
        String imagePath = img.getUploadPath() + File.separatorChar + img.getFileName();
        Optional<String> imageUrl = uploadManager.upload2SM(imagePath);

        if (imageUrl.isPresent()) {
            result =imageService.saveImageInfo(id, type, imageUrl.get());
        } else {
            String url = PropKit.get("server.host") + File.separatorChar + "upload" + File.separatorChar + img.getFileName();
            result =imageService.saveImageInfo(id, type, url);
        }
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
