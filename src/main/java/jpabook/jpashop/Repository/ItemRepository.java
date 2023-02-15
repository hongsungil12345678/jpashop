package jpabook.jpashop.Repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor//기본 생성자(lombok)
public class ItemRepository {
    private final EntityManager em;

    //저장
    /*
    Merge -> 준영속 상태의 엔티티를 영속상태로 변경
    1. merge() 실행
    2. 파라미터로 넘어온 준영속 엔티티 식별자 값으로 1차 캐시 엔티티 조회
    2-1. 없으면 DB에서 엔티티 조회, 1차캐시 저장
    3. 조회한 영속 엔티티에 파라미터로 넘어온 엔티티 값을 채워넣음
    4. 영속상태 반환
    * */
    public void save(Item item){
        //맞는지 아닌지 검증하고 처리
        if(item.getId()==null){
            em.persist(item);
        }
        else{
            em.merge(item);
        }
    }
    public Item findOne(Long id){
        return em.find(Item.class,id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }




}
