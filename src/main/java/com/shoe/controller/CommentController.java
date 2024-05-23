package com.shoe.controller;

import com.shoe.dto.CommentDTO;
import com.shoe.entity.Comment;
import com.shoe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok(commentService.createCmt(commentDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<CommentDTO>> getComment(){
        return ResponseEntity.ok(commentService.getComment());
    }
}
