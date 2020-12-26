package com.poldichen.movie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieApplicationTests {

	@Test
	public void contextLoads() {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String str = encode.encode("123456");
        System.out.println(str);
	}

}
