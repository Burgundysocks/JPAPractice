package com.simplejpa.board.repository;

import com.simplejpa.board.domain.*;
import com.simplejpa.board.dto.CommentDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

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
        em.persist(board); // Board 엔티티도 영속화
        return board;
    }

    private Comment createComment(Member member, Board board) {
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setBoard(board);
        comment.setContent("This is a comment.");
        em.persist(comment); // Comment 엔티티도 영속화
        return comment;
    }

    @Test
    public void testSaveAndFindAllByBoardId() {
        // Given
        Member member = createMember();
        Board board = createBoard(member);
        Comment comment1 = createComment(member, board);
        Comment comment2 = createComment(member, board);

        // When
        List<Comment> comments = commentRepository.findAllByBoardId(board.getId());

        // Then
        Assertions.assertNotNull(comments);
        Assertions.assertEquals(2, comments.size());
        for (Comment comment : comments) {
            Assertions.assertEquals(board.getId(), comment.getBoard().getId());
        }
    }

    @Test
    public void testFindAllByMemberId() {
        // Given
        Member member = createMember();
        Board board = createBoard(member);
        Comment comment = createComment(member, board);

        // When
        List<Comment> comments = commentRepository.findAllByMemberId(member.getId());

        // Then
        Assertions.assertNotNull(comments);
        Assertions.assertFalse(comments.isEmpty());
        for (Comment foundComment : comments) {
            Assertions.assertEquals(member.getId(), foundComment.getMember().getId());
        }
    }

    @Test
    public void testUpdate() {
        // Given
        Member member = createMember();
        Board board = createBoard(member);
        Comment comment = createComment(member, board);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent("Updated Comment");

        // When
        commentRepository.update(commentDTO);
        em.flush();
        em.clear(); // Clear the persistence context to avoid caching issues

        // Then
        Comment updatedComment = em.find(Comment.class, comment.getId());
        Assertions.assertNotNull(updatedComment);
        Assertions.assertEquals("Updated Comment", updatedComment.getContent());
    }

    @Test
    public void testDelete() {
        // Given
        Member member = createMember();
        Board board = createBoard(member);
        Comment comment = createComment(member, board);

        // When
        commentRepository.delete(comment);
        em.flush();
        em.clear(); // Clear the persistence context to avoid caching issues

        // Then
        Comment deletedComment = em.find(Comment.class, comment.getId());
        Assertions.assertNull(deletedComment); // Ensure comment is deleted
    }

}
