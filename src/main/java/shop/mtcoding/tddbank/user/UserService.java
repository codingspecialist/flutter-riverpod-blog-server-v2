package shop.mtcoding.tddbank.user;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import shop.mtcoding.tddbank._core.erros.exception.Exception401;
import shop.mtcoding.tddbank._core.erros.exception.Exception404;
import shop.mtcoding.tddbank._core.erros.exception.Exception500;
import shop.mtcoding.tddbank._core.security.CustomUserDetails;
import shop.mtcoding.tddbank._core.security.JwtTokenProvider;
import shop.mtcoding.tddbank._core.util.ApiUtils;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;

	public String 로그인(UserRequest.LoginDTO loginDTO) {
		try {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
					= new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();

			String jwt = JwtTokenProvider.create(myUserDetails.getUser());
			return jwt;
		}catch (Exception e){
			throw new Exception401("인증되지 않았습니다");
		}
	}
	
	@Transactional
	public void 회원가입(User user) {
		try {
			userRepository.save(user);
		}catch (Exception e){
			throw new Exception500("회원가입 오류 : "+e.getMessage());
		}
	}

	public List<UserResponse.DetailDTO> 회원목록보기() {
		return userRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(UserResponse.DetailDTO::new).collect(Collectors.toList());
	}

	public User 회원정보보기(Integer id) {
		return userRepository.findById(id).orElseThrow(
				()-> new Exception404("해당 id의 유저는 존재하지 않습니다 : "+id)
		);
	}
	
	@Transactional
	public User 회원수정(Integer id, User user) {
		User userPS = userRepository.findById(id).orElseThrow(
				()-> new Exception404("해당 id의 유저는 존재하지 않습니다 : "+id)
		);
		userPS.setPassword(user.getPassword());
		userPS.setEmail(user.getEmail());
		return userPS;
	} // 더티 체킹
}
