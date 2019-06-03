package com.yj.order.model;

import lombok.Data;

@Data
public class User {

    /**
     * 主键
     */
    private String id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 顺序号
     */
    private Long orderId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;
}
