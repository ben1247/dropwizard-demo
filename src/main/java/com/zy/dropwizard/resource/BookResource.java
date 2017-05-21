package com.zy.dropwizard.resource;

import com.zy.dropwizard.domain.Book;
import com.zy.dropwizard.service.BookService;
import com.zy.dropwizard.utils.page.Pagination;
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

}
