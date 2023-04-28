package com.hb.blog.factory;

import com.hb.blog.user.entity.Member;

public class MemberFactory {
    public static Member getNormalMember() {
        return Member.builder()
                .nickname("테스트 유저")
                .password("123123")
                .description("나는 테스트 유저입니다.")
                .build();
    }
}
