package shop.mtcoding.tddbank.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.tddbank._core.erros.exception.Exception401;
import shop.mtcoding.tddbank._core.erros.exception.Exception403;
import shop.mtcoding.tddbank._core.security.CustomUserDetails;
import shop.mtcoding.tddbank._core.security.JwtTokenProvider;
import shop.mtcoding.tddbank._core.util.ApiUtils;
import shop.mtcoding.tddbank.post.Post;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO joinDTO, Errors errors){
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        userService.회원가입(joinDTO.toEntity());
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO loginDTO, Errors errors){
        String jwt = userService.로그인(loginDTO);
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody User user, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if(id != userDetails.getUser().getId()){
            throw new Exception403("해당 id의 회원 정보를 수정할 권한이 없습니다 :"+id);
        }
        UserResponse.DetailDTO responseBody = userService.회원수정(id, user);
        return ResponseEntity.ok().body(ApiUtils.success(responseBody));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> detail(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if(id != userDetails.getUser().getId()){
            throw new Exception403("해당 id의 회원 정보를 읽을 권한이 없습니다 :"+id);
        }
        UserResponse.DetailDTO responseBody = userService.회원정보보기(id);
        return ResponseEntity.ok().body(ApiUtils.success(responseBody));
    }

    @GetMapping("/init/user")
    public ResponseEntity<?> initUser() {
        List<UserResponse.DetailDTO> responseBody = userService.회원목록보기();
        return ResponseEntity.ok().body(ApiUtils.success(responseBody));
    }
}
