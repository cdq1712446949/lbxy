package com.lbxy.dao;

import com.lbxy.common.ImageType;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Image;
import jdk.nashorn.internal.ir.LiteralNode;

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

    public List<Image> getImagesByContentIdAndType(long contentId,int type) {
        return Image.DAO.find("select * from image where contentId = ? and type = ?", contentId, type);
    }

    public List<Image> getImagesByType(int type) {
        return Image.DAO.find("select location from image where type = ?", type);
    }
}
