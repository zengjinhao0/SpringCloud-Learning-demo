package com.zengjinhao.pos.common.mutidatesource;

import com.zengjinhao.pos.common.mutidatesource.DataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    //继承AbstractRoutingDataSource实现多数据源
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }

}
