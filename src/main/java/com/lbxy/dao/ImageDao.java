package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
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

    public Page<Image> getImageByPn(int pn,int type){
        return Image.DAO.paginate(pn,10,"select *","from image where type = ?",type);
    }

    public boolean updateImage(Image image){
        return image.update();
    }

    public Page<Image> findImageByTypeAndId(int type,int id){
        return Image.DAO.paginate(1,10,"select *","from image where type=? and contentId=?",type,id);
    }

}
