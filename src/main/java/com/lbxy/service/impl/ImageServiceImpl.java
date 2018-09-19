package com.lbxy.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.ImageType;
import com.lbxy.core.annotation.Service;
import com.lbxy.core.utils.NetWorkUtil;
import com.lbxy.dao.ImageDao;
import com.lbxy.model.Image;
import com.lbxy.service.ImageService;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.List;

/**
 * @author lmy
 * @description ImageServiceImpl
 * @date 2018/8/26
 */
public class ImageServiceImpl implements ImageService {

    private final ImageDao imageDao;

    @Inject
    public ImageServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

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

    @Override
    public Page<Image> getIndexImagesByPage(int pn) {
        return imageDao.getImageByPn(pn, ImageType.INDEX_SWIPER);
    }

    @Override
    public boolean saveImage(Image image) {
        return imageDao.save(image);
    }

    @Override
    public boolean deleteImage(Image image) {
        return imageDao.updateImage(image);
    }

    @Override
    public Page<Image> findImage(int type, int id) {
        return imageDao.findImageByTypeAndId(type,id);
    }
}
