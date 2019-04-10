package com.zengjinhao.pos.common.annotion;

import java.lang.annotation.*;

/**
 * 多数据源标识
 *自定义一个注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataSource {

    String name() default "";
}
