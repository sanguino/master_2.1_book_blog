package sanguino.board.dtos.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class CommentRequestDto {

    private String name;
    private String comment;
    @Min(value = 0, message = "Score should be equals or greater than 0")
    @Max(value = 5, message = "Score should be equals or less than 5")
    private float score;

    public CommentRequestDto() {
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
