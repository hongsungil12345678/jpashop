package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
    @Embedded// 내장 TYPE
    private Address address;
    @Enumerated(EnumType.STRING) // Enum type임을 알려주기 위해서(@Enumerated),
    //(DEFAULT) EnumType.ORDINAL 으로 설정되어 있는데 숫자로 들어감 ex) READY-> 0 COMP ->1 이런 형태로 저장
    // 문제점 : READY -> 0 , COMP -> 1로 저장된다 치자.
    // CANCEL을 추가한다 치면 READY -> 0 , CANCEL ->1 , COMP-> 2 이런 형태로 저장 될 가능성이 있으므로 조심할 것
    // 결론 : ORDINAL 가 기본값으로 되어있는데 데이터 추가 시 문제점이 발생 할 가능성이 많음, (데이터가 밀린다거나), 순서상에서 문제
    private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]

}
