package com.simplejpa.board.dto;

import com.simplejpa.board.domain.Board;
import com.simplejpa.board.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    private Board board;

    private Member member;

    private String content;

}
