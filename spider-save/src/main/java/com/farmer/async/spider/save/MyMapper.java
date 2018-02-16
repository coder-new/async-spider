package com.farmer.async.spider.save;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/18
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
