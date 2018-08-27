package com.lbxy.dao;

import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Image;

import java.util.List;

/**
 * @author lmy
 * @description ImageDao
 * @date 2018/8/26
 */
@Repository
public class ImageDao {
    public boolean save(Image image) {
        return image.save();
    }

    public List<Image> getImagesByContentIdAndType(int contentId,int type) {
        return Image.dao.find("select * from image where contentId = ? and type = ?", contentId, type);
    }
}