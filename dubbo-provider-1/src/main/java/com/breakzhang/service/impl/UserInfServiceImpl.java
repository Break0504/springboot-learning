package com.breakzhang.service.impl;

import com.breakzhang.SexDescEnum;
import com.breakzhang.dao.UserInfoDao;
import com.breakzhang.dto.UserInfDto;
import com.breakzhang.req.UserInfReq;
import com.breakzhang.res.RpcResponse;
import com.breakzhang.res.UserInfRes;
import com.breakzhang.service.UserInfService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 15:03 2020/12/22
 * @description:
 */
@Service
public class UserInfServiceImpl implements UserInfService {


    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public RpcResponse listUser() {
        List<UserInfDto> userInf = userInfoDao.listUserInf();
        userInf.forEach(user -> user.setSex(SexDescEnum.getDesc(user.getSex())));
        UserInfRes res = new UserInfRes();
        res.setList(userInf);
        return res;
    }

    @Override
    public RpcResponse saveUser(UserInfReq req) {
        req.setUserId(String.valueOf(System.currentTimeMillis()));
        int result = userInfoDao.save(req);
        if (result > 0) {
            return RpcResponse.create();
        }
        return RpcResponse.createErr("新增失败");
    }

    @Override
    public RpcResponse updateUser(UserInfReq req) {
        int update = userInfoDao.update(req);
        if (update > 0) {
            return RpcResponse.create();
        }
        return RpcResponse.createErr("修改失败");
    }

    @Override
    public RpcResponse getUser(UserInfReq req) {
        UserInfDto userInf = userInfoDao.getOne(req);
        UserInfRes res = new UserInfRes();
        res.setData(userInf);
        return res;
    }

    @Override
    public RpcResponse deleteUser(UserInfReq req) {
        int delete = userInfoDao.delete(req);
        if (delete > 0) {
            return RpcResponse.create();
        }
        return RpcResponse.createErr("删除失败");
    }
}
