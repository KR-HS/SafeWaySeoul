package com.example.jpa.util;

import lombok.Data;

@Data //getter, setter, toString
public class Criteria  { //criteria에서 userVO를 사용하기위해

    private int page; //페이지번호
    private int amount; //데이터 개수

    //검색키워드 추가
    private String searchName; //인풋
    private String searchType; //검색타입

    public Criteria() {
        this(1, 10);
    }
    public Criteria(int page, int amount) {
        super();
        this.page = page;
        this.amount = amount;
    }

    //limit (페이지 -1) * 데이터개수, 데이터개수
    //limit함수의 앞에 전달될 값
    public int getPageStart() {
        return (page - 1) * amount;
    }

}
