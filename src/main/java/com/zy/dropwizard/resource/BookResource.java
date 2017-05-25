package com.zy.dropwizard.resource;

import com.zy.dropwizard.base.page.Pagination;
import com.zy.dropwizard.domain.Book;
import com.zy.dropwizard.exception.WrongBusinessException;
import com.zy.dropwizard.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Component:
 * Description:
 * Date: 17/5/13
 *
 * @author yue.zhang
 */
@Path("books")
@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @Autowired
    private BookService bookService;

    @Path("{id}")
    @GET
    public Book get(@PathParam("id") long id){
        return bookService.get(id);
    }

    @POST
    public Book add(@Valid Book book){
        bookService.add(book);
        return book;
    }

    @GET
    public Pagination<Book> select(
            @QueryParam("name") String name,
            @DefaultValue("1") @QueryParam("pageNum") Integer pageNum,
            @DefaultValue("10") @QueryParam("pageSize") Integer pageSize){
        return bookService.select(name,pageNum,pageSize);
    }

    @Path("{id}")
    @DELETE
    public Book delete(@PathParam("id") long id){
        Book book = bookService.get(id);
        if(book == null){
            throw new WrongBusinessException("not_found_book","找不到指定的书籍");
        }
        bookService.delete(id);
        return book;
    }

}
