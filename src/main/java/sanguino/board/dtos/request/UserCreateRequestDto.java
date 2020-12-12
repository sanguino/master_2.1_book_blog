package sanguino.board.dtos.request;

public class UserCreateRequestDto {
    private String nick;
    private String email;

    public UserCreateRequestDto() {
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
