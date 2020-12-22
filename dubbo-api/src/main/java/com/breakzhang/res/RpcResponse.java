package com.breakzhang.res;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 13:32 2020/12/17
 * @description:
 */
@Data
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = -6951517457518045324L;

    private int code = 200;
    private String desc = "成功";

    public RpcResponse() {
    }

    public RpcResponse(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public boolean isSuccess() {
        return code == 200;
    }

    //http://tool.oschina.net/commons?type=5
    public boolean isBroadSuccess() {
        return isSuccess() || code == 202 || code == 205;
    }

    public static final RpcResponse create() {
        return new RpcResponse();
    }

    public static final RpcResponse create(String desc) {
        return new RpcResponse(200, desc);
    }

    public static final RpcResponse create(int code, String desc) {
        return new RpcResponse(code, desc);
    }

    public static final RpcResponse createErr(String desc) {
        return new RpcResponse(500, desc);
    }

}
