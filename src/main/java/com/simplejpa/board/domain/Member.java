package com.simplejpa.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="member")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private long id;

    @Column(name = "login_id")
    private String loginId;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private String zipcode;

    private String addr;

    @Column(name = "addrdetail")
    private String addrDetail;

    @Column(name = "addretc")
    private String addrEtc;

    @Enumerated(EnumType.STRING)
    private IsDelete isDelete;


}
