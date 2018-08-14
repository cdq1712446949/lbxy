package com.lbxy.utils;

import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author lmy
 * @description RandomAvatarUtil 随机生成
 * @date 2018/8/13
 */
public class RandomAvatarUtil {

    /**
     * 图片的url，三个参数依次为：头像编号（随机正整数）、图片分辨率、头像风格（identicon、monsterid、wavatar、retro、robohash等）
     */
    public static String avatarUrl = "http://www.gravatar.com/avatar/%s?s=%s&d=%s";

    public static List<String> avatarTypes = Arrays.asList("identicon", "monsterid", "wavatar", "retro", "robohash");

    public static void generateAvatarAndDownload(String savePath) {
        int avatarCode = RandomUtils.nextInt();
        int avatarType = RandomUtils.nextInt(0, 5);
        String reqUrl = String.format(avatarUrl, avatarCode, 100, avatarTypes.get(avatarType));

        NetWorkUtil.doGetDownload(reqUrl,savePath);
    }

    public static String generateAvatarUrl() {
        int avatarCode = RandomUtils.nextInt();
        int avatarType = RandomUtils.nextInt(0, 5);
        String reqUrl = String.format(avatarUrl, avatarCode, 100, avatarTypes.get(avatarType));
        return reqUrl;
    }


}
