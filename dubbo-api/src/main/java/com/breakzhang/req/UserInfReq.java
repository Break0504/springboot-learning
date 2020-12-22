package com.breakzhang.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 11:47 2020/12/17
 * @description:
 */
@Data
public class UserInfReq implements Serializable {

    private static final long serialVersionUID = 8967970429973780226L;

    private String userId;
    private String name;
    private String mobileNo;
    private Integer birth;
    private Short sex;
}
