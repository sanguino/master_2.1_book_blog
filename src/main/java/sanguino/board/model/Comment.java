package sanguino.board.model;

public class Comment {

    private Long id;
    private Long bookId;
    private String name;
    private String comment;
    private Integer score;

    public Comment(Long bookId, String name, String comment, Integer score) {
        this.bookId = bookId;
        this.name = name;
        this.comment = comment;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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

    @Override
    public String toString() {
        return "Book [id=" + id + "bookId =" + bookId + ", name =" + name + ", comment =" + comment + ", score =" + score.toString() + "]";
    }

}