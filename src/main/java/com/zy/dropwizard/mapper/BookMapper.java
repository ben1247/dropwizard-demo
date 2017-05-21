package com.zy.dropwizard.mapper;

import com.zy.dropwizard.domain.Book;

/**
 * Component:
 * Description:
 * Date: 17/5/19
 *
 * @author yue.zhang
 */
public interface BookMapper {

    Book selectByPrimaryKey(Long id);

    int insert(Book book);

}
