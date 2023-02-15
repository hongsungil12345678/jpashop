package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
// DataBase명을 ORDER로 설정 안한 이유는 명령어로 설정되어 있는 경우가 많아서 관례상 피한다.
@Table(name="orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // Member Mapping

    // cascade = CascadeType.ALL
    // 1) 적용 X 경우 코드 작성 시
    // persist(orderItemA),
    // persist(orderItemB),
    // persist(orderItemC),
    // persist(order)

    // 2) 적용 시 ( 즉, cascade 설정 시 저장을 전파한다,
    // 각각 모든 엔티티에 저장해야하는걸 알아서 전파해서 코드가 간결해진다. 개념이 확실 치 않은데  추후 공부필요
    // persist (order)
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems= new ArrayList<>();
    
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;
    
    private LocalDateTime orderDate;// 주문시간 (java8 hibernate에서 자동으로 mapping)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 연관관계 메서드 (양방향
    public void setMember(Member member){
        this.member=member;
        member.getOrders().add(this);// 양방향 세팅
    }
    public void addOrderItem (OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }
    //생성
    public static Order createOrder(Member member,Delivery delivery, OrderItem... orderItems){//연관관계 설정, 생성
        Order order =new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem:orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    //==비즈니스//
    public void cancel(){// 주문취소
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem:orderItems){
            orderItem.cancel();
        }
    }
    //==조회
    //전체 주문 가격 조회
    public int getTotalPrice(){
        int totalPrice=0;
        for(OrderItem orderItem : orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
