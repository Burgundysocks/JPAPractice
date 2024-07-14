package com.simplejpa.board.dto;


import com.simplejpa.board.domain.IsDelete;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDTO {
    private String loginId;
    private String password;
    private String phoneNumber;
    private String email;

    private String zipcode;

    private String addr;

    private String addrDetail;

    private String addrEtc;

    private IsDelete isdelete;
}
