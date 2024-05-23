package com.shoe.service;

import com.shoe.dto.CommentDTO;
import com.shoe.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createCmt(CommentDTO commentDTO);
    List<CommentDTO> getComment();
}
