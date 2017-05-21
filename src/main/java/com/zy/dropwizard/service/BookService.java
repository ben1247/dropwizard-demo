package com.zy.dropwizard.service;

import com.zy.dropwizard.domain.Book;
import com.zy.dropwizard.mapper.BookMapper;
import com.zy.dropwizard.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Component:
 * Description:
 * Date: 17/5/19
 *
 * @author yue.zhang
 */
@Slf4j
@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    public Book get(long id) {
        return bookMapper.selectByPrimaryKey(id);
    }

    public void add(Book book) {
        Timestamp now = DateUtil.getNow();
        book.setCreateTime(now);
        book.setUpdateTime(now);
        bookMapper.insert(book);
    }
}
