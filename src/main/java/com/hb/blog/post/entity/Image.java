package com.hb.blog.post.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "images")
public class Image {


    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
