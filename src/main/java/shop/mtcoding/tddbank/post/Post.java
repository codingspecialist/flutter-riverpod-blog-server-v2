package shop.mtcoding.tddbank.post;


import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.*;
import shop.mtcoding.tddbank.user.User;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post_tb")
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // auto_increment
	@Column(nullable = false, length = 100)
	private String title;
	@Column(nullable = false, length = 10000)
	private String content;

	@ManyToOne
	private User user;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
}
