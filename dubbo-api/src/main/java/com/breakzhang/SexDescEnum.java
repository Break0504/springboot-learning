package com.breakzhang;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 11:37 2020/12/14
 * @description:
 */
public enum SexDescEnum {

    MAN("0", "男"),
    WOMAN("1", "女");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    SexDescEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SexDescEnum get(String code) {
        if (null == code) {
            return null;
        }
        for (SexDescEnum e : SexDescEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }


    public static String getDesc(String code) {
        return get(code) == null ? "未知" : get(code).getDesc();
    }

}
