package com.hb.blog;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class User {
    public int age;

    public User(int age) {
        this.age = age;
    }
}


public class ReferenceTest {

    @Test
    void RefTest() {
        User userA = new User(10);
        User userB = new User(20);

        assertThat(userA.age).isEqualTo(10);
        assertThat(userB.age).isEqualTo(20);
        System.out.println("============== Before Method ============");
        System.out.println(System.identityHashCode(userA));
        System.out.println(System.identityHashCode(userB));

        updateAge(userA, userB);

        System.out.println("============== After Method ============");
        System.out.println(System.identityHashCode(userA));
        System.out.println(System.identityHashCode(userB));

        assertThat(userA.age).isEqualTo(11);
        assertThat(userB.age).isEqualTo(20);

    }

    void updateAge(User a, User b) {
        System.out.println("============== Inside Method ============");
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));
        a.age++;
        b = new User(30);
        System.out.println("============== new Instance to B In Method ============");
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));

        b.age++;
    }
}
