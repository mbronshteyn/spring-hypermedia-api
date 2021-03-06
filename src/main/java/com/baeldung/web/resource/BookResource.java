package com.baeldung.web.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

import com.baeldung.model.Book;
import com.baeldung.model.BookView;
import com.baeldung.web.controller.BookController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonView;

// public class BookResource extends ResourceSupport {
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResource {

    @JsonView(BookView.Summary.class)
    private final Book book;

    @JsonCreator
    public BookResource(@NotNull final Book book) {
        this.book = book;

        // this.add(BasicLinkBuilder.linkToCurrentMapping().slash("/books").slash(book.getIsbn()).withSelfRel());
        // this.add(linkTo(methodOn(BookController.class, book).findByIsbn(book.getIsbn())).withSelfRel());
        // this.add(linkTo(BookController.class).slash(book.getIsbn()).withSelfRel());
    }

    //

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return "BookResource [book=" + book + ", toString()=" + super.toString() + "]";
    }

}
