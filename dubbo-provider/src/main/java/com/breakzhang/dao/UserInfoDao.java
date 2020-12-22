package com.breakzhang.dao;

import com.breakzhang.dto.UserInfDto;
import com.breakzhang.req.UserInfReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 14:20 2020/12/17
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
