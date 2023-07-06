package shop.mtcoding.tddbank.post;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.tddbank._core.security.CustomUserDetails;
import shop.mtcoding.tddbank._core.util.ApiUtils;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class PostController {

	private final PostService postService;

	@GetMapping("/post")
	public ResponseEntity<?> findAll() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(ApiUtils.success(postService.게시글목록보기()));
	}

	@GetMapping("/post/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(ApiUtils.success(postService.게시글상세보기(1)));
	}

	@PostMapping("/post")
	public ResponseEntity<?> save(@RequestBody Post post, @AuthenticationPrincipal CustomUserDetails userDetails) {
		post.setUser(userDetails.getUser());
		return ResponseEntity.ok().body(ApiUtils.success(postService.게시글쓰기(post)));
	}

	@PutMapping("/post/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Post post, @AuthenticationPrincipal CustomUserDetails userDetails) {
		return ResponseEntity.ok().body(ApiUtils.success(postService.게시글수정하기(id, post, userDetails.getUser().getId())));
	}

	@DeleteMapping("/post/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
		postService.게시글삭제하기(id, userDetails.getUser().getId());
		return ResponseEntity.ok().body(ApiUtils.success(null));
	}

	@GetMapping("/init/post")
	public ResponseEntity<?> initPost() {
		return ResponseEntity.ok().body(ApiUtils.success(postService.게시글목록보기()));
	}
}
