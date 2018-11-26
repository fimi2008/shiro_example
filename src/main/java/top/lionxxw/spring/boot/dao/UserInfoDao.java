package top.lionxxw.spring.boot.dao;

import org.springframework.data.repository.CrudRepository;
import top.lionxxw.spring.boot.entity.UserInfo;

/**
 * Package top.lionxxw.spring.boot.dao
 * Project shiro_example
 *
 * Author lionxxw
 * Created on 2017/8/8 17:26
 * version 1.0.0
 */
public interface UserInfoDao extends CrudRepository<UserInfo, Long> {
    /**通过username查找用户信息;*/
    UserInfo findByUsername(String username);
}
