package com.farmer.async.spider.save.dao;

import com.farmer.async.spider.save.SaveTestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/3/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = SaveTestApplication.class)
@TestPropertySource(locations={"classpath:save.properties"})
public class BloggerDaoTest {


    @Autowired
    private BloggerDao bloggerDao;

    @Test
    public void test() {

        bloggerDao.queryByBloggerName("test001");
    }
}
