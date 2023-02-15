package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Single table
 * 모든 서브 클래스 요소를 부모 클래스에 담아서 관리
 * 서브 클래스를 dtype 을 이용해 구분 결국은 데이터를 한곳에 모아서 관리 용이
 * 상속관계 사이에서 취하는 전략 중 하나 ( singletable , joined, table per class)
 **/
/**
 * @ DiscriminatorValue("xx") : 상속매핑 기본 default
 *  보콩 자식 테이블은 부모테이블 ID 컬럼명을 그대로 사용하는데,
 *  자식 테이블의 기본키 컬럼명을 재정의 하고 싶을때  사용
 *  엔티티 저장 시 구분 컬럼에 입력 값 지정
 *  ex) "S" 라고 지정 시 엔티티 저장 시 부모클래스 DTYPE에 S 저장
 */

/**
 * @ DiscriminatorColumn : 부모 클래스에 구분 컬럼 지정
 * 기본 default DTYPE 보통 따로 지정 안함
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")// 컬럼명을 dtype 으로 구분하겠다.
@Getter @Setter
public abstract class Item {
    //기본 속성
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity; // 재고 갯수

    @ManyToMany(mappedBy = "items")
    private List<Category> categories=new ArrayList<Category>();

    /**
     *  비즈니스 로직
     */
    public void addStock(int quantity){//추가
        this.stockQuantity+=quantity;
    }

    public void removeStock(int quantity){
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("Need More Stock!!");
        }
        this.stockQuantity=restStock;
    }
}
