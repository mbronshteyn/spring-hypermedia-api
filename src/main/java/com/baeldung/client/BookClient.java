package com.baeldung.client;

import com.baeldung.model.Book;
import com.baeldung.web.resource.BookResource;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import org.hibernate.engine.spi.QueryParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BookClient {
    @RequestLine("GET /{isbn}")
    BookResource findByIsbn(@Param("isbn") String isbn);

    @RequestLine("GET")
    List<BookResource> findAll( @QueryMap Map<String,String> queryParameters );

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void create(Book book);
}
