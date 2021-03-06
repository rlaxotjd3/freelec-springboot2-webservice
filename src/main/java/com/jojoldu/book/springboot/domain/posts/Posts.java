package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import javafx.geometry.Pos;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/*
* 책 작성자는 어노테이션 순서를 주요 어노테이션을 클래스에 가깝게 둠.
* 어노테이션을 정렬하는 기준 - @Entity는 JPA의 어노테이션이며, @Getter, @NoArgsConstructor는 롬복의 어노테이션이다.
* 롬복은 코드를 단순화시켜 주지만, 필수 어노테이션은 아임. 그러다 보니 주요 어노테이션인 @Entity를 클래스에 가깝게 두고, 롬복 어노테이션을 그 위로 두었음.
*
* 이렇게 하면 이후에 코틀린 등의 새 언어 전환으로 롬복이 더이상 필요없을 경우, 쉽게 삭제 할 수 있음.
*
* Posts 클래스는 실제 DB의 테이블과 매칭될 클래스이며, Entity 클래스 라고도 합니다. JPA를 이용하면 , DB 데이터에 작업할 경우 실제 쿼리를 날리기보다는,
* 이 ENtity 클래스의 수정을 통해 작업합니다.
* JPA에서 제공하는 어노테이션
*
* @Entity
*   테이블과 링크될 클래스임을 나타냅니다.
*   기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭합니다. ex : SalesManager.java -> sales_manager table
*
* @Id
*   해당 테이블의 PK 필드를 나타냅니다.
*
* @GeneratedValue
*   PK의 생성 규칙을 나타냅니다
*   스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 됩니다.
*
* @Column
*   테이블의 컬럼을 나타내며, 굳이 선언하지 않더라도 해당 클래스의 필드는 모드 칼럼이 됩니다.
*   사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용합니다.
*   문자열의ㅡ 경우, VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나 타입을 TEXT로 변경하고 싶거나 등의 경우에 사용합니다.
*
* 참고 )
*   웬만하면 Entity의 PK는 long 타입의 Auto_increment를 추천합니다. (mysql -> bigint type이 됨)
*   주민등록번호와 같이 비즈니스상 유니크 키나, 여러 키를 조합한 복합키로 PK를 잡을 경우 난감한 상황이 종종 발생합니다.
*       (1) FK를 맺을 때, 다른 테이블에서 복합키 전부를 갖고 있거나, 중간 테이블을 하나 더 둬야 하는 상황이 발생합니다
*       (2) 인덱스에 좋은 영향을 끼치지 못합니다.
*       (3) 유니크한 조건이 변경될 경우, PK 전체를 수정해야 하는 일이 발생합니다.
*           주민등록번호, 복합키 등은 유니크 키로 별도로 추가하는 것을 추천.
*
* @NoArgsConstructor
*   기본 생성자 자동 추가
*   public Posts()와 같은 효과
* @Getter
*   클래스 내 모든 필드의 Getter 메소드를 자동생성
* @Builder
*   해당 클래스의 빌더 패턴 클래스를 생성
*   생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함 .
*
* 서비스 초기 구축 단계에선 , 테이블 설계 ( 여기선 Entity 설계) 가 빈번하게 변경되는데, 이때 롬복의 어노테이션들은 코드 변경량을 최소화시켜 주기 때문에 적극적으로 사용함.
*
* Setter가 없는데
*   자바빈 규약을 생각하면서 getter/setter를 무작정 생성하느느 경우가 있습니다. 이렇게 되면 해당 클랫의 인스턴스 값들이 언제 어디서 변해야 하는지 코드상으로 명확하게
*   구분할 수 없어, 차후 기느으 변경 시 정말 복잡해집니다.
*   그래서 Entity 클래스에서는 절대 Setter 클래스를 만들지 않습니다. 대신, 해당 필드의 값 변경이 필요하면, 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야만 합니다.
*
* 그러면, setter가 없는 상황에서 어떻게 값을 채워 DB에 삽입하나요 .
*   기본적인 구조 = 생성자를 통해 최종값을 채운 후, DB에 삽입하는 것이며, 값 변경이 필요한 경우, 해당 이벤트에 맞는 public 메소드를 호출하여 변경하는 것을 전제로 한다.
*
* 이 책에서는 생성자 대신에 @Builder를 통해 제공되는 빌더 클래스를 사용합니다. 생성자나 빌더나 생성 시점에 값을 채워주는 역할을 똑같습니다.
* 다만, 생성자의 경우 지금 채워야 할 필드가 무엇인지 명확히 지정할 수 가 없습니다. 앞으로도 빌더 패턴을 적극적으로 사용할 수 있도록 합시다.  */
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
