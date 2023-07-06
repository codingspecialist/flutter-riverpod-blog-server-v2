package shop.mtcoding.tddbank.user;

import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {

    @Getter @Setter
    public static class LoginDTO {
        @NotEmpty
        private String username;

        @NotEmpty
        @Size(min = 4, max = 20, message = "4에서 20자 이내여야 합니다.")
        private String password;
    }

    @Getter @Setter
    public static class JoinDTO {
        @NotEmpty
        private String username;

        @NotEmpty
        @Size(min = 4, max = 20, message = "8에서 20자 이내여야 합니다.")
        private String password;

        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }
    }
}
