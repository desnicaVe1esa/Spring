package ru.ma1gus.JWT_token_practice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AuthDTO {

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 180, message = "Имя должно содержать от 2 до 180 символов")
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
