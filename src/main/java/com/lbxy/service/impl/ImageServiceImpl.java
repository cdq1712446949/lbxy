package com.lbxy.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.ImageType;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.ImageDao;
import com.lbxy.model.Image;
import com.lbxy.service.ImageService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lmy
 * @description ImageServiceImpl
 * @date 2018/8/26
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {

    @Resource
    private ImageDao imageDao;

    @Override
    @Before(Tx.class)
    public boolean saveImageInfo(long postId, int type, String imagePath) {
        Image image = new Image();
        image.setContentId(postId);
        image.setLocation(imagePath);
        image.setType(type);
        return imageDao.save(image);
    }

    @Override
    public List<Image> getIndexImages() {

        return imageDao.getImagesByType(ImageType.INDEX_SWIPER);
    }
}
