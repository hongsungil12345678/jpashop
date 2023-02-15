package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class Member { // Database에 Member로 저장
    @Id @GeneratedValue
    // Entity 식별자는 id로 지정 (PK : Primary Key 기본키)로 설정
    // PK명 "member_id"
    @Column(name="member_id")
    private Long id;
    private String name;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member")
    private List<Order> orders=new ArrayList<>();

}
