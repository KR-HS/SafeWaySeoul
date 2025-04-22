package com.example.jpa.util;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageDTO<T> {
    //페이지네이션을 그리는 클래스
    private int start; //첫페이지번호
    private int end; //끝페이지번호
    private boolean prev; //이전버튼
    private boolean next; //다음버튼
    //Page클래스를 전달받아서 필요한 값을 멤버변수로 저장
    private int page; //조회하고있는 페이지번호
    private int amount; //조회하고있는 데이터개수
    private int pageTotal; //전체 페이지수
    private List<T> pageData; //조회된 데이터
    private long total; //전체게시글수

    private List<Integer> pageList; //화면에서 반복문으로 그릴페이지

    //생성자
    public PageDTO(Page<T> pageable) {

        this.page = pageable.getNumber() + 1; //1번 페이지가 0으로 들어오기떄문에
        this.amount = pageable.getSize();
        this.pageTotal = pageable.getTotalPages();
        this.pageData = pageable.getContent();
        this.total = pageable.getTotalElements();

        //end페이지 계산
        //(int)(현재조회하는 페이지 / 10.0 ) * 10
        this.end = (int)(Math.ceil( this.page / 10.0 ) ) * 10;

        //start페이지 계산
        //끝페이지 - 그려질 페이지네이션 + 1
        this.start = end - 10 + 1;

        //진짜 끝페이지의 계산
        //404개 게시글-> 41번페이지 도달했을때 end = 50, pageTotal - 41
        //404개 게시글-> 10번페이지 도달했을때 end = 10, pageTotal - 41
        this.end = end > pageTotal ? pageTotal : end;

        //이전버튼 활성화
        //start값은 1, 11, 21, 31 ......
        this.prev = start > 1;

        //다음버튼 활성화
        //전체페이지수가 end값보다 큰경우 true
        this.next = pageTotal > end;

        //페이지번호 리스트
        this.pageList = IntStream.rangeClosed(this.start, this.end).boxed().collect(Collectors.toList());
    }
}