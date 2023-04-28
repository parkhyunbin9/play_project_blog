package com.hb.blog.user.entity;

import com.hb.blog.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column
    private String description;

    @Builder
    public Member(String nickname, String password, String description) {
        this.nickname = nickname;
        this.password = password;
        this.description = description;
    }
}
