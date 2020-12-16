package com.breakzhang.web.test.dao;

import com.breakzhang.web.test.dto.UserInfDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author: Created by zhangsf
 * @datetime: Created in 10:13 2020/12/11
 * @description:
 */
public interface UserInfoDao {

    int save(UserInfDto dto);

    List<UserInfDto> listUserInf();

    UserInfDto getOne(UserInfDto dto);

    UserInfDto getOneByUserId(@Param("userId") String userId);

    int update(UserInfDto dto);

    int delete(@Param("id") Long id);

}
