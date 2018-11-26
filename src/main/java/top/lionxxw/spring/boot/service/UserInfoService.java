package top.lionxxw.spring.boot.service;

import top.lionxxw.spring.boot.entity.UserInfo;

/**
 * Package top.lionxxw.spring.boot.service
 * Project shiro_example
 *
 * Author lionxxw
 * Created on 2017/8/8 17:23
 * version 1.0.0
 */
public interface UserInfoService {
    /**通过username查找用户信息;*/
    UserInfo findByUsername(String username);
}
