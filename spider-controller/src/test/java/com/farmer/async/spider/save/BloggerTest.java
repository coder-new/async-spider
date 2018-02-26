package com.farmer.async.spider.save;

import com.farmer.async.spider.save.dao.BloggerDao;
import com.farmer.async.spider.save.entity.BloggerEntity;
import com.farmer.async.spider.save.mapper.BloggerMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BloggerTest {


    @Autowired
    private BloggerMapper bloggerMapper;

    @Autowired
    private BloggerDao bloggerDao;

    @Test
    public void testInsert() {

        BloggerEntity bloggerEntity = new BloggerEntity();
        bloggerEntity.setBloggerName("test");
        bloggerEntity.setBloggerUrl("http://www.cnblogs.com/");

        bloggerMapper.insert(bloggerEntity);
    }

    @Test
    public void testQuery() {

        List<BloggerEntity> bloggerEntities = bloggerDao.queryUidNullBlogger();

        System.out.println(bloggerEntities.size());
    }

    @Test
    public void testUidNotNullQuery() {

        List<BloggerEntity> bloggerEntities = bloggerDao.queryUidNotNullBlogger();

        System.out.println(bloggerEntities.size());
    }

    @Test
    public void testUpdateBloggerUid() {

        bloggerDao.updateBloggerUid("test","123edafjhgc");
    }

    @Test
    public void testQueryIsRelationZeroBlogger() {

        List<BloggerEntity> bloggerEntities = bloggerDao.queryIsRelationZeroBlogger();
        System.out.println(bloggerEntities.size());
    }

    @Test
    public void testQueryOneRelationZeroBlogger() {

        BloggerEntity bloggerEntity = bloggerDao.queryOneRelationZeroBlogger();

        System.out.println("");
    }
}
