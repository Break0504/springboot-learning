package com.breakzhang.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 11:45 2020/12/17
 * @description:
 */
@Data
public class UserInfDto implements Serializable {

    private static final long serialVersionUID = -4250536754544326358L;
    private Long id;
    private String userId;
    private String name;
    private String mobileNo;
    private Integer birth;
    private String sex;
    private String crtTs;

}
