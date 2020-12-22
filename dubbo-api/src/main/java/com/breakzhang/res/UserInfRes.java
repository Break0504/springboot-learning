package com.breakzhang.res;

import com.breakzhang.dto.UserInfDto;
import lombok.Data;

import java.util.List;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 11:48 2020/12/17
 * @description:
 */
@Data
public class UserInfRes extends RpcResponse {

    private List<UserInfDto> list;

    private UserInfDto data;
}
