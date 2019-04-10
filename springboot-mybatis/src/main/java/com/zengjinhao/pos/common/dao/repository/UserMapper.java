package com.zengjinhao.pos.common.dao.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zengjinhao.pos.common.dao.entity.User;


/**
 * 后台管理用户表 Mapper
 *
 * @author 熊能
 * @version 1.0
 * @since 2018/01/02
 */
public interface UserMapper extends BaseMapper<User> {
    //这个接口继承 BaseMapper<User>后即可获得常用的增删改查的方法， 如果有其他复杂的操作
    // 就再定义自己的方法，并同时定义mapping文件即可

}
