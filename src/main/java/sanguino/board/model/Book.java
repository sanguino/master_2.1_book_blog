package sanguino.board.model;

import com.fasterxml.jackson.annotation.JsonView;

public class Book {

    public interface Basic {}

    @JsonView(Basic.class)
    private Long id;

    @JsonView(Basic.class)
    private String title;

    private String synopsis;
    private String author;
    private String editorial;
    private Integer publishedYear;

    public Book(String title, String synopsis, String author, String editorial, Integer publishedYear) {
        this.title = title;
        this.synopsis = synopsis;
        this.author = author;
        this.editorial = editorial;
        this.publishedYear = publishedYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title =" + title + ", synopsis =" + synopsis + ", author =" + author + ", editorial =" + editorial + ", publishedYearuser=" + this.publishedYear.toString() + "]";
    }

}
