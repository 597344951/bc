package com.zltel.broadcast.publish.utils;

import com.zltel.broadcast.publish.Constant;

/**
 * MaterialUtil class
 *
 * @author Touss
 * @date 2018/5/22
 */
public class MaterialUtil {

    private final static String[] IMAGE = {"webp", "bmp", "pcx", "tiff", "gif", "jpg", "jpeg", "tga", "exif",
            "fpx", "svg", "psd", "cdr", "pcd", "dxf", "ufo", "eps", "ai", "png", "hdri", "raw", "wmf", "flic",
            "emf", "ico"};
    private final static String[] VIDEO = {"avi", "rmvb", "rm", "asf", "divx", "mpg", "mpeg", "mpe", "wmv", "mp4",
            "mkv", "vob", "ogg"};
    private final static String[] AUDIO = {"wav", "aif", "aiff", "au", "mp1", "mp2", "mp3", "mid", "rmi"};

    public static String getFileType(String file) {
        String suffix = file.substring(file.lastIndexOf(".") + 1, file.length());
        for(String type : IMAGE) {
            if(type.equals(suffix)) {
                return Constant.MATERIAL_TYPE_PICTURE;
            }
        }
        for(String type : VIDEO) {
            if(type.equals(suffix)) {
                return Constant.MATERIAL_TYPE_VIDEO;
            }
        }
        for(String type : AUDIO) {
            if(type.equals(suffix)) {
                return Constant.MATERIAL_TYPE_AUDIO;
            }
        }

        return Constant.MATERIAL_TYPE_OTHER;
    }

    public static boolean isImage(String file) {
        String suffix = file.substring(file.lastIndexOf(".") + 1, file.length());
        for(String type : IMAGE) {
            if(type.equals(suffix)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isVideo(String file) {
        String suffix = file.substring(file.lastIndexOf(".") + 1, file.length());
        for(String type : VIDEO) {
            if(type.equals(suffix)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAudio(String file) {
        String suffix = file.substring(file.lastIndexOf(".") + 1, file.length());
        for(String type : AUDIO) {
            if(type.equals(suffix)) {
                return true;
            }
        }
        return false;
    }
}
