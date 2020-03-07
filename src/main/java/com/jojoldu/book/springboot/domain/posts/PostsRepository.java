package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
/*
* 보통 ibatis나 MyBatis 등에서 Dao 라고 불리는 DB Layer 접근자 입니다.
* JPA 에선 Repository라고 부르며, 인터페이스로 생성합니다. 단순히 인터페이스를 생성 후, JpaRepositor<Entity 클래스, PK 타입>를 상속하면, 기본적인 CRUD 메소드가 자동으로 생성됩니다.
* @Repository를 추가할 필요도 없습니다.
* 주의** Entity 클래스와 기본 Entity Repository는 함께 위치해야 한다는 것입니다 둘은 아주 밀접한 관계이고, Entity 클래스는 기본 Repository 없이는 제대로 역할을 할 수가 없습니다.
*
* 나중에 프로젝트 규모가 커져 도메인별로 프로젝트를 분리해야 한다면 이때 Entity 클래스와 기본 Repository는 함께 움직여야 하므로, 도메인 패키지에서 함께 관리합니다. */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
