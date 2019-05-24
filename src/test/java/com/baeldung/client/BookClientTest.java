package com.baeldung.client;

import com.baeldung.model.Book;
import com.baeldung.web.resource.BookResource;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BookClientTest {

    BookClient bookClient = Feign.builder()
            .client(new OkHttpClient())
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .logger(new Slf4jLogger(BookClient.class))
            .logLevel(Logger.Level.FULL)
            .target(BookClient.class, "http://localhost:8081/api/books");

    @Test
    public void testClient() {

        Map<String,String> queryParams = new HashMap<>();
        List<BookResource> all = bookClient.findAll( queryParams );

        System.out.println(  "=====================" );
        all.stream().forEach( bookResource -> {
            System.out.println( bookResource.getBook() );
        });


    }

    @Test
    public void givenBookClient_shouldRunSuccessfully_Summary() throws Exception {

        Map<String,String> queryParams = new HashMap<>();
        queryParams.put( "summary", "true" );
        List<Book> books = bookClient.findAll( queryParams ).stream()
                .map(BookResource::getBook)
                .collect(Collectors.toList());

        System.out.println( "With Summary Param" );
        books.stream().forEach( book -> {
            System.out.println( book );
        });


        assertTrue(books.size() > 2);
    }

    @Test
    public void givenBookClient_shouldRunSuccessfully() throws Exception {

        Map<String,String> queryParams = new HashMap<>();
        List<Book> books = bookClient.findAll( queryParams ).stream()
                .map(BookResource::getBook)
                .collect(Collectors.toList());

        assertTrue(books.size() > 2);
    }

    @Test
    public void givenBookClient_shouldFindOneBook() throws Exception {
        Book book = bookClient.findByIsbn("0151072558").getBook();
        assertThat(book.getAuthor(), containsString("Orwell"));
    }

    @Test
    public void givenBookClient_shouldPostBook() throws Exception {
        String isbn = UUID.randomUUID().toString();
        Book book = new Book(  "Me", "It's me!", isbn );
        bookClient.create(book);
        book = bookClient.findByIsbn(isbn).getBook();

        assertThat(book.getAuthor(), is("Me"));
    }


}