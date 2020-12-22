package com.breakzhang.dao;

import com.breakzhang.dto.UserInfDto;
import com.breakzhang.req.UserInfReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 15:03 2020/12/22
 * @description:
 */
public interface UserInfoDao {

    int save(UserInfReq req);

    List<UserInfDto> listUserInf();

    UserInfDto getOne(UserInfReq req);

    UserInfDto getOneByUserId(@Param("userId") String userId);

    int update(UserInfReq req);

    int delete(UserInfReq req);

}
