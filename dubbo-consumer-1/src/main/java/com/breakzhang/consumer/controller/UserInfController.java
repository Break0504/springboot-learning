package com.breakzhang.consumer.controller;

import com.breakzhang.dto.UserInfDto;
import com.breakzhang.req.UserInfReq;
import com.breakzhang.res.RpcResponse;
import com.breakzhang.res.UserInfRes;
import com.breakzhang.service.UserInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 15:40 2020/12/22
 * @description:
 */
@Controller
public class UserInfController {

    @Autowired
    private UserInfService userInfService;

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        UserInfRes res = null;
        RpcResponse response = userInfService.listUser();
        if (response.isSuccess()) {
            res = (UserInfRes) response;
        }
        model.addAttribute("alist", res != null ? res.getList() : "");
        return "index";
    }

    /**
     * 添加用户信息界面
     *
     * @return
     */
    @GetMapping("/addUserHtml")
    public String addUserHtml() {
        return "user_add";
    }

    /**
     * 添加用户信息
     *
     * @param req
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public boolean addUser(UserInfReq req) {
        RpcResponse response = userInfService.saveUser(req);
        return response.isSuccess();
    }

    /**
     * 修改页面回显
     *
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/goUpdateUser/{userId}")
    public String goUpdateUser(@PathVariable("userId") String userId, Model model) {

        UserInfReq req = new UserInfReq();
        req.setUserId(userId);
        UserInfDto userInf = null;
        RpcResponse response = userInfService.getUser(req);
        if (response.isSuccess()) {
            UserInfRes res = (UserInfRes) response;
            userInf = res.getData();
        }
        model.addAttribute("user", userInf);
        return "user_update";
    }

    /**
     * 修改用户信息
     *
     * @param req
     * @return
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public boolean updateUser(UserInfReq req) {
        RpcResponse response = userInfService.updateUser(req);
        return response.isSuccess();
    }

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {

        UserInfReq req = new UserInfReq();
        req.setUserId(userId);
        RpcResponse response = userInfService.deleteUser(req);
        if (response.isSuccess()) {
            return "common/success";
        } else {
            return "common/false";
        }
    }
}
