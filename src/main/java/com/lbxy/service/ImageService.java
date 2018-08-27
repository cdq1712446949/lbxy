package com.lbxy.service;

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
}
