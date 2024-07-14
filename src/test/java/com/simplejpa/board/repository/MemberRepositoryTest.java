package com.simplejpa.board.repository;

import com.simplejpa.board.domain.Member;
import com.simplejpa.board.domain.IsDelete;
import com.simplejpa.board.dto.MemberUpdateDTO;
import com.simplejpa.board.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void testSaveMember() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setPassword("password");
        member.setEmail("testuser@example.com");

        // When
        memberRepository.save(member);

        // Then
        Member savedMember = em.find(Member.class, member.getId());
        assertNotNull(savedMember);
        assertEquals("testuser", savedMember.getLoginId());
        assertEquals("testuser@example.com", savedMember.getEmail());
    }

    @Test
    public void testFindById() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setPassword("password");
        member.setEmail("testuser@example.com");
        member.setIsDelete(IsDelete.N);
        memberRepository.save(member);

        // When
        Member foundMember = memberRepository.findById("testuser");

        // Then
        assertNotNull(foundMember);
        assertEquals("testuser", foundMember.getLoginId());
        assertEquals("testuser@example.com", foundMember.getEmail());
    }

    @Test
    public void testUpdateMember() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setPassword("password");
        member.setEmail("testuser@example.com");
        member.setIsDelete(IsDelete.N);
        memberRepository.save(member);

        MemberUpdateDTO updateDTO = new MemberUpdateDTO();
        updateDTO.setLoginId("testuser");
        updateDTO.setPassword("newpassword");
        updateDTO.setEmail("newemail@example.com");

        // When
        memberRepository.update(updateDTO);
        em.flush();
        em.clear(); // Clear the persistence context to avoid caching issues

        // Then
        Member updatedMember = em.find(Member.class, member.getId());
        assertNotNull(updatedMember);
        assertEquals("newpassword", updatedMember.getPassword());
        assertEquals("newemail@example.com", updatedMember.getEmail());
    }


    @Test
    public void testLeaveMember() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setPassword("password");
        member.setEmail("testuser@example.com");
        member.setIsDelete(IsDelete.N);
        memberRepository.save(member);

        MemberUpdateDTO updateDTO = new MemberUpdateDTO();
        updateDTO.setLoginId("testuser");

        // When
        memberRepository.leave(updateDTO);
        em.flush();
        em.clear(); // Clear the persistence context to avoid caching issues

        // Then
        Member deletedMember = em.find(Member.class, member.getId());
        assertNotNull(deletedMember);
        assertEquals(IsDelete.Y, deletedMember.getIsDelete());
    }


    @Test
    public void testDeleteMember() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setPassword("password");
        member.setEmail("testuser@example.com");
        memberRepository.save(member);

        // When
        memberRepository.delete(member);
        em.flush();
        em.clear(); // Clear the persistence context to avoid caching issues

        // Then
        Member deletedMember = em.find(Member.class, member.getId());
        assertNull(deletedMember);
    }
}
