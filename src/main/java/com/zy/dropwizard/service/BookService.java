package com.zy.dropwizard.service;

import com.zy.dropwizard.domain.Book;
import com.zy.dropwizard.mapper.BookMapper;
import com.zy.dropwizard.model.BookSearch;
import com.zy.dropwizard.utils.DateUtil;
import com.zy.dropwizard.base.page.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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

    public Pagination<Book> select(String name, Integer pageNum, Integer pageSize) {

        BookSearch search = new BookSearch();
        search.setName(name);
        search.setPageNum(pageNum);
        search.setPageSize(pageSize);
        int totalCount = bookMapper.selectCountBySearch(search);

        if(totalCount == 0){
            return new Pagination<>();
        }

        List<Book> bookList = bookMapper.selectBySearch(search);

        return new Pagination<>(bookList, pageNum, pageSize, totalCount);
    }

    public void delete(long id) {
        log.info("BookService delete, id: {}" , id);
    }
}
