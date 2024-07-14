package com.simplejpa.board.dto;

import com.simplejpa.board.domain.IsDelete;
import com.simplejpa.board.domain.UpdateCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long id;

    private String title;

    private String content;

    private UpdateCheck updateCheck;

    private IsDelete isDelete;

    private String keyword;
}
