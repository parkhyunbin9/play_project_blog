package com.hb.blog.user.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
