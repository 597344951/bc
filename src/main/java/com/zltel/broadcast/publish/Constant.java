package com.zltel.broadcast.publish;

/**
 * 常量集合
 * Constant class
 *
 * @author Touss
 * @date 2018/5/8
 */
public class Constant {
    /** 首次内容编辑 **/
    public static final int FIRST_EDIT = 1;
    /** 二次内容编辑 **/
    public static final int MORE_EDIT = 2;
    /** 审核 **/
    public static final int VERIFY = 3;
    /** 可发布 **/
    public static final int PUBLISHABLE = 4;
    /** 发布中 **/
    public static final int PUBLISHING = 5;
    /** 发布完成 **/
    public static final int PUBLISHED = 6;
    /** 废置 **/
    public static final int DISCARD = 0;

    /** 未审核 **/
    public static final int VERIFY_NOT_START = 0;
    /** 审核通过 **/
    public static final int VERIFY_PASS = 1;
    /** 审核未通过 **/
    public static final int VERIFY_NOT_PASS = 2;
    /** 再审核 **/
    public static final int VERIFY_ONCE = 3;
    /** 跳过审核 **/
    public static final int VERIFY_ABSTAIN = 4;
    /** 审核操作 **/
    public static final int VERIFY_OPERATE_APPROVED = 1;
    public static final int VERIFY_OPERATE_NOT_APPROVED = 2;
    public static final int VERIFY_OPERATE_ABSTAIN = 3;

    /** 为编辑 **/
    public static final int MORE_EDIT_NOT_EDIT = 0;
    /** 正在编辑 **/
    public static final int MORE_EDIT_EDITING = 1;
    /** 已提交 **/
    public static final int MORE_EDIT_COMMIT = 2;
    /** 在编辑 **/
    public static final int MORE_EDIT_REEDITING = 3;

    /** 机能-内容发布 **/
    public static final String FEATURE_CONTENT_PUBLISH = "内容发布";
    public static final String FEATURE_CONTENT_MATERIAL = "素材管理";

    /** 提示性信息 **/
    public static final String MSG_CONTENT_FIRST_EDIT = "内容发布发起.发起人：";
    public static final String MSG_CONTENT_MORE_EDIT_START = "进入编辑.编辑人：";
    public static final String MSG_CONTENT_MORE_EDIT_COMMIT = "提交编辑.提交人：";
    public static final String MSG_CONTENT_VERIFY_PASS = "审核通过.审核人：";
    public static final String MSG_CONTENT_VERIFY_NOT_PASS = "审核不通过.审核人：";
    public static final String MSG_CONTENT_VERIFY_ABSTAIN = "审核放弃.审核人：";
    public static final String MSG_CONTENT_DISCARD = "废弃内容.废弃人：";
    public static final String MSG_CONTENT_PUBLISHING = "发布内容.发布人：";
    public static final String MSG_CONTENT_OFFLINE = "下架内容.下架人：";
    public static final String MSG_CONTENT_REPUBLISH = "节目重新发布审核.发起人：";

    /** 素材类型 **/
    public static final String MATERIAL_TYPE_IMAGE = "image";
    public static final String MATERIAL_TYPE_VIDEO = "video";
    public static final String MATERIAL_TYPE_AUDIO = "audio";
    public static final String MATERIAL_TYPE_TEXT = "text";
    public static final String MATERIAL_TYPE_OTHER = "other";

    /** 素材上传原因 **/
    public static final int MATERIAL_UPLOAD_REASON_MAKE = 1;
    public static final int MATERIAL_UPLOAD_REASON_FEEDBACK = 2;

    /** 内容类型 **/
    public static final int CONTENT_TYPE_ARTICLE = 1;
    public static final int CONTENT_TYPE_TIP = 2;
    public static final int CONTENT_TYPE_ACTIVITY = 3;
    public static final int CONTENT_TYPE_SHOW = 4;
    public static final int CONTENT_TYPE_EMEGENCY = 5;
    public static final int CONTENT_TYPE_EXIST = 6;


}
