package shop.mtcoding.tddbank.post;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;
import shop.mtcoding.tddbank._core.erros.exception.Exception403;
import shop.mtcoding.tddbank._core.erros.exception.Exception404;
import shop.mtcoding.tddbank._core.erros.exception.Exception500;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository postRepository;

	@Transactional
	public Post 게시글쓰기(Post post) {
		try {
			return postRepository.save(post);
		}catch (Exception e){
			throw new Exception500("게시글 쓰기 오류 : "+e.getMessage());
		}
	}

	public List<Post> 게시글목록보기() {
		return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	public Post 게시글상세보기(Integer id) {
		return postRepository.findById(id).orElseThrow(
				() -> new Exception404("해당 id의 게시글은 존재하지 않습니다 : "+id)
		);
	}
	
	@Transactional
	public Post 게시글수정하기(Integer id, Post post, Integer principalId) {
		Post postPS = postRepository.findById(id).orElseThrow(
				() -> new Exception404("해당 id의 게시글은 존재하지 않습니다 : "+id)
		);
		if(principalId != postPS.getUser().getId()){
			throw new Exception403("해당 id의 게시글을 수정할 권한이 없습니다 : "+id);
		}
		postPS.setTitle(post.getTitle());
		postPS.setContent(post.getContent());
		return postPS;
	} // 더티 체킹
	
	@Transactional
	public void 게시글삭제하기(Integer id, Integer principalId) {
		Post postPS = postRepository.findById(id).orElseThrow(
				() -> new Exception404("해당 id의 게시글은 존재하지 않습니다 : "+id)
		);
		if(principalId != postPS.getUser().getId()){
			throw new Exception403("해당 id의 게시글을 수정할 권한이 없습니다 : "+id);
		}
		try {
			postRepository.deleteById(id);
		} catch (Exception e) {
			throw new Exception500("게시글 삭제 오류 : "+e.getMessage());
		}
	}

}
