package com.jojoldu.book.springboot.config.auth.dto;

import com.jojoldu.book.springboot.domain.user.User;
import lombok.Getter;
/*
* SessionUser에는 인증된 사용자 정보만 필요합니다. 그 외에 필요한 정보들은 없으니, name, email, picture 만 필드로 선언합니다.
* 왜 User 클래스를 그대로 사용하면 안되는지?
* User 클래스를 그대로 사용했을 시 에러 ㅂ라생.
*
* 이는 세션에 저장하기 위해 User 클래스를 세션에 저장하려하니, User클래스에 직렬화를 구현하지 않았다는 의미의 에러가 말생
* 그럼 오류를 해결하기 위해 User클래스에 직렬화 코드를 넣으면 될까? 그것에 대해선 생각해 볼 게 많음. 이유는 User 클래스가 엔티티이기 때문
* 엔티티 클래스에는 언제 다른 엔티티와 관계가 형성될지 모릅니다.
* 예를 들어 @OneToMany, @ManyToMany 등 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함되며, 성능 이슈, 부수 효과가 발생활 확률이 높음
* 그래서 직렬화 기능을 가진 세션 DTO 를 하나 추가로 만드는 것이 이후 운영 및 유지보수 때 많은 도움이 된다.
*/
@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
