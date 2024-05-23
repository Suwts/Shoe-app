package com.shoe.service.impl;

import com.shoe.dto.CommentDTO;
import com.shoe.entity.Comment;
import com.shoe.entity.User;
import com.shoe.reponsitory.CommentRepo;
import com.shoe.reponsitory.UserRepo;
import com.shoe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CommentRepo commentRepo;

    @Override
    public Comment createCmt(CommentDTO commentDTO) {
        User user = userRepo.findByUserID(commentDTO.getUser_id()).orElseThrow();
        Comment comment = new Comment();
        comment.setNote(commentDTO.getNote());
        comment.setUserID(commentDTO.getUser_id());
        comment.setProductId(commentDTO.getProduct_id());
        return commentRepo.save(comment);
    }

    @Override
    public List<CommentDTO> getComment() {
        return commentRepo.getComment();
    }
}
