package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    //================== 여기까지 공통속성============
    
    // Book 속성
    private String author;
    private String isbn;
}
