package com.simplejpa.board.repository;

import com.simplejpa.board.domain.Board;
import com.simplejpa.board.domain.Comment;
import com.simplejpa.board.domain.IsDelete;
import com.simplejpa.board.dto.CommentDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public List<Comment> findAllByBoardId(Long boardId) {
        return em.createQuery("select c from Comment c JOIN FETCH c.board b where b.id= :boardId and b.isDelete =: isDelete", Comment.class)
                .setParameter("boardId", boardId)
                .setParameter("isDelete", IsDelete.N)
                .getResultList();
    }

    public List<Comment> findAllByMemberId(Long memberId) {
        return em.createQuery("select c from Comment c JOIN FETCH c.member m where m.id = :memberId and m.isDelete = :isDelete", Comment.class)
                .setParameter("memberId", memberId)
                .setParameter("isDelete", IsDelete.N)
                .getResultList();
    }

    public void update(CommentDTO commentDTO) {
        Comment comment = em.find(Comment.class, commentDTO.getId());
        if(comment != null) {
            comment.setContent(commentDTO.getContent());
        }
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }

}
