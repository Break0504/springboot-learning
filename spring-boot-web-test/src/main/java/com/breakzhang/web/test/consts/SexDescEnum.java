package com.breakzhang.web.test.consts;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 11:37 2020/12/14
 * @description:
 */
public enum SexDescEnum {

    MAN((short) 0, "男"),
    WOMAN((short) 1, "女");

    private short code;
    private String desc;

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    SexDescEnum(short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SexDescEnum get(Short code) {
        if (null == code) {
            return null;
        }
        for (SexDescEnum e : SexDescEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }


    public static String getDesc(Short code) {
        return get(code) == null ? "未知" : get(code).getDesc();
    }


}
