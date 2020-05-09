package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
* 보통 ibatis나 MyBatis 등에서 Dao 라고 불리는 DB Layer 접근자 입니다.
* JPA 에선 Repository라고 부르며, 인터페이스로 생성합니다. 단순히 인터페이스를 생성 후, JpaRepositor<Entity 클래스, PK 타입>를 상속하면, 기본적인 CRUD 메소드가 자동으로 생성됩니다.
* @Repository를 추가할 필요도 없습니다.
* 주의** Entity 클래스와 기본 Entity Repository는 함께 위치해야 한다는 것입니다 둘은 아주 밀접한 관계이고, Entity 클래스는 기본 Repository 없이는 제대로 역할을 할 수가 없습니다.
*
* 나중에 프로젝트 규모가 커져 도메인별로 프로젝트를 분리해야 한다면 이때 Entity 클래스와 기본 Repository는 함께 움직여야 하므로, 도메인 패키지에서 함께 관리합니다. */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();

}
/*
SpringDataJpa에서 제공하는 않는 메소드는 위처럼 쿼리로 작성해도 되는 것을 보여드리고자 @Query를 사용하였습니다.
@Query는 가독성이 좋으니 선택해서 사용!

규모가 있는 프로젝트에서의 데이터 조회는 FK의 조인, 복잡한 조건 등으로 인해 이런 Entity 클래스 만으로 처리하기 어려워 조회용 프레임워크
를 추가로 사용합니다. 대표적으로 querydsl, jooq, MyBatis 등이 있습니다. 조회는 위 3가지 프레임워크 중 하나를 통해 조회하고, 등록/수정/삭제
등은 SpringDataJpa를 통해 진행합니다. 개인적으로는 Querydsl을 추천합니다.

추천이유
1. 타입 안정성이 보장됩니다.
 단순한 문자열로 쿼리를 생성하는 것이 아니라, 메소드를 기반으로 쿼리를 생성하기 때문에 오타나 존재하지 않는 컬럼명을 명시할 경우 IDE에서 자동으로 검출됩니다ㅣ
 이 장점은 jooq에서도 지원하지만 , MyBatis에서는 지원하지 않습니다
2. 국내 많은 회사에서 사용중입니다
 쿠팡, 배민 등 JPA를 적극적으로 사용하는 회사에서는 Querydsl을 적극적으로 사용중입니다.
3. 레퍼런스가 많습니다
 앞 2번의 장점에서 이어지는 것인데, 많은 회사와 개발자들이 사용하다보니 그만큼 국내 자료들이 많습니다. 어떤 문제가 발생했을 때 여러 커뮤니티에 질문하고
 그에 대한 답변을 들을 수 있다는 것은 큰 장점입니다.

 */
