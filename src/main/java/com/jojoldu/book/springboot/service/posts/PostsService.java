package com.jojoldu.book.springboot.service.posts;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostsService {
    /*
    * 스프링을 어느정도 써 본 사람이라면, Controller와 Service에서 @Autowired가 없는 것이 어색하게 느껴집니다. 스프링에선 Bean을 주입
    * 받는 방식이 다음과 같습니다
    * @Autowired
    * setter
    * 생성자
    *
    * 이 중 가장 권장하는 방식은 생성자로 주입받는 방식입니다 (@Autowired)는 권장하지 않습니다. 즉 생성자로 Bean 객체를 받도록 하면
    * @Autowired와 동일한 효과를 볼 수가 있다는 것입니다. 그러면 앞에서 생성자는 어디 있을까요
    *
    * 바로 @RequiredArgsConstructor에서 해결 해 줍니다 final이 선언된 모든 필드를 인자값으로 하는 생성자를 롬복의 @RequiredArgsConstructor가
    * 대신 생성 해 준 것입니다.
    *
    * 생성자를 직접 안 쓰고 롬복 어노테이션을 쓰는 이유는 간단합니다. 해당 클래스의 의존성 관계가 변경 될 때 마다, 생성자 코드를 계속해서
    * 수정하는 번거로움을 해결하기 위함입니다.
    *
    * 롬복 어노테이션이 있으면 해당 컨트롤러에 새로운 서비스를 추가하거나, 기존 컴포넌트를 제거하는 등의 상황이 발생해도 생성자 코드는 전혀 손대지 않아도 됩니다.*/
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = "+ id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }
    /*
     * 여기서 신기한 것이  update 기능에서 데이터베이스에 쿼리를 날리는 부분이 없습니다. 이게 가능한 이유는 JPA의
     * 영속성 컨텍스트
     * 때문입니다.
     *
     * 영속성 컨텍스트란, 엔티티를 영구 저장하는 환경입니다. 일종의 논리적 개념이라고 보시면 되며, JPA의 핵심 내용은 엔티티가
     * 영속석 컨텍스트에 포함되어 있냐 아니냐로 갈립니다.
     * JPA의 엔티티 매니저가 활성화된 상태로 (Spring Data Jpa를 쓴다면 기본 옵션) 트랜잭션 안에서 데이터베이스에서 데이터를 가져오면
     * 이 데이터는 영속성 컨텍스트가 유지된 상태입니다.
     * 이 상태에서 해당 데이터의 값을 변경시키면, 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영합니다. 즉, Entity 객체의 값만
     * 변경하면, 별도로 update 쿼리를 날릴 필요가 없다는 것. 이 개념을 더티 체킹 방식이라고 합니다.
     * ㄴ*/

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(("해당 사용자가 없습니다. iid = " + id)));
        return new PostsResponseDto(entity);
    }

}
