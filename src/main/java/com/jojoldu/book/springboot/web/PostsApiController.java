package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id) {
        return postsService.findById(id);
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
}
