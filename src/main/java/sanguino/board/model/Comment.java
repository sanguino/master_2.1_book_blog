package sanguino.board.model;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String comment;
    private Integer score;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;

    public Comment(String name, String comment, Integer score) {
        this.name = name;
        this.comment = comment;
        this.score = score;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", name =" + name + ", comment =" + comment + ", score =" + score.toString() + ", book =" + book.getId() + "]";
    }
}