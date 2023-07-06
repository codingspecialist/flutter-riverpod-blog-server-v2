package shop.mtcoding.tddbank._core.util;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;
import shop.mtcoding.tddbank.post.Post;
import shop.mtcoding.tddbank.post.PostRepository;
import shop.mtcoding.tddbank.user.User;
import shop.mtcoding.tddbank.user.UserRepository;

@RequiredArgsConstructor
@Configuration
public class DBInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, PostRepository postRepository) {

        return (args) -> {
            List<User> users = new ArrayList<>();
            users.add(User.builder().username("ssar").password(passwordEncoder.encode("1234")).email("ssar@nate.com").build());
            users.add(User.builder().username("cos").password(passwordEncoder.encode("1234")).email("cos@nate.com").build());
            userRepository.saveAll(users);

            List<Post> posts = new ArrayList<>();
            posts.add(Post.builder().title("꽃길만 걷는 법").content("꽃길이 아닌 곳은 걷지 않는다. 걸어야 한다면 최대한 빨리 벋어나기 위해 노력한다.").user(User.builder().id(1).build()).build());
            posts.add(Post.builder().title("자부심을 가지는 법").content("자부심이 낮아지는 일은 하지 않는다. 만약 해야한다면 최대한 빨리 벋어나기 위해 노력한다.").user(User.builder().id(1).build()).build());
            posts.add(Post.builder().title("낭만을 배우는 법").content("낭만을 배우는 법은 나도 알고 싶다.").user(User.builder().id(1).build()).build());
            posts.add(Post.builder().title("호기심을 가지는 법").content("궁금하면 맞서봐야 한다. 그래야 꼬꼬무처럼 호기심이 증가한다.").user(User.builder().id(2).build()).build());
            posts.add(Post.builder().title("행복을 느끼는 법").content("그렇게 생각하고, 그렇게 믿는 수 밖에 없다. 금방 사라지더라.").user(User.builder().id(2).build()).build());
            postRepository.saveAll(posts);
        };
    }
}
