package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Image;

import java.util.List;

/**
 * @author lmy
 * @description ImageService
 * @date 2018/8/26
 */
public interface ImageService {
    boolean saveImageInfo(long postId, int type, String imagePath);

    List<Image> getIndexImages();

    Page<Image> getIndexImagesByPage(int pn);

    boolean saveImage(Image image);

    boolean deleteImage(Image image);

    Page<Image> findImage(int type,int id);

}
