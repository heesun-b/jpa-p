package shop.mtcoding.board.modules.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class UserSaveRequest {

    @NotBlank(message = "username을 입력해주세요")
    private String username;

    @NotBlank(message = "passoword을 입력해주세요")
    private String password;

    public UserSaveRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
