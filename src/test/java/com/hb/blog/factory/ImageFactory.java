package com.hb.blog.factory;

import com.hb.blog.post.entity.Image;

import java.util.UUID;

public class ImageFactory {

    public static Image getImage() {
        return Image.builder()
                .url(new Byte(UUID.randomUUID().toString()))
                .build();
    }

    public static Image getImage(String url) {
        return Image.builder()
                .url(new Byte(url))
                .build();
    }

}
