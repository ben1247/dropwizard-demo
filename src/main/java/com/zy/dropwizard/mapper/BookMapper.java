package com.zy.dropwizard.mapper;

import com.zy.dropwizard.domain.Book;
import com.zy.dropwizard.model.BookSearch;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 17/5/19
 *
 * @author yue.zhang
 */
public interface BookMapper {

    Book selectByPrimaryKey(Long id);

    Integer insert(Book book);

    Integer selectCountBySearch(BookSearch search);

    List<Book> selectBySearch(BookSearch search);
}
