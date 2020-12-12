package sanguino.board.dtos.response;

public class UserResponseDto {
    private String nick;
    private String email;

    public UserResponseDto() {
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
