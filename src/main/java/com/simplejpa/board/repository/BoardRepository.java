package com.simplejpa.board.repository;

import com.simplejpa.board.domain.Board;
import com.simplejpa.board.domain.IsDelete;
import com.simplejpa.board.domain.UpdateCheck;
import com.simplejpa.board.dto.BoardDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public Board findById(Long id) {
        return em.createQuery("SELECT b FROM Board b where b.id = :id and b.isDelete =:isDelete", Board.class)
                .setParameter("id", id)
                .setParameter("isDelete", IsDelete.N)
                .getSingleResult();
    }

    public List<Board> findAll() {
        return em.createQuery("SELECT b FROM Board b where b.isDelete =:isDelete", Board.class)
                .setParameter("isDelete", IsDelete.N)
                .getResultList();
    }

    public List<Board> findByMemberId(Long memberId) {
        return em.createQuery("SELECT b FROM Board b JOIN FETCH b.member m WHERE m.id = :memberId AND b.isDelete = :isDelete", Board.class)
                .setParameter("memberId", memberId)
                .setParameter("isDelete", IsDelete.N)
                .getResultList();
    }

    public List<Board> findByTitleContaining(BoardDTO searchDTO) {
        String keyword = searchDTO.getKeyword();
        return em.createQuery("SELECT b FROM Board b WHERE b.title LIKE :titlePattern AND b.isDelete = :isDelete", Board.class)
                .setParameter("titlePattern", "%" + keyword + "%")
                .setParameter("isDelete", IsDelete.N)
                .getResultList();
    }

    public void update(BoardDTO updateDTO){
        Board board = findById(updateDTO.getId());
        if(board != null){
            board.setTitle(updateDTO.getTitle());
            board.setContent(updateDTO.getContent());
            board.setUpdateCheck(UpdateCheck.UPDATE);
        }
    }

    public void delete(BoardDTO boardUpdate) {
        Board board = findById(boardUpdate.getId());
        if(board != null){
            board.setIsDelete(IsDelete.Y);
        }
    }

    public void delete(Board board) {
        em.remove(board);
    }
}
