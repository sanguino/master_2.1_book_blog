package sanguino.board.model;

import java.util.List;

public class BookWithComments extends Book {

    private List<Comment> comments;

    public BookWithComments(Book book, List<Comment> comments) {
        super(book.getTitle(), book.getSynopsis(), book.getAuthor(), book.getEditorial(), book.getPublishedYear());
        this.setId(book.getId());
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
