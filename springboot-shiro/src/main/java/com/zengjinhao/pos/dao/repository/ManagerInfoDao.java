package com.zengjinhao.pos.dao.repository;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zengjinhao.pos.common.dao.repository.ManagerMapper;
import com.zengjinhao.pos.dao.entity.ManagerInfo;


import java.util.List;
import java.util.Map;

/**
 * Description  :
 */
public interface ManagerInfoDao extends ManagerMapper {
    ManagerInfo findByUsername(String username);
}
