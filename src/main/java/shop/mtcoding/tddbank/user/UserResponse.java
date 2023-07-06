package shop.mtcoding.tddbank.user;

import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

public class UserResponse {

    @Setter
    @Getter
    public static class DetailDTO {
        private Integer id;
        private String username;
        private String password;
        private String email;
        private String created;
        private String updated;

        public DetailDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = "";
            this.email = user.getEmail();
            this.created = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.updated = user.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

    }
}
