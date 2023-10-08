package iskander.tabaev.springsecuritybasic;

import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Collections;

public class MyTest {

    @Test
    public void test64Encode() {
        String encodedString = "aXNrYW5kZXI6MTIz";
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);
    }

    @Test
    public void test() {
        int x = 990;

        int a = 0;
        int b = 1;

        while (x > 0) {
            a = a + 1;
            b = b * (x % 10);
            x = x / 10;
        }

        System.out.println(a);
        System.out.println(b);
    }
    }
