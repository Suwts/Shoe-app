package com.shoe.reponsitory;

import com.shoe.dto.CommentDTO;
import com.shoe.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    Optional<List<Comment>> findByProductId(int id);
    @Query("select new com.shoe.dto.CommentDTO(c.note, u.userNames, c.productId, c.createtime) from Comment c INNER join User u on c.userID= u.userID")
    List<CommentDTO> getComment();
}
