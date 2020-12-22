package com.breakzhang.service;

import com.breakzhang.req.UserInfReq;
import com.breakzhang.res.RpcResponse;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 13:38 2020/12/17
 * @description: 用户服务接口
 */
public interface UserInfService {

    /**
     * 用户查询接口
     * @return
     */
    RpcResponse listUser();

    /**
     * 用户保存接口
     * @param req
     * @return
     */
    RpcResponse saveUser(UserInfReq req);

    /**
     * 用户修改接口
     * @param req
     * @return
     */
    RpcResponse updateUser(UserInfReq req);

    /**
     * 获取单个用户信息接口
     * @param req
     * @return
     */
    RpcResponse getUser(UserInfReq req);

    /**
     * 删除用户
     * @param req
     * @return
     */
    RpcResponse deleteUser(UserInfReq req);

}
