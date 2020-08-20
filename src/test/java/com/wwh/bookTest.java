package com.wwh;

import com.wwh.service.BookService;
import com.wwh.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class bookTest {

    @Resource
    private BookService bookService;

    @Resource
    private UserService userService;

    @Test
    public void testBook(){
//        System.out.println(bookService.list());
        System.out.println(userService.findByUsername("wuwenhao"));
    }
}
