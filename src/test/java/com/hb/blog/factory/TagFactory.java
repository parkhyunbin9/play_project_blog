package com.hb.blog.factory;

import com.hb.blog.post.entity.Tag;

public class TagFactory {
    public static Tag getTag(String name) {
        return Tag.builder().name(name).build();
    }
}
