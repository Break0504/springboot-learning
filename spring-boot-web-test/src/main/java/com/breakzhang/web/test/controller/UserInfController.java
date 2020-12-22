package com.breakzhang.web.test.controller;

import com.breakzhang.web.test.cache.UserInfCache;
import com.breakzhang.web.test.consts.SexDescEnum;
import com.breakzhang.web.test.dao.UserInfoDao;
import com.breakzhang.web.test.dto.UserInfDto;
import com.breakzhang.web.test.dto.UserInfVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: Created by zhangsf
 * @datetime: Created in 10:15 2020/12/11
 * @description:
 */
@Controller
public class UserInfController {

    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 首页
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        List<UserInfDto> userInf = userInfoDao.listUserInf();
        List<UserInfVO> list = new ArrayList<>();
        userInf.forEach(user -> {
            UserInfVO infVO = new UserInfVO();
            BeanUtils.copyProperties(user, infVO);
            infVO.setSex(SexDescEnum.getDesc(user.getSex()));
            list.add(infVO);
        });
        model.addAttribute("alist", list);
        return "index";
    }

    /**
     * 添加用户信息界面
     * @return
     */
    @GetMapping("/addUserHtml")
    public String addUserHtml() {
        return "user_add";
    }

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public boolean addUser(UserInfDto user) {
        user.setUserId(String.valueOf(System.currentTimeMillis()));
        return userInfoDao.save(user) > 0 ? true : false;
    }

    /**
     * 修改页面回显
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/goUpdateUser/{userId}")
    public String goUpdateUser(@PathVariable("userId") String userId, Model model) {
        UserInfDto userInf = UserInfCache.getUserInf(userId);
        model.addAttribute("user", userInf);
        return "user_update";
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public boolean updateUser(UserInfDto user) {
        return userInfoDao.update(user) > 0 ? true : false;
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        int i = userInfoDao.delete(id);
        if (i > 0) {
            return "common/success";
        } else {
            return "common/false";
        }
    }





}
