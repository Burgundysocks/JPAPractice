package com.simplejpa.board.repository;

import com.simplejpa.board.domain.Board;
import com.simplejpa.board.domain.IsDelete;
import com.simplejpa.board.domain.Member;
import com.simplejpa.board.dto.MemberUpdateDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findById(String loginId) {
        return em.createQuery("SELECT m FROM Member m WHERE m.loginId = :loginId AND m.isDelete = :isDelete", Member.class)
                .setParameter("loginId", loginId)
                .setParameter("isDelete", IsDelete.N)
                .getSingleResult();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m WHERE m.isDelete = : isDelete", Member.class)
                .setParameter("isDelete", IsDelete.N)
                .getResultList();
    }

    public void update(MemberUpdateDTO updateDTO) {
        Member member = findById(updateDTO.getLoginId());
        if (member != null) {
            member.setPassword(updateDTO.getPassword());
            member.setPhoneNumber(updateDTO.getPhoneNumber());
            member.setEmail(updateDTO.getEmail());
            member.setZipcode(updateDTO.getZipcode());
            member.setAddr(updateDTO.getAddr());
            member.setAddrDetail(updateDTO.getAddrDetail());
            member.setAddrEtc(updateDTO.getAddrEtc());
        }
    }

    public void leave(MemberUpdateDTO updateDTO) {
        Member member = findById(updateDTO.getLoginId());
        if (member != null) {
            member.setIsDelete(IsDelete.Y);
        }
    }

    public void delete(Member member) {
        em.remove(member);
    }



}
