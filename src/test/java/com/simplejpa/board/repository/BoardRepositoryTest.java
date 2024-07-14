package com.simplejpa.board.repository;

import com.simplejpa.board.domain.*;
import com.simplejpa.board.dto.BoardDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    private Member createMember() {
        Member member = new Member();
        member.setLoginId("testuser");
        member.setPassword("password");
        member.setEmail("testuser@example.com");
        member.setIsDelete(IsDelete.N);
        member.setEmail("ia05125@naver.com");
        member.setAddr("tjdnf");
        member.setAddrDetail("1234");
        member.setGender(Gender.M);
        member.setAddrEtc("1234");
        member.setPhoneNumber("010-1111-1111");
        member.setZipcode("1234");
        em.persist(member);
        return member;
    }

    private Board createBoard(Member member) {
        Board board = new Board();
        board.setMember(member);
        board.setTitle("Test Board");
        board.setContent("This is a test board.");
        board.setRegDate("2024-07-15");
        board.setUpdateCheck(UpdateCheck.ORIGIN);
        board.setLikeCnt(0);
        board.setViewCnt(0);
        board.setReplyCnt(0);
        board.setIsDelete(IsDelete.N);
        return board;
    }

    @Test
    public void testSaveAndFindById() {
        // Given
        Member member = createMember();
        Board board = createBoard(member);

        // When
        boardRepository.save(board);
        em.flush();
        em.clear();

        // Then
        Board foundBoard = boardRepository.findById(board.getId());
        assertNotNull(foundBoard);
        assertEquals("Test Board", foundBoard.getTitle());
    }

    @Test
    public void testFindById() {
        // Given
        Member member = createMember();
        Board board = createBoard(member);
        boardRepository.save(board);

        // When
        Board foundBoard = boardRepository.findById(board.getId());

        // Then
        assertNotNull(foundBoard);
        assertEquals("Test Board", foundBoard.getTitle());
        assertEquals("This is a test board.", foundBoard.getContent());
    }

    @Test
    public void testFindByMemberId() {
        // Given
        Member member = createMember();
        Board board = createBoard(member);
        boardRepository.save(board);

        // When
        List<Board> boards = boardRepository.findByMemberId(member.getId());

        // Then
        assertNotNull(boards);
        Assertions.assertFalse(boards.isEmpty());
        for (Board foundBoard : boards) {
            assertEquals(member.getId(), foundBoard.getMember().getId());
        }
    }

    @Test
    public void testFindByTitleContaining() {
        // Given
        Member member = createMember();
        Board board = createBoard(member);
        boardRepository.save(board);

        BoardDTO searchDTO = new BoardDTO();
        searchDTO.setKeyword("Test");

        // When
        List<Board> boards = boardRepository.findByTitleContaining(searchDTO);

        // Then
        assertNotNull(boards);
        Assertions.assertFalse(boards.isEmpty());
        for (Board foundBoard : boards) {
            Assertions.assertTrue(foundBoard.getTitle().contains("Test"));
        }
    }

    @Test
    public void testUpdate() {
        // Given
        Member member = createMember();
        Board board = createBoard(member);
        boardRepository.save(board);

        BoardDTO updateDTO = new BoardDTO();
        updateDTO.setId(board.getId());
        updateDTO.setTitle("Updated Title");
        updateDTO.setContent("Updated Content");

        // When
        boardRepository.update(updateDTO);
        em.flush();
        em.clear(); // Clear the persistence context to avoid caching issues

        // Then
        Board updatedBoard = em.find(Board.class, board.getId());
        assertNotNull(updatedBoard);
        assertEquals("Updated Title", updatedBoard.getTitle());
        assertEquals("Updated Content", updatedBoard.getContent());
    }

    @Test
    public void testDelete() {
        // Given
        Member member = createMember();
        Board board = createBoard(member);
        boardRepository.save(board);

        BoardDTO boardUpdate = new BoardDTO();
        boardUpdate.setId(board.getId());

        // When
        boardRepository.delete(boardUpdate);
        em.flush(); // 변경 사항을 데이터베이스에 즉시 반영
        em.clear(); // 영속성 컨텍스트 초기화

        // Then
        Board deletedBoard = em.find(Board.class, board.getId());
        assertEquals(IsDelete.Y, deletedBoard.getIsDelete()); // isDelete 필드가 Y인지 확인
    }

}
