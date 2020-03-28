package com.jojoldu.book.springboot.service.posts;

import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
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

}
