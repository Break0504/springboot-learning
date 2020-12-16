package com.breakzhang.web.test.dto;

import lombok.Data;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 10:12 2020/12/14
 * @description:
 */
@Data
public class UserInfVO {

    private Long id;
    private String userId;
    private String name;
    private String mobileNo;
    private Integer birth;
    private String sex;
    private String crtTs;


}
