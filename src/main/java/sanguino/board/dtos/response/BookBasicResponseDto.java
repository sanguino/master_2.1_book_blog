package sanguino.board.dtos.response;

public class BookBasicResponseDto {

    private Long id;
    private String title;

    public BookBasicResponseDto() {
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
}
