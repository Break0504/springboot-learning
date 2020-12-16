package com.breakzhang.web.test.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 10:08 2020/12/11
 * @description:
 */
@Data
public class UserInfDto implements Serializable {
    /**
     * redis 需要的序列化
     */
    private static final long serialVersionUID = -6638303130033712772L;

    private Long id;
    private String userId;
    private String name;
    private String mobileNo;
    private Integer birth;
    private Short sex;
    private String crtTs;


}
