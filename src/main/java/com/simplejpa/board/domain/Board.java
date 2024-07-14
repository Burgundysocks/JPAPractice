package com.simplejpa.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name="board")
@Getter @Setter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    @Column(name = "regdate")
    private String regDate;

    @Enumerated(EnumType.STRING)
    private UpdateCheck updateCheck;

    @Column(name = "like_cnt")
    private long likeCnt;

    @Column(name = "view_cnt")
    private long viewCnt;

    @Column(name = "reply_cnt")
    private long replyCnt;

    @Enumerated(EnumType.STRING)
    private IsDelete isDelete;

}
