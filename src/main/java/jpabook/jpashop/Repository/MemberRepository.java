package jpabook.jpashop.Repository;

import jpabook.jpashop.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class MemberRepository {
    // JPA에서 사용하는 표준 Annotation @PersistenceContext 설정 시 Spring에서 자동으로 생성하여 주입
    // 원래는 EntityManagerFactory에서 생성해서 주입해야하는데 번거로운 과정 생략이 가능하다.
    //@PersistenceUnit : EntityManagerFactory를 직접 주입 받는 경우 사용
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member){//회원 저장
        em.persist(member);// 저장
        return member.getId();
    }
    
    public Member findMember(Long id){//회원 검색
        return em.find(Member.class,id);
    }
    public List<Member> findAll(){
        // JPQL : 일반 SQL문과 차이점
        // SQL  : Query의 From 대상이 Table
        // JPQL : Query 의 대상 Entity
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name=:name",
                Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
