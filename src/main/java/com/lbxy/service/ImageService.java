package com.lbxy.service;

/**
 * @author lmy
 * @description ImageService
 * @date 2018/8/26
 */
public interface ImageService {
    boolean saveImageInfo(long postId, int type, String imagePath);
}
