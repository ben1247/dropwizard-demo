package com.zy.dropwizard.service;

import com.zy.dropwizard.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Component:
 * Description:
 * Date: 17/5/23
 *
 * @author yue.zhang
 */
@Test
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
public class BookServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private BookService bookService;

    @Test
    public void get(){
        long id = 1;
        Book book = bookService.get(id);
        System.out.println(book.toString());
    }

}
