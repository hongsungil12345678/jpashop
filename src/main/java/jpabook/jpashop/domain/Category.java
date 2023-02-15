package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(name="category_item",
            joinColumns = @JoinColumn(name="category_id"),// 내 Column
            inverseJoinColumns = @JoinColumn(name="item_id")) // 반대편 Column
    private List<Item> items = new ArrayList<>();


    // 계층구조 상에서 부모, 자식

    @ManyToOne
    @JoinColumn(name="parent_id")
    // 부모 - 1개
    private Category parent;

    // 자식 - 여러개
    @OneToMany(mappedBy = "parent")// mappedBy="parent"
    private List<Category> child=new ArrayList<>();

    //연관관계
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
