package sanguino.board.dtos;

import java.util.Collection;

public class BookCompleteResponseDto {

    private Long id;
    private String title;
    private String synopsis;
    private String author;
    private String editorial;
    private int publishedYear;
    private Collection<CommentResponseDto> comments;

    public BookCompleteResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Collection<CommentResponseDto> getComments() {
        return comments;
    }

    public void setComments(Collection<CommentResponseDto> comments) {
        this.comments = comments;
    }
}
