package top.lionxxw.spring.boot.service.impl;

import org.springframework.stereotype.Service;
import top.lionxxw.spring.boot.dao.UserInfoDao;
import top.lionxxw.spring.boot.entity.UserInfo;
import top.lionxxw.spring.boot.service.UserInfoService;

import javax.annotation.Resource;

/**
 * Package top.lionxxw.spring.boot.service.impl
 * Project shiro_example
 *
 * Author lionxxw
 * Created on 2017/8/8 17:32
 * version 1.0.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}
