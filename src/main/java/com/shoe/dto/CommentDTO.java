package com.shoe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String note;
    private int user_id;
    private int product_id;
    private String user_name;
    private LocalDateTime createtime;
    public CommentDTO(String note, String user_name, int product_id, LocalDateTime createtime){
        this.note = note;
        this.user_name = user_name;
        this.product_id = product_id;
        this.createtime = createtime;
    }

}
